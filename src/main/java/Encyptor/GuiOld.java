
package Encyptor;

import static Encyptor.cipher.EncAndDec.*;
import Encyptor.cipher.Output;
import Encyptor.utils.File_handling;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;



public class GuiOld extends Main implements ActionListener {
    //all  the buttons the program will have
    private final JButton decryptButton = new JButton("Decrypt");
    private final JButton encryptButton = new JButton("Encrypt");
    private final JButton openFile = new JButton("Open file");
    private final JButton help = new JButton("Help");
    private final JButton showAllINfo = new JButton("All info");
    private final JButton loadConfigButton = new JButton("Load config for decryption");
    private final JButton clearInfo = new JButton("Clear info");
    private final JButton saveConfig = new JButton("Save config");
    private final JButton changeColor = new JButton("Dark Mode");

    private final JButton settingsButton = new JButton("Settings");
    //all the checkboxes
    private final JCheckBox deleteConf  = new JCheckBox("Delete Config after usage",false);
    private final JCheckBox deleteFile = new JCheckBox("Delete File after usage", false);

    // all  the status text like what file got chosen and stuff like that
    //Encryption labels
    private final JLabel titleE = new JLabel("Encryption files");
    private final JLabel statusFL = new JLabel("No file selected");
    private final JLabel statusL = new JLabel("No file status");
    private final JLabel statusFNameL = new JLabel("No file name");
    private final JLabel txtF = new JLabel("Key: ");//password
    //Decryption labels
    private final JLabel decryptFile = new JLabel("No file selected");//file path
    private final JLabel decryptFileName = new JLabel("No file name");//file name
    private final JLabel saltL1 = new JLabel("Decryption Salt: ");
    private final JLabel titleD = new JLabel("Decryption Files:");
    private final JLabel IVL1 = new JLabel("Decryption IV: ");


    // The settings Menu Items
    private final  JButton closeMenueM = new JButton("Close");





    //creates the panel and Frame
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();

    //creates the UI manager so that I can have a dark mode
    UIManager UI=new UIManager();

    //the colors that are being used
    Color background = Color.WHITE;
    Color foreground = Color.BLACK;

    //other general Variables
    private String path;
    //rework the way the program interacts with the os and where it saves data
    private final String workingdir = System.getProperty("user.dir");
    private byte[] curSalt;
    private byte[] curIV;
    private Output out;
    private String FileName;
    private byte[] tempsalt;
    private byte[] tempiv;
    private String ConfigFilePath;
    private String ChoosenFilePath;


    private Color dark = Color.decode("#120F10");
    //text fields
    JTextField pswField = new JTextField("password", 30);//todo replace "Password with a randomly generated password(using somethign like salt gen or somethign else wich is strong and not really reversable)"
    JTextField saltField = new JTextField(500);
    JTextField ivField = new JTextField(500);

    public GuiOld() {

        //general frame settings
        frame.setSize(700, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setTitle("File-encryption");
        frame.setResizable(false);
        //panel settings
        panel.setLayout(null);

        //does all the coloring
        repaintWindow();
        //adding all  the buttons

        openFile.setBounds(40, 170, 100, 25);
        openFile.addActionListener(this);

        //decryption button
        decryptButton.setBounds(160, 170, 100, 25);
        decryptButton.addActionListener(this);

        //encryption button
        encryptButton.setBounds(280, 170, 100, 25);
        encryptButton.addActionListener(this);

        //info Button
        showAllINfo.setBounds(40, 130, 100, 25);
        showAllINfo.addActionListener(this);

        //help button
        help.setBounds(550, 170, 120, 25);
        help.addActionListener(this);

        //load config button
        loadConfigButton.setBounds(160, 130, 220, 25);
        loadConfigButton.addActionListener(this);

        //Salt clear button
        clearInfo.setBounds(550, 130, 120, 25);
        clearInfo.addActionListener(this);

        //saves the config to your hard drive so you can send it to someone
        saveConfig.setBounds(550, 210, 120, 25);
        saveConfig.addActionListener(this);

        //Color button
        changeColor.setBounds(430, 50,200,25);
        changeColor.addActionListener(this);

        settingsButton.setBounds( 430, 20, 200, 25);
        settingsButton.addActionListener(this);

        //adding the text field to enter the key
        pswField.setBounds(130, 210, 300, 25);

        //key field for the salt will need more stuff backend but this is just a non-working intehration so that there is something there and not just something empty
        saltField.setBounds(130, 270, 300, 25);

        //Iv Field so that you can enter is manually
        ivField.setBounds(130,240,300,25);

        
        //adding text
        //just what file you have selected
        statusFL.setBounds(20, 50, 600, 25);

        statusFNameL.setBounds(20, 70, 380, 25);

        //the status of if encryption is finished and shit
        statusL.setBounds(20, 90, 200, 25);

        //adding the text for the key field
        txtF.setBounds(20, 210, 40, 25);

        //add the decryption text field
        //the label for th decryption Salt
        saltL1.setBounds(20, 270, 120, 25);

        //adding all the titles
        //encryption
        titleE.setBounds(20, 20, 600, 25);

        //decryption title
        titleD.setBounds(20, 300, 300, 25);

        //adding everything for decryption
        //file path
        decryptFile.setBounds(20, 330, 600, 25);

        //file name
        decryptFileName.setBounds(20, 360, 600, 25);
        //the title for the decryption
        
        //decription IV 
        IVL1.setBounds(20,240,200,25);


        addDefaultPanelItems();
        panel.setBackground(background);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //this open the target File we want to use to either dec or enc
        if (e.getSource() == openFile) {
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(panel);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //setting all the statuses and giving out the path for everything
                statusFL.setText("File Path: " + chooser.getSelectedFile().getPath());
                ChoosenFilePath = chooser.getSelectedFile().getPath();
                statusFNameL.setText("File Name: " + chooser.getSelectedFile().getName());
                path = chooser.getSelectedFile().getPath();
                FileName = (chooser.getSelectedFile().getName());

            }
        }

        if (e.getSource() == encryptButton) {
            //if stuff is empty give out an Error
            if (pswField.getText().trim().length() == 0 || path.trim().length() == 0) {
                JOptionPane.showMessageDialog(frame, "something was missing and it could not encrypt", "Error", JOptionPane.PLAIN_MESSAGE);
            } else {// there is stuff there and then try that
                char[] password = (pswField.getText()).toCharArray(); //converts the thing into a Char array
                String tempdir = workingdir+ "/"+ FileName + ".enc";//the path to which we encrypt to
                try {
                    //because might fabyte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();il so there is a try statement
                    //returns a new type that has the salt and IV stored
                    out = encryptedFile(password, path, tempdir, saltGen()); //actually encrypts the thing and creates a new file with this
                    //converting the output into their own arrays
                    curSalt = out.retSalt();
                    curIV = out.retIv();
                    //doing the conversion 
                    //getting all of the variables and converting it to a string 
                    String temporarysalt = Arrays.toString(out.retSalt());
                    String temporaryiv = Arrays.toString(out.retIv());
                    //removing all of the brackets
                    //for salt
                    temporarysalt=  temporarysalt.replaceAll(Pattern.quote("["),"");
                    temporarysalt=  temporarysalt.replaceAll(Pattern.quote("]"),"");
                    //for iv 
                    temporaryiv=  temporaryiv.replaceAll(Pattern.quote("["),"");
                    temporaryiv=  temporaryiv.replaceAll(Pattern.quote("]"),"");
                    //setting the text fields
                    saltField.setText(temporarysalt);
                    ivField.setText(temporaryiv);
                    temporaryiv = null;
                    temporarysalt = null;
                    out = null;
                    //file deletion 
                    //deleting the actual file
                    if(deleteFile.isSelected() == true){
                        System.out.println("File was delted at: "+ path);
                        deleteFile(path);
                    }
                    // catch all of the exceptions
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | InvalidParameterSpecException ex) {
                    Logger.getLogger(GuiOld.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frame, "Something went wrong during runtime\nplease check the Application logs", "Error", JOptionPane.PLAIN_MESSAGE);
                    statusL.setText("Error");
                }
            }
            
        }
        if (e.getSource() == showAllINfo) {
            JOptionPane.showMessageDialog(frame,
                    "All information"
                            + "\nSalt : " + saltField.getText()
                            + "\nCurrent Password : " + pswField.getText()
                            + "\nCurrent Starting paramenter : " + ivField.getText()
                            + "\n"
                            + "\n",
                    "Information",
                    JOptionPane.PLAIN_MESSAGE);
        }
        //the help menu
        if (e.getSource() == help) {
            JOptionPane.showMessageDialog(frame,
                    "this is an help menue"
                            + "\nthis shows you how to use this program"
                            + "\n"
                            + "\nthe Key Field should be used to enter the password that the user wants to use to encript/decript the file"
                            + "\nthe description Key could be entered into the program if known "
                            + "\nthe password will be used with a random salt to generate the key for encryption"
                            + "\nall info shows all of the information about the current salt and password"
                            + "\nload file should be used to load a file to de- or encrypt"
                            + "\nclear Info clears salt and IV"
                            + "\nthe password and salt can be reused to decrypt something medially"
                            + "\nso the salt stay in cach as long as the app is open "
                            + "\nselecting for the file to be deleted after usage deletes the File after is was used this is not a safe delete So"
                            + "\nthere is the possibility for the File to be recovered after deletion",
                    "Help",
                    JOptionPane.PLAIN_MESSAGE);
        
//load the file for decryption and also sets all the files
        }//this loads a config 
        if (e.getSource() == loadConfigButton) {
            String outputFileRead = "";
            JFileChooser configL = new JFileChooser();
            int returnVal2 = configL.showOpenDialog(panel);
            if (returnVal2 == JFileChooser.APPROVE_OPTION) {
                decryptFile.setText("File Path: " + configL.getSelectedFile().getPath());
                ConfigFilePath = configL.getSelectedFile().getPath();
                decryptFileName.setText("File Name: " + configL.getSelectedFile().getName());
                
                try {
                    outputFileRead = File_handling.readFile(configL.getSelectedFile().getPath());
                } catch (IOException ex) {
                    Logger.getLogger(GuiOld.class.getName()).log(Level.SEVERE, null, ex);
                }
                splitAndConvertandSetParameters(outputFileRead);
            }


            //decryption
        }
        if (e.getSource() == decryptButton) {
            char[] password = (pswField.getText()).toCharArray();//converts the thing into a Char array
            String tempdir = workingdir+ "/" + FileName.replace(".enc", "");
            //selecting everything to use
            //salt
            if(saltField.getText()== ""){
                tempsalt = curSalt;
            } else {
                tempsalt = StringToByteArray(saltField.getText());
            }
            //IV
            if(ivField.getText()== ""){
                tempiv = curIV;
            } else {
                tempiv = StringToByteArray(ivField.getText());
            }

            //actual decryption
            try {
                DecriptionFiles(password, path, tempdir, tempsalt, tempiv);
            //file deletion 
            //the actual File 
            if(deleteFile.isSelected() == true){
                System.out.println("deleting the original File: "+ path);
                deleteFile(path);
            }
            //the config
            if(deleteConf.isSelected() == true){
                System.out.println("Deleting the config File: "+ ConfigFilePath);
                deleteFile(ConfigFilePath);               
            }
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | InvalidAlgorithmParameterException ex) {
                Logger.getLogger(GuiOld.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(frame, "Something went wrong during runtime\nplease check the Application logs", "Error", JOptionPane.PLAIN_MESSAGE);
                statusL.setText("Error");
            }
            //clears out all the info that is saved
        }
        if (e.getSource() == clearInfo) {
            
            //this is just temporary will be replaced with the keyfields
            curSalt = null;
            curIV = null;
            //clearing all the keyfields
            saltField.setText("");
            ivField.setText("");
            pswField.setText("password");            
        }//saves all of the info to a file
        if (e.getSource() == saveConfig) {
            String outSt = creatingOutputString();
            try {
                File_handling.StringWriter(outSt, outFilePath());
            } catch (IOException ex) {
                Logger.getLogger(GuiOld.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getSource() == changeColor) {
            String currentTheme = changeColor.getText();
            if (currentTheme == "Dark Mode") {
                changeColor.setText("Light Mode");
                background = dark;
                foreground = Color.WHITE;
            } else {
                changeColor.setText("Dark Mode");
                background = Color.WHITE;
                foreground = dark;
                //test if I just update the background manually if it will work
            }
            repaintWindow();
        }
        if(e.getSource() == settingsButton){
            //this removes all the old components and then adds new ones that are there for the Menu
            removeDefaultPanelItems();
            menuePanelComp();
        }
        if(e.getSource() == closeMenueM){
            removeSettingsMenu();
            addDefaultPanelItems();
        }
    }

    // generates a random salt using the system native RNG (this can lead to different generations depending on system and what is used)
    private byte[] saltGen() throws NoSuchAlgorithmException 
    {
        SecureRandom sr;
        try {
            sr = SecureRandom.getInstance("NativePRNG"); //Slower and uses the system native defined RNG generator. (Use "SHA1PRNG" if you want something more consistent or faster)
        } catch (NoSuchAlgorithmException e) {
            sr = SecureRandom.getInstanceStrong();
        }
        byte[] bytes = new byte[16];
        sr.nextBytes(bytes);
        return bytes;
    }
    //converts everythign into a single string that can be writen out to a file
    public String creatingOutputString() {
        if (curSalt == null | pswField.getText() == null | curIV ==null) {
            JOptionPane.showMessageDialog(frame,
                    "There was a fatal flaw",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE);
        } else {//password , salt, IV
            return pswField.getText() + "|" + Arrays.toString(curSalt) + "|" + Arrays.toString(curIV);
        }
        return "There was a mistake";
    }

    //writes out to a file

    //gives out the path for the file also generates the random name that is going to be used
    public String outFilePath() {
        Random rand = new Random();
        int n = rand.nextInt(100000);
        String dir = System.getProperty("user.dir");
        return dir + "/" + "SavedEncryptionData" + Integer.toString(n) + ".txt";
    }


    //splitting the thing into different parts
    public void splitAndConvertandSetParameters(String inputString) {
        String[] split1 = inputString.split(Pattern.quote("|"));//return the a list with everything separated //have to do this because this uses regular expression so this means smt
        //the only thing left is to convert the byte
        //setting the password
        pswField.setText(split1[0]);
        //converting these things to arrays and setting them appropriately
        //so we remove all the brackets before we open this I can reuse the other part
        
        String thissalt =  split1[1].replaceAll(Pattern.quote("]"),"");
        thissalt =  thissalt.replaceAll(Pattern.quote("["),"");
        
        saltField.setText(thissalt);//spits out errors evn thought it whould work 
        
        //the same thing as abouve but for the IV 
        String thisiv =  split1[2].replaceAll(Pattern.quote("["),"");
        thisiv =  thisiv.replaceAll(Pattern.quote("]"),"");
        
        ivField.setText(thisiv);
        
        //ima just leave this here as a backup but this is not acutally need and could be removed 
        curSalt = StringToByteArray(thissalt);
        curIV = StringToByteArray(thisiv);
    }
    
    //converts smt to a bytearry used for iv, salt
    public byte[] StringToByteArray(String inputString) {
        inputString = inputString.replaceAll(" ", "");
        String[] split2 = inputString.split(Pattern.quote(","));
        byte[] bytes = new byte[split2.length];

        for (int i = 0; i < split2.length; i++) {
            bytes[i] = Byte.parseByte(split2[i]);
        }

        return bytes;
    }
    //the File deletion
    public void deleteFile(String Filepath){
        File toDel = new File(Filepath);
        if (toDel.delete()){
            System.out.println("success with deletion ");
        }else{
            System.out.println("Failed to delete the File");
            JOptionPane.showMessageDialog(frame,
                    "There was a fatal flaw",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
    public void repaintWindow(){
        panel.setBackground(background);
        panel.setForeground(foreground);

        encryptButton.setBackground(background);
        encryptButton.setForeground(foreground);

        decryptButton.setBackground(background);
        decryptButton.setForeground(foreground);

        openFile.setBackground(background);
        openFile.setForeground(foreground);

        help.setBackground(background);
        help.setForeground(foreground);

        showAllINfo.setBackground(background);
        showAllINfo.setForeground(foreground);

        loadConfigButton.setBackground(background);
        loadConfigButton.setForeground(foreground);

        clearInfo.setBackground(background);
        clearInfo.setForeground(foreground);

        saveConfig.setBackground(background);
        saveConfig.setForeground(foreground);

        changeColor.setBackground(background);
        changeColor.setForeground(foreground);

        deleteConf.setBackground(background);
        deleteConf.setForeground(foreground);

        deleteFile.setBackground(background);
        deleteFile.setForeground(foreground);

        titleE.setBackground(background);
        titleE.setForeground(foreground);

        statusL.setBackground(background);
        statusL.setForeground(foreground);

        statusFL.setBackground(background);
        statusFL.setForeground(foreground);

        statusFNameL.setBackground(background);
        statusFNameL.setForeground(foreground);

        txtF.setBackground(background);
        txtF.setForeground(foreground);

        decryptFileName.setBackground(background);
        decryptFileName.setForeground(foreground);

        saltL1.setBackground(background);
        saltL1.setForeground(foreground);

        titleD.setBackground(background);
        titleD.setForeground(foreground);

        IVL1.setBackground(background);
        IVL1.setForeground(foreground);

        decryptFile.setBackground(background);
        decryptFile.setForeground(foreground);

        statusFL.setBackground(background);
        statusFL.setBackground(foreground);

        settingsButton.setBackground(background);
        settingsButton.setForeground(foreground);

        closeMenueM.setBackground(background);
        closeMenueM.setForeground(foreground);


        UI.put("OptionPane.background", background);
        UI.put("OptionPane.font", foreground);
        UI.put("PopupMenu.font", foreground);
        UI.put("Panel.background", background);
        UI.put("setForeground", foreground);
    }
    //this should remove add all the default items to the panel
    private void addDefaultPanelItems(){
        panel.add(IVL1);
        panel.add(decryptFileName);
        panel.add(decryptFile);
        panel.add(titleD);
        panel.add(titleE);
        panel.add(statusFL);
        panel.add(saltL1);
        panel.add(txtF);
        panel.add(statusL);
        panel.add(statusFNameL);
        panel.add(ivField);
        panel.add(saltField);
        panel.add(pswField);
        panel.add(changeColor);
        panel.add(saveConfig);
        panel.add(clearInfo);
        panel.add(loadConfigButton);
        panel.add(help);
        panel.add(showAllINfo);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(openFile);
        panel.add(settingsButton);
        //there to refresh the panel
        panel.repaint();
    }
    //this removes all the default components
    private void removeDefaultPanelItems(){
        panel.remove(IVL1);
        panel.remove(decryptFileName);
        panel.remove(decryptFile);
        panel.remove(titleD);
        panel.remove(titleE);
        panel.remove(statusFL);
        panel.remove(saltL1);
        panel.remove(txtF);
        panel.remove(statusL);
        panel.remove(statusFNameL);
        panel.remove(ivField);
        panel.remove(saltField);
        panel.remove(pswField);
        panel.remove(changeColor);
        panel.remove(saveConfig);
        panel.remove(clearInfo);
        panel.remove(loadConfigButton);
        panel.remove(help);
        panel.remove(showAllINfo);
        panel.remove(encryptButton);
        panel.remove(decryptButton);
        panel.remove(openFile);
        panel.remove(settingsButton);

        //there to refresh the panel
        panel.repaint();
    }
    //this defines and add all the components for the settings menu
    private void menuePanelComp(){
        closeMenueM.setBounds(300,350,100,25);
        closeMenueM.addActionListener(this);
        panel.add(closeMenueM);

        // adding the first checkmark
        deleteConf.setBounds(20, 20,200,25);
        deleteConf.addActionListener(this);
        panel.add(deleteConf);
        //add the second checkmark
        deleteFile.setBounds(20, 50,200,25);
        deleteFile.addActionListener(this);
        panel.add(deleteFile);

        panel.repaint();
    }
    private void removeSettingsMenu(){
        panel.remove(closeMenueM);
        panel.remove(deleteConf);
        panel.remove(deleteFile);
        panel.repaint();
    }


}

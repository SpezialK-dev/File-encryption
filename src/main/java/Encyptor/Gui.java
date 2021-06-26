
package Encyptor;

import static Encyptor.cipher.EncAndDec.deEcripedFiles;
import static Encyptor.cipher.EncAndDec.encryptedFile;
import Encyptor.cipher.Output;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

public class Gui extends Main implements ActionListener{

    //all of the buttons the program will have
    JButton decripB = new JButton("Decript");
    JButton encriptB = new JButton("Encript");
    JButton openFile = new JButton("open File");
    JButton help = new JButton("Help");
    JButton showAllINfo = new JButton("all Info");
    JButton loadingFforD = new JButton("load config for Dec");
    JButton clearInfo = new JButton("clear Info");
    JButton saveConfig = new JButton("save Config");

    // all of the status text like what file got choosen and shit like that
    //encription labels
    JLabel titelE = new JLabel("encription Files");
    JLabel statusFL = new JLabel("no file selected");
    JLabel statusL = new JLabel("No file Status");
    JLabel statusFNameL = new JLabel("no file name");
    JLabel txtF = new JLabel("Key: ");//pasword
    //decription lables
    JLabel decripFile  = new JLabel("no file selected");//file path
    JLabel decripFileName = new JLabel("no file name");//fiole name
    JLabel saltL1 = new JLabel("decription Salt: ");
    JLabel titelD = new JLabel("decription Files:");

    //creates the panel and Frame
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();


    //other generell Variables
    private String path;
    private String Key ; //the encyption key
    //rework the way the program interacts with the os and where it saves data
    private String dir = System.getProperty("user.dir");
    private String workingdir = System.getProperty("user.dir");
    private byte[] curSalt;
    private byte[] curIV;
    private Output out;
    private String FileName;

    //text fields
    JTextField pswField = new JTextField("password",30);//replace "Password with a randomly generated password(using somethign like salt gen or somethign else wich is strong and not really reversable)"
    JTextField keyField = new JTextField(260);//the password field
    JTextField saltField = new JTextField();

    public Gui(){
        this.keyField = new JTextField(260);

        //generell frame settings
        frame.setSize(700, 420);//valous 500,270
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setTitle("Encryptor / decryptor");
        frame.setResizable(false);
        //pannel settings
        panel.setLayout(null);


        //adding all of the buttons
        //open file button
        openFile.setBounds(40,170,100, 25);
        openFile.addActionListener(this);
        panel.add(openFile);
        //decription button
        decripB.setBounds(160,170,100,25);
        decripB.addActionListener(this);
        panel.add(decripB);
        //encript Button
        encriptB.setBounds(280, 170,100,25);
        encriptB.addActionListener(this);
        panel.add(encriptB);
        //info Button
        showAllINfo.setBounds(40, 130,100,25);
        showAllINfo.addActionListener(this);
        panel.add(showAllINfo);
        //help button
        help.setBounds(520, 170,120,25);
        help.addActionListener(this);
        panel.add(help);
        //load config button
        loadingFforD.setBounds(160, 130,220,25);
        loadingFforD.addActionListener(this);
        panel.add(loadingFforD);
        //Salt clear Button
        clearInfo.setBounds(520,130,120,25);
        clearInfo.addActionListener(this);
        panel.add(clearInfo);
        //saves the config to your harddrive so you can send it to someone
        saveConfig.setBounds(520,210,120,25);
        saveConfig.addActionListener(this);
        panel.add(saveConfig);
        //adding the text field to enter in the key
        pswField.setBounds(130,210,300,25);
        panel.add(pswField);
        //keyfield for the salt will need more stuff backend but this is just a non working intehration so that there is something there and not just somethign empty
        saltField.setBounds(130,270,300,25);
        panel.add(saltField);


        //adding the text thingis
        //just what file you have selected
        statusFL.setBounds(20,50,600,25);
        panel.add(statusFL);
        statusFNameL.setBounds(20,70,380,25);
        panel.add(statusFNameL);
        //the status of if encryition is finished and shit
        statusL.setBounds(20,90,200,25);
        panel.add(statusL);
        //adding the text for the key field
        txtF.setBounds(20,210,40,25);
        panel.add(txtF);
        // add the decription text field
        //the label for th decription Salt
        saltL1.setBounds(20,270,120,25);
        panel.add(saltL1);
        //adding all the titels
        //encription
        titelE.setBounds(20,20,600,25);
        panel.add(titelE);
        //decription Titel
        titelD.setBounds(20, 300,300,25);
        panel.add(titelD);
        //adding everything for decription
        //file path
        decripFile.setBounds(20,330,600,25);
        panel.add(decripFile);
        //file name
        decripFileName.setBounds(20,360,600,25);
        panel.add(decripFileName);
        //the titel for the decription


        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == openFile){
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(panel);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                //setting all of the statuses and giving out the path for everything
                statusFL.setText("File Path: "+ chooser.getSelectedFile().getPath());
                statusFNameL.setText("File Name: " + chooser.getSelectedFile().getName());
                path = chooser.getSelectedFile().getPath();
                FileName = (chooser.getSelectedFile().getName());

            }
        }if(e.getSource() == encriptB){
            //if stuff is empty give out an Error
            if(pswField.getText() == ""|| path == ""){//redo this to make a popup
                JOptionPane.showMessageDialog(frame,"Something went wrong during runtime\nplease check the Application logs","Error", JOptionPane.PLAIN_MESSAGE);
            }else{// there is stuff there and then try that
                char[] pasw = (pswField.getText()).toCharArray();//converts the thing into a Char array
                String tempdir = workingdir+ "/" +  FileName + ".enc";//creates the new name of the file
                try {//because the shit might fabyte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();il so there is a try statmen
                     //returns a new type that has the salt and IV stored
                    out = encryptedFile(pasw,path, tempdir,statusL,saltGen());//actually encryptes the thing and creates a new file with this
                    //converting the output into theire own arrays and shit
                    curSalt = out.retSalt();
                    curIV = out.retIv();
                    out =  null;
                // catch all of the exeptions
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | InvalidParameterSpecException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frame,"Something went wrong during runtime\nplease check the Application logs","Error", JOptionPane.PLAIN_MESSAGE);
                    statusL.setText("Error");
                }
            }
        }if(e.getSource()== showAllINfo){
            JOptionPane.showMessageDialog(frame,
                "All information"
                    +"\nSalt : "+ Arrays.toString(curSalt)
                    +"\nCurrent Password : " + pswField.getText()
                    +"\nCurrent Starting paramenter : " + Arrays.toString(curIV)
                    +"\n"
                    +"\n"
                    +"\n",
                    "Information",
            JOptionPane.PLAIN_MESSAGE);
            }
        if(e.getSource() == help){
            JOptionPane.showMessageDialog(frame,
                    "this is an help menue"
                        +"\nthis shows you how to use this program"
                        +"\n"
                        +"\nthe Key Field should be used to enter the password that the user wants to use to encript/decript the file"
                        +"\nthe decription Key could be enterd into the program if knowen "
                        +"\nthe password will be used with a random salt to generate the key for encription"
                        +"\nall info shows all of the information about the current salt and password"
                        +"\nload file should be used to load a file to de- or encript"
                        +"\nclear Info clears salt and IV"
                        +"\nthe password and salt can be reused to decript something imidialy"
                        +"\nso the salt stay in cach as long as the app is open "
                        +"\n"
                        +"\n",
                    "Help",
                    JOptionPane.PLAIN_MESSAGE);
            //load the file for decription and also sets all of the files
        }if(e.getSource()== loadingFforD){
            String outputFileRead = "";
            JFileChooser configL = new JFileChooser();
            int returnVal2 = configL.showOpenDialog(panel);
            if(returnVal2 == JFileChooser.APPROVE_OPTION){
                decripFile.setText("File Path: "+ configL.getSelectedFile().getPath());
                decripFileName.setText("File Name: " + configL.getSelectedFile().getName());
                try {
                    outputFileRead = readFile(configL.getSelectedFile().getPath());
                } catch (IOException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
                split1(outputFileRead);
            }


            //decription
        }if(e.getSource() == decripB){
            char[] pasw = (pswField.getText()).toCharArray();//converts the thing into a Char array
            String Filename2 = FileName.replaceAll(Pattern.quote(".enc"), "");
            String tempdir = workingdir+ "/" +  Filename2;
            try {
                deEcripedFiles(pasw,path,tempdir,statusL,curSalt,curIV);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException| IllegalBlockSizeException |BadPaddingException |InvalidKeySpecException |InvalidAlgorithmParameterException  ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(frame,"Something went wrong during runtime\nplease check the Application logs","Error", JOptionPane.PLAIN_MESSAGE);
                statusL.setText("Error");
            }


        //clears out all of the info that is saved
        }if(e.getSource() == clearInfo){
            curSalt = null;
            curIV = null;

        }if(e.getSource() == saveConfig){
            String outSt = creatingOutputString();
            try {
                Stringwriter(outSt,outFilePath());
            } catch (IOException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //generates a random salt using the system nativ RNG (this can lead to different generations depending on system and waht is used)
    private byte[] saltGen() throws NoSuchAlgorithmException{
        SecureRandom sr = SecureRandom.getInstance("NativePRNG");//slowe and uses the system nativ defined RNG generator use "SHA1PRNG" if you want smth more consistent or faster
	byte[] bytes = new byte[16];
	sr.nextBytes(bytes);
        return  bytes;
    }
    public String creatingOutputString(){
        if(curSalt == null|pswField.getText() == null){
                    JOptionPane.showMessageDialog(frame,
                    "There was a fatal flaw",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE);
        }else{//password , salt, IV
            String outputString = pswField.getText()+ "|" + Arrays.toString(curSalt) + "|" + Arrays.toString(curIV) ;
            return outputString;
        }
        return "there was a mistake";
    }
    //writes out to a file
    public void Stringwriter(String intput, String Filepath) throws IOException{
    File outFile = new File(Filepath);
    BufferedWriter outwriter = new BufferedWriter(new FileWriter(Filepath));
    outwriter.write(intput);
    outwriter.close();
    }
    //this should return a String to a filepath where it gives out a random 5 digit number wich is used as a
    public String outFilePath(){
        Random rand = new Random();
        int n = rand.nextInt(100000);
        String fpath = dir + "/" + "SavedEncriptionData" + Integer.toString(n) + ".txt";
        return fpath;
    }
    //file reader that gives out a single string
    public String readFile(String filePath) throws IOException{
        Path path = Path.of(filePath);
        String outString = Files.readString(path);
        return outString;
    }
    //spliting the thing into different parts
    public void split1(String inputString){
        String[] split1 = inputString.split(Pattern.quote("|"));//return the a list with everythign seperated //have to do this because this uses reguar expression so this means smt
        //the only thing left is to cinvert the byte
        //setting the password
        Key = split1[0];
        pswField.setText(split1[0]);
        //converting these things to arrays and setting them approiatly
        curSalt = StringTobyteArray(split1[1]);
        curIV = StringTobyteArray(split1[2]);
    }
    public byte[] StringTobyteArray(String inputString){
        //idk if there is a better way to do this
        inputString = inputString.replaceAll(Pattern.quote("["), "");
        inputString = inputString.replaceAll(Pattern.quote("]"), "");
        inputString = inputString.replaceAll(" ", "");
        System.out.println(inputString);
        String[] split2 = inputString.split(Pattern.quote(","));
        byte[] bytes = new byte[split2.length];

        for(int i = 0; i < split2.length; i++) {
            bytes[i] = Byte.parseByte(split2[i]);
        }

    return bytes;
    }

}


package Encyptor;

import Encyptor.cipher.EncAndDec;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class Gui extends Main implements ActionListener{
    
    //all of the buttons the program will have
    JButton decripB = new JButton("Decript");
    JButton encriptB = new JButton("Encript");
    JButton openFile = new JButton("open File");
    JButton help = new JButton("Help");
    JButton showAllINfo = new JButton("all Info");
    JButton loadingFforD = new JButton("load config for Dec");
    JButton clearSalt = new JButton("clear Salt");
    
    // all of the status text like what file got choosen and shit like that
    //encription labels
    JLabel titelE = new JLabel("encription Files");
    JLabel statusFL = new JLabel("no file selected");
    JLabel statusL = new JLabel("No file Status");
    JLabel statusFNameL = new JLabel("no file name");
    JLabel txtF = new JLabel("Key: ");
    //decription lables
    JLabel keyL = new JLabel("decription Key: ");
    JLabel decripFile  = new JLabel("no file selected");//file path
    JLabel decripFileName = new JLabel("no file name");//fiole name
    JLabel saltL1 = new JLabel("decription Salt: ");
    JLabel titelD = new JLabel("decription Files:");
    
    //text fields
    JTextField pswField = new JTextField("Password",30);//replace "Password with a randomly generated password(using somethign like salt gen or somethign else wich is strong and not really reversable)"
    JTextField keyField = new JTextField(260);//the password field 
    JTextField saltField = new JTextField();
    
    //creates the panel and Frame
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    
    
    //other generell Variables 
    private String path;
    private String Key; //the encyption key
    private String dir = System.getProperty("user.dir");
    private byte[] curSalt;
    
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
        clearSalt.setBounds(520,130,120,25);
        clearSalt.addActionListener(this);
        panel.add(clearSalt);
        
        //adding the text field to enter in the key 
        pswField.setBounds(130,210,300,25);
        panel.add(pswField);
        //adding the field for where a user could enter a secret key that then would be used to decript the thingi 
        keyField.setBounds(130,240,300,25);
        panel.add(keyField);
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
        keyL.setBounds(20,240,120,25);
        panel.add(keyL);
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
                dir = (dir + chooser.getSelectedFile().getName()+".enc");
                
            }
        }if(e.getSource() == encriptB){
            //if stuff is empty give out an Error
            if(pswField.getText() == ""|| path == ""){//redo this to make a popup 
                JOptionPane.showMessageDialog(frame,"Something went wrong during runtime\nplease check the Application logs","Error", JOptionPane.PLAIN_MESSAGE);
            }else{// there is stuff there and then try that
                char[] pasw = (pswField.getText()).toCharArray();//converts the thing into a Char array
                try {//because the shit might fabyte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();il so there is a try statmen
                    //tring secretKey, String fileInputPath, String fileOutPath, JLabel status //retunr the salt that was used
                    curSalt = Encyptor.cipher.EncAndDec.encryptedFile(pasw,path, dir,statusL,saltGen());//actually encryptes the thing and creates a new file with this 
                    // catch all of the exeptions
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | InvalidParameterSpecException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frame,"Something went wrong during runtime\nplease check the Application logs","Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }if(e.getSource()== showAllINfo){ 
            JOptionPane.showMessageDialog(frame,
                "All information"
                    +"\nSalt : "+ Arrays.toString(curSalt)
                    +"\nCurrent Password : " + pswField.getText()
                    +"\n"
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
                        +"\nthe Key Field should be used to enter the password that the user wants to use to encript the file"
                        +"\nthe decription Key could be enterd into the program if knowen "
                        +"\nthe password will be used with a random salt to generate the key for encription"
                        +"\nall info shows all of the information about the current salt and password"
                        +"\nload file should be used to load a file to de- or encript"
                        +"\nclear Salt overwrites the variable for the salt"
                        +"\nthe password and salt can be reused to decript something imidialy"
                        +"\nso the salt stay in cach as long as the app is open "
                        +"\n"
                        +"\n",
                    "Help",
                    JOptionPane.PLAIN_MESSAGE);
        }if(e.getSource()== loadingFforD){
            JFileChooser configL = new JFileChooser();
            int returnVal2 = configL.showOpenDialog(panel);
            if(returnVal2 == JFileChooser.APPROVE_OPTION){
                System.out.println("feature currently unavailable  ");
            } 
        }if(e.getSource() == decripB){
            creatingOutputString();
        }if(e.getSource() == clearSalt){
            curSalt = null;
        }
    }
    //generates a random salt using the system nativ RNG (this can lead to different generations depending on system and waht is used)
    private byte[] saltGen() throws NoSuchAlgorithmException{
        SecureRandom sr = SecureRandom.getInstance("NativePRNG");//slowe and uses the system nativ defined RNG generator use "SHA1PRNG" if you want smth more consitend or faster
	byte[] bytes = new byte[16];
	sr.nextBytes(bytes);
        return  bytes;
    }
    public String creatingOutputString(){
        if(curSalt == null|pswField.getText() == null){
                    JOptionPane.showMessageDialog(frame, 
                    "There was a fatal flaw",
                    "Erorre",
                    JOptionPane.PLAIN_MESSAGE);
        }else{
            String outputString = pswField.getText()+ "|" + Arrays.toString(curSalt) ;
            System.out.println(outputString);
            return outputString;
        }
        return "there was a mistake";
    }
/*
    public String convertbyteArrytoString(byte[] input){
        int n = 0;
        String output = "";
        while(n < input.length){
            output = (input[n]) + ",";
            n++;
        }
        return output;
    }*/
    /*
    add to gui
    - a way to see and add a key to decript it 
    - a way to show the key 
    - a button remove every pice from ram and stuff to wipe your pc from the genreation
    */
}

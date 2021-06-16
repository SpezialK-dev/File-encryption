
package Encyptor;

import Encyptor.cipher.EncAndDec;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
    
    // all of the status text like what file got choosen and shit like that
    JLabel statusFL = new JLabel("no file selected");
    JLabel statusL = new JLabel("No file Status");
    JLabel statusFNameL = new JLabel("no file name");
    JLabel txtF = new JLabel("Key: ");
    
    //text fields
    JTextField keyField = new JTextField("Password",30);//replace "Password with a randomly generated password(using somethign like salt gen or somethign else wich is strong and not really reversable)"
    
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    
    
    //other generell Variables 
    private String path;
    private String Key; //the encyption key
    private String dir = System.getProperty("user.dir");
    
    public Gui(){
        
        //generell frame settings
        frame.setSize(700, 270);//valous 500,270
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
        showAllINfo.setBounds(400, 170,100,25);
        showAllINfo.addActionListener(this);
        panel.add(showAllINfo);
        //help button
        help.setBounds(520, 170,100,25);
        help.addActionListener(this);
        panel.add(help);
        
        //adding the text field to enter in the key 
        keyField.setBounds(55,210,300,25);
        panel.add(keyField);
        
        //adding the text thingis 
        //just what file you have selected
        statusFL.setBounds(20,20,600,25);
        panel.add(statusFL);
        statusFNameL.setBounds(20,40,380,25);
        panel.add(statusFNameL);
        //the status of if encryition is finished and shit 
        statusL.setBounds(20,60,200,25);
        panel.add(statusL);
        //adding the text for the key field
        txtF.setBounds(20,210,40,25);
        panel.add(txtF);
        
                
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
            if(keyField.getText() == ""|| path == ""){//redo this to make a popup 
                System.out.println("Error not being able to find shit  ");
            }else{// there is stuff there and then try that
                char[] pasw = (keyField.getText()).toCharArray();//converts the thing into a Char array
                try {//because the shit might fabyte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();il so there is a try statment
                    System.out.println(dir);
                    //tring secretKey, String fileInputPath, String fileOutPath, JLabel status
                    Encyptor.cipher.EncAndDec.encryptedFile(pasw,path, dir,statusL,saltGen());//actually encryptes the thing and creates a new file with this 
                    // catch all of the exeptions
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidParameterSpecException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }if(e.getSource()== showAllINfo){
            try {//can be removed when instead of a salt gen we have a variable that actually displays the current salt
                //creates an info pannel
                JOptionPane.showMessageDialog(frame,
                        "All information"
                                +"\nSalt : "+ Arrays.toString(saltGen())
                                +"\nCurrent Password : " + keyField.getText()
                                +"\n"
                                +"\n"
                                +"\n"
                                +"\n",
                        "Information",
                        JOptionPane.PLAIN_MESSAGE);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if(e.getSource() == help){
            JOptionPane.showMessageDialog(frame, 
                    "this is an help menue"
                        +"\n this shows you how to use this program"
                        +"\n"
                        +"\n"
                        +"\n"
                        +"\n"
                        +"\n"
                        +"\n"
                        +"\n"
                        +"\n"
                        +"\n",
                    "Help",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
    //generates a random salt using the system nativ RNG (this can lead to different generations depending on system and waht is used)
    private byte[] saltGen() throws NoSuchAlgorithmException{
        SecureRandom sr = SecureRandom.getInstance("NativePRNG");//slowe and uses the system nativ defined RNG generator use "SHA1PRNG" if you want smth more consitend or faster
	byte[] bytes = new byte[64];
	sr.nextBytes(bytes);
        return  bytes;
    }
}

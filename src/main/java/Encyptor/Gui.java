
package Encyptor;

import Encyptor.cipher.EncAndDec;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    
    // all of the status text like what file got choosen and shit like that
    JLabel statusFL = new JLabel("no file selected");
    JLabel statusL = new JLabel("No file Status");
    JLabel statusFNameL = new JLabel("no file name");
    JLabel txtF = new JLabel("Key: ");
    
    //text fields
    JTextField keyField = new JTextField(30);
    
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    
    
    //other generell Variables 
    private String path;
    private String Key; //the encyption key
    private String dir = System.getProperty("user.dir");
    
    public Gui(){
        
        //generell frame settings
        frame.setSize(400, 210);//valous 500,270
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setTitle("Encryptor / decryptor");
        frame.setResizable(false);
        //pannel settings
        panel.setLayout(null);
        
        
        //adding all of the buttons
        //open file button
        openFile.setBounds(40,100,100, 25);
        openFile.addActionListener(this);
        panel.add(openFile);
        //decription button 
        decripB.setBounds(160,100,100,25);
        decripB.addActionListener(this);
        panel.add(decripB);
        //encript Button
        encriptB.setBounds(280, 100,100,25);
        encriptB.addActionListener(this);
        panel.add(encriptB);
        
        //adding the text field to enter in the key 
        keyField.setBounds(55,140,300,25);
        panel.add(keyField);
        
        //adding the text thingis 
        //just what file you have selected
        statusFL.setBounds(20,20,380,25);
        panel.add(statusFL);
        statusFNameL.setBounds(20,40,380,25);
        panel.add(statusFNameL);
        //the status of if encryition is finished and shit 
        statusL.setBounds(20,60,200,25);
        panel.add(statusL);
        //adding the text for the key field
        txtF.setBounds(20,140,40,25);
        panel.add(txtF);
        
                
        frame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == openFile){
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(panel);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                statusFL.setText("File Path: "+ chooser.getSelectedFile().getPath());
                statusFNameL.setText("File Name: " + chooser.getSelectedFile().getName());
                path = chooser.getSelectedFile().getPath();
                dir = (dir + chooser.getSelectedFile().getName() +".enc");
                
            }
        }if(e.getSource() == encriptB){
            if(keyField.getText() == ""&& path == ""){//redo this to make a popup 
                System.out.println("Error not being able to find shit  ");
            }else{
                try {
                    //tring secretKey, String fileInputPath, String fileOutPath, JLabel status
                    Encyptor.cipher.EncAndDec.encryptedFile(keyField.getText(),path, dir,statusL);
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public Byte[] saltGen(){
      
    }
}

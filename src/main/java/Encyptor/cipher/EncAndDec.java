
package Encyptor.cipher;

import Encyptor.Gui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JLabel;

public class EncAndDec extends Gui{
    public EncAndDec(){
    }
    //encriptor
public static void encryptedFile(char[] password, String fileInputPath, String fileOutPath, JLabel status,byte[] salt)
throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
    //
    status.setText("Converting Key");
    //creates a key that will later be used to encryped everything 
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);//uses 65536 retreys and 256 bit encryption
    SecretKey tmp = factory.generateSecret(spec);
    SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");//uses AES

    status.setText("reading File");
    //reads the input of the file as an array
    File fileInput = new File(fileInputPath);
    FileInputStream inputStream = new FileInputStream(fileInput);
    byte[] inputBytes = new byte[(int) fileInput.length()];
    inputStream.read(inputBytes);
    inputStream.close();
    
    status.setText("encyping");
    //the actual cyper 
    //byte[] outputBytes = cipher.doFinal(inputBytes);removed because not working
    //writes the encrypted text out to the file
    
    //output stream
    status.setText("writing to new File");
    File fileEncryptOut = new File(fileOutPath);
    FileOutputStream outputStream = new FileOutputStream(fileEncryptOut);
    //outputStream.write(outputBytes);
    //outputStream.close();

    }
    //decriptor
public static void deEcripedFiles(String secretKey, String fileInputPath, String fileOutPath, JLabel status) 
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException{
        
   //Converting a key
    status.setText("Converting Key");
    SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, key);
        
    status.setText("reading File");
    //reads the input of the file as an array
    File fileInput = new File(fileInputPath);
    FileInputStream inputStream = new FileInputStream(fileInput);
    byte[] inputBytes = new byte[(int) fileInput.length()];
    inputStream.read(inputBytes);
    inputStream.close();
    
    status.setText("encyping and writing to new File");
    //the actual cyper 
    byte[] outputBytes = cipher.doFinal(inputBytes);
    //writes the encrypted text out to the file
    
    //output stream
    File fileDeEncryptOut = new File(fileOutPath);
    FileOutputStream outputStream = new FileOutputStream(fileDeEncryptOut);
    outputStream.write(outputBytes);
    outputStream.close();

        
    } 
}

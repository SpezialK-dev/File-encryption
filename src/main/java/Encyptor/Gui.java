package Encyptor;

import Encyptor.cipher.Output;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import static Encyptor.cipher.EncAndDec.encryptedFile;


//this is the new Gui there to replace the old Jpanel one
public class Gui extends Application{
    //all the Variables for settings
    //this might be really useless
    ImGui gui = new ImGui();
    FileSelector f = new FileSelector();
    //this is the window for all the encryption stuff

    //settingsMenu Variables
    boolean deleteConfAfterUsage = false;
    boolean deleteFileAfterusage = false;

    //EncryptionWindow Variables
    boolean fileOpenerHasbeenOpenENC = false;
    ImString encPswdWindow = new ImString("password",256);

    //the current path to the File
    String currentFilepath = null;

    //the output of the File might need to be replaced
    private Output out ;

    //decryptionWindow Variables

    @Override
    protected void configure(Configuration config) {
        config.setTitle("File-encryption");
    }

    //all the windows that are spawned on top of the layer
    public void process(){
        //calls for all the different windows
        EncryptionWindow();
        decryptionWindow();
        settingsMenu();

        //the current way to implement this bc closing a window does not work that well
        if(fileOpenerHasbeenOpenENC){
            String s = f.openFileDialog("");
            if(s != null){
                //selects the File and closes the Gui
                currentFilepath = s;
                fileOpenerHasbeenOpenENC = !fileOpenerHasbeenOpenENC;
            }
        }

    }

    //all the Methods for the different Windows
    private void EncryptionWindow(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("Encryption",pOpen,ImGuiWindowFlags.MenuBar );
        if (ImGui.beginMenuBar()) {
            //begin drop down menu
            if (ImGui.beginMenu( "File")) {
                if (ImGui.menuItem("Open..", "Ctrl+O")) {
                    fileOpend("test"); }
                if(ImGui.menuItem("Close", "Ctrl+W")) {

                }
                //end Drop down menu
                ImGui.endMenu();
            }
            ImGui.endMenuBar();
            if(ImGui.button("Encrypt")){
                //todo write code for encryption
                //tests if the password field is empty
                if(encPswdWindow.get().trim().length() == 0 || currentFilepath == null){
                    System.out.println("No password was entered or you didn't select a File!");
                }else{
                    char[] char_Password_Arr = encPswdWindow.get().toCharArray();
                    try {
                        out = encryptedFile(char_Password_Arr, currentFilepath, currentFilepath + ".enc", saltGen());
                        //todo make this into a single line that catches all of the statements
                    }catch (NoSuchPaddingException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalBlockSizeException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (BadPaddingException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeySpecException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidParameterSpecException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            if(ImGui.button("Open File selector")){
                //todo this is currently a test function to test out the file chooser
                fileOpenerHasbeenOpenENC =!fileOpenerHasbeenOpenENC;
            }
            //ImGuiInputTextFlags.Password maybe just add to the end of the line to hide password
            if(ImGui.inputTextMultiline("Password: ", encPswdWindow,200, 20)){
            }


        }
        ImGui.end();
    }

    private void decryptionWindow(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("Decryption",pOpen,ImGuiWindowFlags.MenuBar );

        //begin Menu bar code
        if (ImGui.beginMenuBar()) {
            //begin Drop down menu
            if (ImGui.beginMenu( "File")) {
                if (ImGui.menuItem("Open..", "Ctrl+O")) {
                    fileOpend("test"); }
                if (ImGui.menuItem("Close", "Ctrl+W")) {
                }
                ImGui.endMenu();
                //end Drop Down menu
            }
            ImGui.endMenuBar();
            //end menubar
            if(ImGui.button("decrypt")){
                //todo write code for decryption
            }
        }
        ImGui.end();
    }
    //todo fix settings menu
    private void settingsMenu(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("Settings",pOpen);
       //all the Settings

        //Imgui checkboxes are just a fancy button they only detect the state change and nothing else, so you have to treat them as if they were a button
        if (ImGui.checkbox("Delete Config after usage", deleteConfAfterUsage)){
            deleteConfAfterUsage = !deleteConfAfterUsage;
        }
        if(ImGui.checkbox("Delete File after usage", deleteFileAfterusage)){
            deleteFileAfterusage = !deleteFileAfterusage;
        }

        ImGui.end();

    }


    // takes care of File selection
    private void fileOpend(String filenameAndPath){
        ImGui.text("CurrentFile Opened: ");
    }
    //switches a boolean and then returns the switched version
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
}

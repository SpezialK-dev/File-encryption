package Encyptor;

import Encyptor.cipher.Output;
import Encyptor.utils.ImguiWindows.DevWindow;
import Encyptor.utils.ImguiWindows.FileSelector;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import static Encyptor.cipher.EncAndDec.encryptedFile;


public class Gui extends Application{
    //settingsMenu Variables
    boolean deleteConfAfterUsage = false;
    boolean deleteFileAfterusage = false;
    boolean show_hidden_files_Files_Selector = false;
    boolean clear_values_after_usage = false;
    boolean dev_mode_check = false;
    static boolean disable_logging_to_Consol = false;

    //all the Variables for settings
    ImGui gui = new ImGui();
    FileSelector f = new FileSelector();

    static DevWindow devWindow = new DevWindow();
    //this is the window for all the encryption stuff



    //EncryptionWindow Variables
    boolean fileOpenerHasbeenOpenENC = false;
    ImString encPswdWindow = new ImString("password",256);

    //the current path to the File for ENC
    String currentFilepathENC = null;

    //the output of the File might need to be replaced
    private Output out ;

    //decryptionWindow Variables
    String currentFilepathDEC = null;
    boolean fileOpenerHasbeenOpenDEC = false;
    //the Password
    ImString dec_Psw_Window = new ImString("password", 256);
    //the salt value
    ImString dec_Salt_Window = new ImString("", 768);
    //iv
    ImString dec_IV_Window = new ImString("", 250);
    //end decryptionWindow Variables


    //debug Variables


    //end Debug variables

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
                currentFilepathENC = s;
                fileOpenerHasbeenOpenENC = !fileOpenerHasbeenOpenENC;
            }
        }
        //file dialog for the decryption window
        if(fileOpenerHasbeenOpenDEC){
            String s = f.openFileDialog("");
            if(s != null){
                //selects the File and closes the Gui
                currentFilepathDEC = s;
                fileOpenerHasbeenOpenDEC = !fileOpenerHasbeenOpenDEC;
            }
        }
        //dev mode
        if(dev_mode_check){
            devWindow.dev_mode_Window();

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
                    fileOpenerHasbeenOpenENC = !fileOpenerHasbeenOpenENC; }
                if(ImGui.menuItem("Close", "Ctrl+W")) {
                    fileOpenerHasbeenOpenENC = !fileOpenerHasbeenOpenENC;
                }
                //end Drop down menu
                ImGui.endMenu();
            }
            ImGui.endMenuBar();
            if(ImGui.button("Encrypt")){
                //tests if the password field is empty
                if(encPswdWindow.get().trim().length() == 0 || currentFilepathENC == null){
                    Main.write_to_console("No password was entered or you didn't select a File!");
                }else{
                    char[] char_Password_Arr = encPswdWindow.get().toCharArray();
                    try {
                        out = encryptedFile(char_Password_Arr, currentFilepathENC, currentFilepathENC + ".enc", saltGen());
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
                    if(deleteFileAfterusage){
                        Main.write_to_console("deleted File at: " + currentFilepathENC);
                        //todo add the actual removal of the File
                    }
                }

            }
            if(ImGui.button("Open File selector")){
                fileOpenerHasbeenOpenENC =!fileOpenerHasbeenOpenENC;
            }
            //ImGuiInputTextFlags.Password maybe just add to the end of the line to hide password
            if(ImGui.inputTextMultiline(": Password", encPswdWindow,200, 20)){
            }
            if(ImGui.button("export Settings")){

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
                    fileOpenerHasbeenOpenDEC =!fileOpenerHasbeenOpenDEC; }
                if (ImGui.menuItem("Close", "Ctrl+W")) {
                    fileOpenerHasbeenOpenDEC =!fileOpenerHasbeenOpenDEC;
                }
                ImGui.endMenu();
                //end Drop Down menu
            }
            ImGui.endMenuBar();
            //end menubar
            if(ImGui.button("decrypt")){
                //todo write code for decryption
                //to check that it is none null
                if(dec_Psw_Window.get().trim().length() != 0  && dec_Psw_Window.get().trim().length() != 0  && dec_IV_Window.get().trim().length() != 0 && currentFilepathDEC != null){
                    Main.write_to_console("all Fields were filled! and a file was selected");

                }
            }
            if(ImGui.button("Open File selector")){
                //todo maybe replace this with some different variables
                fileOpenerHasbeenOpenDEC =!fileOpenerHasbeenOpenDEC;
            }

            //the Password field
            if(ImGui.inputTextMultiline(": Password",dec_Psw_Window ,200, 20)){

            }
            //The Salt field
            if(ImGui.inputTextMultiline(": Salt",dec_Salt_Window ,200, 20)){
            }

            //the IV field
            if(ImGui.inputTextMultiline(": IV", dec_IV_Window, 200,20)){

            }
            if(ImGui.button("Import Settings")){

            }

        }
        ImGui.end();
    }
    private void settingsMenu(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("Settings",pOpen);
       //all the Settings

        //Imgui checkboxes are just a fancy button they only detect the state change and nothing else, so you have to treat them as if they were a button
        if (ImGui.checkbox("Delete Config after usage", deleteConfAfterUsage)){
            deleteConfAfterUsage = !deleteConfAfterUsage;
            Main.write_to_console("updated deleteConfAfterUsage to: " + deleteConfAfterUsage);
        }
        if(ImGui.checkbox("Delete File after usage", deleteFileAfterusage)){
            deleteFileAfterusage = !deleteFileAfterusage;
        }
        if(ImGui.checkbox("Show Hidden Files in File Selector", show_hidden_files_Files_Selector)){
            show_hidden_files_Files_Selector = !show_hidden_files_Files_Selector;
        }
        if(ImGui.checkbox("clear En /-Decryption values after usage",clear_values_after_usage)){
            clear_values_after_usage = !clear_values_after_usage;
        }if(ImGui.checkbox("Developer Mode",dev_mode_check )){
            dev_mode_check = !dev_mode_check;
        }if(ImGui.checkbox("Disable Logging to console",disable_logging_to_Consol )){
            disable_logging_to_Consol = !disable_logging_to_Consol;
            Main.update_Logging_to_console_Boolean(disable_logging_to_Consol);
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
    public static DevWindow getDevWindow() {
        return devWindow;
    }

}

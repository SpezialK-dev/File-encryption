package Encyptor;

import Encyptor.cipher.Output;
import Encyptor.utils.File_handling;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

import static Encyptor.cipher.EncAndDec.DecriptionFiles;
import static Encyptor.cipher.EncAndDec.encryptedFile;


public class Gui extends Application{
    //this is the variable that will be used to decide
    final String string_for_file_manager = "Waiting to Select File";
    //settingsMenu Variables
    boolean deleteConfAfterUsage = false;
    boolean deleteFileAfterusage = false;
    boolean show_hidden_files_Files_Selector = false;
    boolean clear_values_after_usage = false;
    boolean dev_mode_check = false; //default false
    static boolean disable_logging_to_Consol = false;

    int[] test = new int[100];

    //all the Variables for settings
    ImGui gui = new ImGui();
    FileSelector f = new FileSelector();

    static DevWindow devWindow = new DevWindow();
    //this is the window for all the encryption stuff

    //EncryptionWindow Variables

    ImString encPswdWindow = new ImString("password",256);

    //the current path to the File for ENC
    String currentFilepathENC = null;

    //the output of the File might need to be replaced
    private Output out = null;

    //decryptionWindow Variables
    String currentFilepathDEC = null;
    //the Password
    ImString dec_Psw_Window = new ImString("password", 256);
    //the salt value
    ImString dec_Salt_Window = new ImString("", 768);
    //iv
    ImString dec_IV_Window = new ImString("", 250);

    //this is the path to load a config with
    String pathToConfig = null;
    //end decryptionWindow Variables
    Boolean import_started = false;

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
        if(currentFilepathENC == string_for_file_manager){
            String s = f.openFileDialog("");
            if(s != null){
                //selects the File and closes the Gui
                currentFilepathENC = s;
                Main.write_to_console("selected File: " + currentFilepathDEC);
            }
        }
        //file dialog for the decryption window
        if(currentFilepathDEC == string_for_file_manager){
            String s = f.openFileDialog("");
            if(s != null){
                //selects the File and closes the Gui
                currentFilepathDEC = s;
                Main.write_to_console("selected File: " +currentFilepathDEC);
            }
        }

        if(pathToConfig == string_for_file_manager){
            String s = f.openFileDialog("");
            if(s != null){
                //selects the File and closes the Gui
                pathToConfig = s;
                Main.write_to_console("selected File: " +pathToConfig);
            }
        }
        //dev mode
        if(dev_mode_check){
            devWindow.dev_mode_Window();

        }

        //check for import window
        if(import_started){
            String outfileRead = "";
            //checking so that a file is selected
            if(pathToConfig != null && pathToConfig != string_for_file_manager){
                Main.write_to_console("selected File test :" + pathToConfig);
                Main.write_to_console("started reading File");

                outfileRead = File_handling.readFile(pathToConfig);
                Main.write_to_console("read the file");

                Main.write_to_console("finished reading from file");
                //
                if(outfileRead != ""){
                    splitAndConvertandSetParameters(outfileRead);//should already set the variables at the right lication

                    //ends this
                    import_started= false;
                }
            }

        }

    }

    //all the Methods for the different Windows
    private void EncryptionWindow(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("Encryption",pOpen,ImGuiWindowFlags.MenuBar );
        if (ImGui.beginMenuBar()) {
            //begin drop down menu
            if (ImGui.beginMenu( "F`il`e")) {
                if (ImGui.menuItem("Open..", "Ctrl+O")) {
                    currentFilepathENC = string_for_file_manager; }
                //end Drop down menu
                ImGui.endMenu();
            }
            ImGui.endMenuBar();
            ImGui.text("Current Selected File: " + currentFilepathENC);
            if(ImGui.button("Encrypt")){
                //tests if the password field is empty
                //this might need to include to test if we have a file selector open at the moment
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
                currentFilepathENC = string_for_file_manager;
            }
            //ImGuiInputTextFlags.Password maybe just add to the end of the line to hide password
            if(ImGui.inputTextMultiline(": Password", encPswdWindow,200, 20)){

            }
            if(ImGui.button("export Variables")){
                if(out != null){//checks that there are things
                    //creates the output string
                    String export_settings_Str = encPswdWindow.get() + "|" + Arrays.toString(out.retSalt()) + "|" + Arrays.toString(out.retIv());
                    Random rand = new Random();
                    int rand_numb_for_export = rand.nextInt(100000);
                    String export_target = File_handling.get_path_file(currentFilepathENC) + "SavedEncryptionData" + Integer.toString(rand_numb_for_export) + ".txt";
                    Main.write_to_console(export_target);
                    File_handling.StringWriter(export_settings_Str,export_target );
                    Main.write_to_console("exported the variables");
                }
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
                    currentFilepathDEC = string_for_file_manager;
                }

                ImGui.endMenu();
                //end Drop Down menu
            }
            ImGui.endMenuBar();
            //end menubar

            //shows the currently selected File
            ImGui.text("Current Selected File: " + currentFilepathDEC);

            if(ImGui.button("decrypt")){
                //to check that it is none null
                //this might need to include to test if we have a file selector open at the moment
                if(dec_Psw_Window.get().trim().length() != 0  && dec_Salt_Window.get().trim().length() != 0  && dec_IV_Window.get().trim().length() != 0 && currentFilepathDEC != null){
                    Main.write_to_console("all Fields were filled! and a file was selected");
                    /*
                    creates a string variable and also clears the string variable for security reasons
                    aswell as memory efficient
                     */

                    //code for salt
                    String salt_Value= dec_Salt_Window.get().trim();
                    byte[] salt_byte = StringToByteArray(salt_Value);
                    salt_Value = null; //clears variable

                    //code for password
                    String password_Value = dec_Psw_Window.get().trim();
                    char[] password_arr = password_Value.toCharArray();
                    password_Value = null;


                    //code for IV
                    String iv_value = dec_IV_Window.get().trim();
                    byte[] iv_byte = StringToByteArray(iv_value);
                    iv_value = null;//clear value

                    //code for ouput file
                    String outdir = currentFilepathDEC.replace(".enc", "");

                    // the actual decryption
                    try {
                        DecriptionFiles(password_arr, currentFilepathDEC, outdir, salt_byte, iv_byte);
                    }catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            if(ImGui.button("Open File selector")){
                currentFilepathDEC = string_for_file_manager;
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
                pathToConfig = string_for_file_manager;
                Main.write_to_console("Imported Settings from"+ pathToConfig);
                import_started = true;
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
            Main.write_to_console("updated deleteFileAfterusage to: " + deleteFileAfterusage);

        }
        if(ImGui.checkbox("Show Hidden Files in File Selector", show_hidden_files_Files_Selector)){
            show_hidden_files_Files_Selector = !show_hidden_files_Files_Selector;
            Main.write_to_console("updated show_hidden_files_Files_Selector to: " + show_hidden_files_Files_Selector);

        }
        if(ImGui.checkbox("clear En /-Decryption values after usage",clear_values_after_usage)){
            clear_values_after_usage = !clear_values_after_usage;
            Main.write_to_console("updated clear_values_after_usage to: " + clear_values_after_usage);

        }if(ImGui.checkbox("Developer Mode",dev_mode_check )){
            dev_mode_check = !dev_mode_check;
            Main.write_to_console("updated dev_mode_check to: " + dev_mode_check);

        }if(ImGui.checkbox("Disable Logging to console",disable_logging_to_Consol )){
            disable_logging_to_Consol = !disable_logging_to_Consol;
            Main.write_to_console("updated disable_logging_to_Consol to: " + disable_logging_to_Consol);
            Main.update_Logging_to_console_Boolean(disable_logging_to_Consol);
        }
        if(ImGui.sliderInt("max Amount lines ",test, 100,1000 )){
            //checks and updates the value of the max amounts of lines
            for(int i: test){
                if(i != 0){
                    Main.setConsol_lenght(i);
                }

            }
        }

        ImGui.end();

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
    //code to convert the String value from some inputs to a byte array
    public byte[] StringToByteArray(String inputString) {
        inputString = inputString.replaceAll(" ", "");
        String[] split2 = inputString.split(Pattern.quote(","));
        byte[] bytes = new byte[split2.length];

        for (int i = 0; i < split2.length; i++) {
            bytes[i] = Byte.parseByte(split2[i]);
        }

        return bytes;
    }
    //todo add the putting the Strings into the right variables
    public void splitAndConvertandSetParameters(String inputString) {
        String[] split1 = inputString.split(Pattern.quote("|"));//return the list with everything separated
        //the only thing left is to convert the byte
        //setting the password

        dec_Psw_Window =new ImString(split1[0]);

        //converting these things to arrays and setting them appropriately
        //so we remove all the brackets before we open this I can reuse the other part

        String thissalt =  split1[1].replaceAll(Pattern.quote("]"),"");
        thissalt =  thissalt.replaceAll(Pattern.quote("["),"");

        dec_Salt_Window = new ImString(thissalt);
        thissalt =null;

        //the same thing as abouve but for the IV
        String thisiv =  split1[2].replaceAll(Pattern.quote("["),"");
        thisiv =  thisiv.replaceAll(Pattern.quote("]"),"");

        dec_IV_Window = new ImString(thisiv);
        thisiv = null;

        //ivField.setText(thisiv);

    }

}

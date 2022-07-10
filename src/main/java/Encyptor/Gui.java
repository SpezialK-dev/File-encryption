package Encyptor;

import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;

import java.io.File;


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
    ImString encPswdWindow = new ImString("",256);

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
            String s = f.openFileDialog("","");
            if(s != null){
                System.out.println(s);
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
                if (ImGui.menuItem("Close", "Ctrl+W")) {

                }
                //end Drop down menu
                ImGui.endMenu();
            }
            ImGui.endMenuBar();
            if(ImGui.button("Encrypt")){
                //todo write code for encryption
            }
            if(ImGui.button("Open File selector")){
                //todo this is currently a test function to test out the file chooser
                fileOpenerHasbeenOpenENC =!fileOpenerHasbeenOpenENC;
            }
            //ImGuiInputTextFlags.Password maybe just add to the end of the line to hide password
            if(ImGui.inputTextMultiline("Password: ", encPswdWindow,200, 20)){
                System.out.println("password typed in:"+ encPswdWindow);
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
}

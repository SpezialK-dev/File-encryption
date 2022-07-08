package Encyptor;

import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGuiWindow;
import imgui.type.ImBoolean;
import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.app.Window;


//this is the new Gui there to replace the old Jpanel one
public class Gui extends Application{
    //all the Variables for settings
    //this might be really useless
    ImGui gui = new ImGui();
    //this is the window for all the encryption stuff

    Boolean deleteConfAfterUsage = false;
    Boolean deleteFileAfterusage = false;
    //shitty copy variables
    Boolean copyVardeleteFileAfterusage = null;

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


    }



    //all the Methods for the different Windows
    private void EncryptionWindow(){
        //todo fix this to work so that I can actually open a new window
        //Create a window called "My First Tool", with a menu bar.
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
            deleteConfAfterUsage = switchBoolean(deleteConfAfterUsage);
        }
        if(ImGui.checkbox("Delete File after usage", deleteFileAfterusage)){
            deleteFileAfterusage = switchBoolean(deleteFileAfterusage);
        }

            //first_run = false;
        //}

        ImGui.end();

    }


    // takes care of File selection
    private void fileOpend(String filenameAndPath){
        ImGui.text("CurrentFile Opened: ");
    }
    //switches a boolean and then returns the switched version
    private Boolean switchBoolean(Boolean input){
        if(input){
            input = false;

        }else{
            input = true;
        }
        return input;
    }
}

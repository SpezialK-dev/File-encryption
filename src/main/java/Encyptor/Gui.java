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

    @Override
    protected void configure(Configuration config) {
        config.setTitle("File-encryption");
    }

    public void process(){
        //todo fix this to work so that I can actually open a new window
        // Create a window called "My First Tool", with a menu bar.
        ImBoolean pOpen = new ImBoolean(true);
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
            if (ImGui.beginMenu( "File")) {
                if (ImGui.menuItem("Open..", "Ctrl+O")) {
                    fileOpend("test"); }
                if (ImGui.menuItem("Close", "Ctrl+W")) {
                }
                ImGui.endMenu();
            }
            ImGui.endMenuBar();
            if(ImGui.button("Encrypt")){

            }
        }
        ImGui.end();
    }

    private void decryptionWindow(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("Decryption",pOpen,ImGuiWindowFlags.MenuBar );
        if (ImGui.beginMenuBar()) {
            if (ImGui.beginMenu( "File")) {
                if (ImGui.menuItem("Open..", "Ctrl+O")) {
                    fileOpend("test"); }
                if (ImGui.menuItem("Close", "Ctrl+W")) {
                }
                ImGui.endMenu();
            }
            ImGui.endMenuBar();
            if(ImGui.button("decrypt")){

            }
        }
        ImGui.end();
    }
    private void settingsMenu(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("Settings",pOpen);
       //all the Settings

        Boolean deleteConfAfterUsage = false;
        Boolean deleteFileAfterusage = false;

        //this is a hack so that everything gets rendered when I first open the application
        Boolean first_run = true;

        //this toggles the while to only work when the window is focused to safe on system resources
        while(ImGui.isAnyItemFocused() ||first_run ) {
            deleteConfAfterUsage = ImGui.checkbox("Delete Config after usage", deleteConfAfterUsage);
            deleteFileAfterusage = ImGui.checkbox("Delete File after usage", deleteFileAfterusage);

            first_run = false;
        }
        ImGui.end();
    }


    // takes care of File selection
    private void fileOpend(String filenameAndPath){
        ImGui.text("CurrentFile Opened: ");
    }
}

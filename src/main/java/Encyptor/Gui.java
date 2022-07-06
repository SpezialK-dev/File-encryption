package Encyptor;

import imgui.flag.ImGuiWindowFlags;
import imgui.flag.ImGuiWindowFlags.*;
import imgui.type.ImBoolean;
import imgui.type.ImBoolean.*;
import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.app.Window;


//this is the new Gui there to replace the old Jpanel one
public class Gui extends Application{
    //this is the window for all the encryption stuff

    @Override
    protected void configure(Configuration config) {
        config.setTitle("File-encryption");
    }

    public void process(){
        //todo fix this to work so that I can actually open a new window
        // Create a window called "My First Tool", with a menu bar.
        ImBoolean pOpen = new ImBoolean(true);
        testWindow();
    }


    private void testWindow(){
        //todo fix this to work so that I can actually open a new window
        //Create a window called "My First Tool", with a menu bar.
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("Encryption",pOpen,ImGuiWindowFlags.MenuBar );
        if (ImGui.beginMenuBar()) {
            if (ImGui.beginMenu("File")) {
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
    // takes care of File selection
    private void fileOpend(String filenameAndPath){
        ImGui.text("CurrentFile Opened: ");
    }
}

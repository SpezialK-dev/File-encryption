package Encyptor.utils.ImguiWindows;

import Encyptor.Main;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

import java.util.ArrayList;

public class DevWindow {
    //this is a wrapper for an array to display an array


    public DevWindow(){

    }
    //main window to be called
    public void dev_mode_Window(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("DEVELOPMENT",pOpen, ImGuiWindowFlags.NoCollapse);
        ImGui.listBox(": Console Log ",new ImInt(0), Main.get_consol_log() );
        if(ImGui.button("clear Console:")){
            Main.clear_console();

        }
        ImGui.end();
    }

}

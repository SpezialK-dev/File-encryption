package Encyptor.utils.ImguiWindows;

import Encyptor.Main;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

import java.util.ArrayList;

public class DevWindow {
    //this is a wrapper for an array to display an array
    int console_lenght = 100;
    public DevWindow(){
    }
    //main window to be called
    public void dev_mode_Window(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("DEVELOPMENT",pOpen, ImGuiWindowFlags.NoCollapse);
        //Main.get_consol_log() is the command that gets the command log from our main class
        //ImGui.listBox(": Console Log ",new ImInt(0), Main.get_consol_log() );
        ImGui.beginChild("Scrolling");
        for(String s : Main.get_consol_log()){
            ImGui.text(s);
        }
        ImGui.endChild();
        if(ImGui.button("clear Console:")){
            Main.clear_console();
            Main.write_to_console("cleared console");
        }
        ImGui.end();
        //there to clean console when exceeded length
        if(Main.get_consol_log().length > Main.getConsol_lenght()){
            Main.clear_console();
        }
    }
}
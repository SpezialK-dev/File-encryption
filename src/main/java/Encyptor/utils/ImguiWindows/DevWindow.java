package Encyptor.utils.ImguiWindows;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

import java.util.ArrayList;

public class DevWindow {
    //if this program should write to console
    Boolean log_to_Konsol = false;
    ArrayList<String> console_log = new ArrayList<>();

    public DevWindow(Boolean logging_to_Konsol){
        log_to_Konsol = logging_to_Konsol;
    }
    //main window to be called
    public void dev_mode_Window(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("DEVELOPMENT",pOpen, ImGuiWindowFlags.NoCollapse);
        ImGui.listBox(": Console Log ",new ImInt(0), console_log.toArray(new String[console_log.size()])  );

        /*
        todo implement the following
         - a console
         - showing all internal variables via reflection
         */
        if(ImGui.button("clear Console:")){
            clear_console();

        }
        ImGui.end();
    }
    //methods to interact with the class
    public void write_to_console(String input){
        if(!log_to_Konsol){
            console_log.add(input);
            System.out.println("added |" + input + "| to console log" );
        }
    }
    public void clear_console(){
        console_log.clear();
    }
    public void update_Logging_to_console_Boolean(Boolean updated_Value){
        log_to_Konsol = updated_Value;
    }

    //local methods

}

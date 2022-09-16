package Encyptor.utils.ImguiWindows;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public class DevWindow {
    Boolean log_to_Konsol = true;
    public DevWindow(Boolean logging_to_Konsol){
        log_to_Konsol = logging_to_Konsol;
    }
    //main window to be called
    public void dev_mode_Window(){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("DEVELOPMENT",pOpen, ImGuiWindowFlags.NoCollapse);

        ImGui.end();
    }
}

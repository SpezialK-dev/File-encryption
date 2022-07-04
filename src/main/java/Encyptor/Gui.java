package Encyptor;

import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;

public class Gui extends Application{

    @Override
    protected void configure(Configuration config) {
        config.setTitle("File-encryption");
    }


    @Override
    public void process() {
        ImGui.text("Hello, World!");
    }
    //This will be the replacement for the old

}

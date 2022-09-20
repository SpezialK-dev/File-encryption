package Encyptor;

import Encyptor.utils.File_handling;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static Encyptor.utils.File_handling.readFile;
import static imgui.app.Application.launch;



public class  Main {
    //todo maybe put the creation of the Dev window here into main so that I can inherit it to other classes via the constructor not optimal but possible
    //error handling for the new Gui
    static Boolean log_to_Konsol = false;
    static ArrayList<String> console_log = new ArrayList<>();
    //what launch option is selected
    final static String os_info = System.getProperty("os.name").toLowerCase().trim();
    //depending on how many options we will have we might have to create a hashmap with options
    static int launch_option = 0;
    //all the file handling stuff
    static String config_dir =  File_handling.get_Path_to_Config_dir();
    static File path_to_config_File = new File(config_dir + "config.conf");
    public static void main(String[] args) {
        for(String st: args){
            System.out.println(st);
        }
        write_to_console("!!DEVELOPMENT CONSOLE!!");
        write_to_console("Code running on: " + os_info);

        //checks for existence
        if(!path_to_config_File.isFile()){
            write_to_console("config FIle does not exist:");
        }else{
            write_to_console("File found");
            //reads the file
            String out_from_File_in;
            try {
                out_from_File_in = readFile(path_to_config_File.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            write_to_console("What was found in the file: " + out_from_File_in);
            launch_option = Integer.parseInt(out_from_File_in.trim());
        }

        //new GuiOld();
        //launch(new Gui());
        if(launch_option == 0){
            new Launch_Menu();
        }
        if(launch_option == 1){
            launch(new Gui());
        }
        if(launch_option == 2){
            new GuiOld();
        }
        //the file writer to write the config

    }
    public void update_launch_option(int update){
        write_to_console("updated Launch option with : " + update);
        launch_option = update;
        if(!path_to_config_File.isFile()){
            write_to_console("The current Value that is printed: " + launch_option);
            try {
                File_handling.StringWriter(String.valueOf(launch_option),path_to_config_File.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //get the current OS info Variable
    public String getOs_info(){
        return os_info;
    }

    //code for the Dev window
    public static void write_to_console(String input){
        if(!Main.getLog_to_Konsol()){
            console_log.add(input);
        }
    }
    public static void clear_console(){
        console_log.clear();
    }
    public static void update_Logging_to_console_Boolean(Boolean updated_Value){
        log_to_Konsol = updated_Value;
    }
    public static String[] get_consol_log(){
        return console_log.toArray(new String[console_log.size()]);
    }
    //interaction
    public static Boolean getLog_to_Konsol(){
        return log_to_Konsol;
    }
}
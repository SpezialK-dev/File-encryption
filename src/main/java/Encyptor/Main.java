package Encyptor;

import Encyptor.utils.File_handling;

import java.io.File;
import java.io.IOException;

import static Encyptor.utils.File_handling.readFile;
import static imgui.app.Application.launch;



public class  Main {
    //what launch option is selected
    final static String os_info = System.getProperty("os.name").toLowerCase().trim();
    //depending on how many options we will have we might have to create a hashmap with options
    static int launch_option = 0;
    //all the file handling stuff
    static String config_dir =  File_handling.get_Path_to_Config_dir();
    static File path_to_config_File = new File(config_dir + "config.conf");
    static Boolean launched_bool = false;
    public static void main(String[] args) {
        System.out.println("!!DEVELOPMENT CONSOLE!!");
        System.out.println("\n\n\n");
        System.out.println("Code running on: " + os_info);

        //checks for existence
        if(!path_to_config_File.isFile()){
            System.out.println("config FIle does not exist:");
        }else{
            System.out.println("File found");
            //reads the file
            String out_from_File_in = new String();
            try {
                out_from_File_in = readFile(path_to_config_File.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("What was found in the file: " + out_from_File_in);
            launch_option = Integer.parseInt(out_from_File_in.trim());
        }

        //new GuiOld();
        //launch(new Gui());
        if(launch_option == 0){
            Launch_Menu l = new Launch_Menu();
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
        System.out.println("updated Launch option with : " + update);
        launch_option = update;
        if(!path_to_config_File.isFile()){
            System.out.println("The current Value that is printed: " + launch_option);
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


}
package Encyptor.utils;


import Encyptor.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Pattern;

public class File_handling{
    //todo add stuff for File handling
    //reads out a file and returns its content as a string
    public static String readFile(String filePath) {
        Path path = Path.of(filePath);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            Main.write_to_console("Failed to write to : " + filePath);
            throw new RuntimeException(e);
        }
    }
    //writes a String into a file
    public static void StringWriter(String input, String filepath)  {
        BufferedWriter outWriter = null;
        try {
            outWriter = new BufferedWriter(new FileWriter(filepath));
            outWriter.write(input);
            outWriter.close();
        } catch (IOException e) {
            Main.write_to_console("failed to write to:" + filepath);
            throw new RuntimeException(e);
        }

    }
    public static String get_Path_to_Config_dir(){
        String operating_system = System.getProperty("os.name").toLowerCase().trim();
        String endpath = System.getProperty("user.home");
        if(operating_system.equals("linux")){
            Main.write_to_console("Linux has been selected as your Operating system");
            //the path selected
            endpath = endpath + "/.config/Encryption-Java/";
            //creates the actual Path and gives Status to the Console
            Boolean stauts_Creation= new File(endpath).mkdirs();
            Main.write_to_console("Status in creating: " + endpath + "\n Status: " + stauts_Creation);
        }if(operating_system.equals("windows")){
            Main.write_to_console("Windows has been selected as your Operating system");
            //todo add code for the windows operating system
            Main.write_to_console("Coming Soon");
        }else{
            //this currently gets triggered every time this code gets run even though it should not
            Main.write_to_console("we could not find your Operating System (can be ignored !! if it prints a path before)");
        }
        return endpath;
    }
    public static String  get_path_file(String inp){
        String[] split = inp.split(Pattern.quote("/"));
        split[split.length-1] = "";
        String outString = "";
        for(String s :split){
            outString = outString + "/" + s;
        }

        return outString;
    }

}

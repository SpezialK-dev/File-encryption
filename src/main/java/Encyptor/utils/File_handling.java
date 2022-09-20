package Encyptor.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class File_handling{
    //todo add stuff for File handling
    //reads out a file and returns its content as a string
    public static String readFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        return Files.readString(path);
    }
    //writes a String into a file
    public static void StringWriter(String input, String Filepath) throws IOException {
        BufferedWriter outWriter = new BufferedWriter(new FileWriter(Filepath));
        outWriter.write(input);
        outWriter.close();
    }
    public static String get_Path_to_Config_dir(){
        String operating_system = System.getProperty("os.name").toLowerCase().trim();
        String endpath = System.getProperty("user.home");
        if(operating_system.equals("linux")){
            System.out.println("Linux has been selected as your Operating system");
            //the path selected
            endpath = endpath + "/.config/Encryption-Java/";
            //creates the actual Path and gives Status to the Console
            Boolean stauts_Creation= new File(endpath).mkdirs();
            System.out.println("Status in creating: " + endpath + " Status: " + stauts_Creation);
        }if(operating_system.equals("windows")){
            System.out.println("Windows has been selected as your Operating system");
            //todo add code for the windows operating system
            System.out.println("Coming Soon");
        }else{
            //this currently gets triggered every time this code gets run even though it should not
            System.out.println("we could not find your Operating System (can be ignored !! if it prints a path before)");
        }
        return endpath;
    }

}

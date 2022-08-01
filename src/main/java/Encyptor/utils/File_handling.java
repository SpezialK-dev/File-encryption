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
        File outFile = new File(Filepath);
        BufferedWriter outWriter = new BufferedWriter(new FileWriter(Filepath));
        outWriter.write(input);
        outWriter.close();
    }
}

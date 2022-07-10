package Encyptor;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSelector {
    public FileSelector(){

    }
    //current hack bc I can't find a way to otherwise close the window
    /* current Why to use this
    if(fileOpenerHasbeenOpenENC){
            String s = f.openFileDialog("","");
            if(s != null){
                System.out.println(s);//Optional if you want to debug
                fileOpenerHasbeenOpenENC = !fileOpenerHasbeenOpenENC;
            }
        }
     */
    //a custom File Selector

    public String openFileDialog(String sourcePath,String endingParameter){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("File Selector",pOpen, ImGuiWindowFlags.NoCollapse );
        //code that shows the all the Files in a directory
        ImInt i =new  ImInt(0);
        String[] testAr =new String[]{"hello","this","is ", "a ", "you ", "I hope ", "this", "works"};

        //this is a list all the Files in the dir
        ImGui.listBox("test", i, testAr);


        if(ImGui.button("Open File")){
            ImGui.end();
            //todo find out why this is not working
            // this is not closing and not returning anything
            return "hello this is a test";
        }
        //temp file name

        //this should never be reached
        ImGui.end();
        return null;
    }
    //lists all the Files in a directory
    private List<Path> ls(String path) {
        //the stream of the files
        Stream<Path> filesStream = null;
        //try catch block that should get all the Files paths
        try{
            filesStream= Files.list(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //gets a list of paths
        List<Path> filesList = filesStream.collect(Collectors.toList());

        return null;
    }
}

package Encyptor.utils.ImguiWindows;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.*;

import java.io.File;
import java.util.regex.Pattern;

public class FileSelector {
    //starts in the users home dir
    private String currentDir = System.getProperty("user.home");
    //all the arrays to remove amount of reads
    private File[] filesListing =null;
    private String[] displaynames = null;
    //a variable that is there and asks if I need to refresh the variables
    private Boolean needsrefresh = true;
    //saves what is selected
    private int selectedItem = 0;
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
    //todo add a starting parameter so that it actually can start in a different dir
    public String openFileDialog(String endingParameter){
        ImBoolean pOpen = new ImBoolean(true);
        ImGui.begin("File Selector",pOpen, ImGuiWindowFlags.NoCollapse );
        //code that shows the all the Files in a directory
        ImInt i =new  ImInt(0);//current temp state in the moment of the array

        //helps to not put a lot of strain on drive
        if(needsrefresh){
            filesListing = ls(currentDir);//gets all the files in my current home dir(just debug currently)
            displaynames = convertFileToString(filesListing);
            needsrefresh =false;
            System.out.println("refreshed Dirs");
        }
        //shows the full path of the dir we are currently in
        /*//todo write this so that it shows the current path as a editable string
        ImString dir = new ImString(currentDir,800);
        if(ImGui.inputTextMultiline("cur dir: ",dir, 200,20)){

        }*/

        //this is a list all the Files in the dir
        ImGui.listBox("Dir", i, displaynames);
        //this code determines what thing has been pressed cannot open the 0 part of a array
        if(i.get() != 0){
            //checking if it is dir and then moving into it
            selectedItem = i.get();
            if(filesListing[i.get()-1].isDirectory()){
                currentDir = filesListing[i.get()-1].getAbsolutePath();
                selectedItem =0;
                needsrefresh = true;
            }
            //System.out.println(i);
        }
        ImGui.text("Current selected File: " + displaynames[selectedItem]);

        if(ImGui.button("Go Back One Directory")){
            //splits the string and checks for 0
            //todo add different os root dirs
             String[] pathdev = currentDir.split(Pattern.quote("/"));
             if(pathdev.length == 0){
                System.out.println("You have reached a root directory");
                //todo write a pop up
             }else {
                 //removes one the last dir and removes
                 pathdev[pathdev.length - 1] = "";
                 //todo remove the first part of the array because 0 !!!works currently under Linux
                 String out = "";
                 //combines all of strings
                 for (String tempS : pathdev) {
                     out = out + "/" + tempS;
                 }
                 //to actually refresh the page
                 needsrefresh =true;
                 currentDir = out;
             }
        }
        //ImGui.text("Selected Item: " + testAr[selectedItem]);
        if(ImGui.button("Open File")){
            //selects the files and returns it
            if(selectedItem != 0){
                String returnValue = filesListing[selectedItem-1].getAbsolutePath();
                        ImGui.end();
                return returnValue;
            }else{
                System.out.println("No File selected");
            }

        }


        //this should never be reached
        ImGui.end();
        return null;
    }
    //lists all the Files in a directory
    private File[] ls(String path) {
        File startpoint = new File(path);
        File[] out = startpoint.listFiles();
        return out;
    }
    //convert the File array to a more suitable String version to display
    private String[] convertFileToString(File[] input){
        int index = 1;
        String[] out =  new String[input.length+1];
        out[0] = "Files to Select From";
        for (File f : input) {
            if(f.isDirectory()){
                out[index] = "D: "+ f.getName();

            }else{
                out[index] = f.getName();
            }
            index = index+1;
        }
        return out;
    }
}

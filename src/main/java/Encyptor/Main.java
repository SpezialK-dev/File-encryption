package Encyptor;

import Encyptor.utils.File_handling;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static imgui.app.Application.launch;



public class Main {
    //what launch option is selected
    static int launch_option = 0;
    static Boolean launched_bool = false;
    public static void main(String[] args) {
        //todo make a launcher for to choose between the different Gui's
        File_handling.get_Path_to_Config_dir();
        //Thread code
        Launch_Menu thread_Obj_coust  = new Launch_Menu();
        Thread thread_obj = new Thread((Runnable) thread_Obj_coust);
        thread_obj.start();

        //end of the thread code
        //launcher();
        /*
        if(launch_option == 1){
            System.out.println("launched new Gui");
            launched_bool = true;
            launch(new Gui());
        }
        if(launch_option == 2){
            System.out.println("launched Old Gui");
            launched_bool = true;
            new GuiOld();
        }*/

        //new GuiOld();
        //launch(new Gui());
        if(launch_option == 1){
            System.out.println("launched new Gui");
            launched_bool = true;
            launch(new Gui());
        }
        if(launch_option == 2){
            System.out.println("launched Old Gui");
            launched_bool = true;
            new GuiOld();
        }
    }
    public void update_launch_option(int update){
        System.out.println("updated Launch option with : " + update);
        launch_option = update;
    }


    //this inner class will be used for the launch menu, to be later destroyed
    private static class Launch_Thread extends Thread {
        public void run(){

        }
        public static void main(String[] args){
            launcher();
        }
        //seeing what menu needs to be launched
        private static void launcher(){
            if(launch_option == 0){
                Launch_Menu l = new Launch_Menu();
            }
            if(launch_option == 1){
                launch(new Gui());
            }
            if(launch_option == 2){
                new GuiOld();
            }
        }
    }

}
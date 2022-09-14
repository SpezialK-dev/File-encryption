package Encyptor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launch_Menu extends Main implements ActionListener {
    private int return_value = 0;
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame();
    private static JButton old_Gui_Button = new JButton("Old Gui");
    private static JButton new_Gui_Button = new JButton("new Gui");

    public Launch_Menu(){


        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setTitle("File-encryption Launch Options");
        frame.setResizable(false);
        panel.setLayout(null);

    //
    //setting all the bounds for all the buttons
        new_Gui_Button.setBounds(20, 100, 260, 25);
        old_Gui_Button.setBounds(20, 145, 260, 25);

        new_Gui_Button.addActionListener(this);
        old_Gui_Button.addActionListener(this);

    //adding the buttons to the panel

        panel.add(new_Gui_Button);
        panel.add(old_Gui_Button);

        frame.setVisible(true);
}
    //all the Action listener code
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == new_Gui_Button){
            update_launch_option(1);
            frame.dispose();
        }
        if(actionEvent.getSource() == old_Gui_Button){
            update_launch_option(2);
            frame.dispose();
        }
    }
    //just removes this thing and returns the Value needed

}

package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePageGUI extends JFrame implements ActionListener, MouseListener { 

    public HomePageGUI(){
        super("Wildlife Rescue");
        setupGUI();
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setupGUI(){
        JButton ScheduleButton  = new JButton("Schedule");
        ScheduleButton.addActionListener(this);

        JButton AnimalButton = new JButton("Animals");
        AnimalButton.addActionListener(this);

        JButton TreatmentsButton =  new JButton("Treatments");
        TreatmentsButton.addActionListener(this);

        JButton TasksButton = new JButton("Tasks");
        TasksButton.addActionListener(this);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        headerPanel.add(ScheduleButton);
        headerPanel.add(AnimalButton);
        headerPanel.add(TreatmentsButton);
        headerPanel.add(TasksButton);

        this.add(headerPanel, BorderLayout.NORTH);

    }
    public void actionPerformed(ActionEvent event){}
    
    public void mouseClicked(MouseEvent event){
      
    }

    public void mouseEntered(MouseEvent event){}
    public void mouseExited(MouseEvent event){}
    public void mousePressed(MouseEvent event){}
    public void mouseReleased(MouseEvent event){}
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new HomePageGUI().setVisible(true);        
        });
    }
}

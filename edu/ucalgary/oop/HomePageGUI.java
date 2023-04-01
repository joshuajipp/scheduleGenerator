package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class HomePageGUI extends JFrame implements ActionListener, MouseListener { 
    private LocalDate date;
    private JLabel scheduleHeader;

    public HomePageGUI(){
        super("Wildlife Rescue");
        setupGUI();
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setupGUI(){
        JButton ScheduleButton  = new JButton("Schedule");
        ScheduleButton.setFont(new Font("Calibri",Font.PLAIN,30));
        ScheduleButton.addActionListener(this);
        
        scheduleHeader = new JLabel("Schedule for " + date);
        scheduleHeader.setFont(new Font("Calibri", Font.BOLD,30));
        scheduleHeader.setForeground(Color.BLUE);


        JButton AnimalButton = new JButton("Animals");
        AnimalButton.setFont(new Font("Calibri",Font.PLAIN,30));
        AnimalButton.addActionListener(this);

        JButton TreatmentsButton =  new JButton("Treatments");
        TreatmentsButton.setFont(new Font("Calibri",Font.PLAIN,30));
        TreatmentsButton.addActionListener(this);

        JButton TasksButton = new JButton("Tasks");
        TasksButton.setFont(new Font("Calibri",Font.PLAIN,30));
        TasksButton.addActionListener(this);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        JPanel mainBody = new JPanel();
        mainBody.setLayout(new FlowLayout());

        headerPanel.add(ScheduleButton);
        headerPanel.add(AnimalButton);
        headerPanel.add(TreatmentsButton);
        headerPanel.add(TasksButton);
        mainBody.add(scheduleHeader);

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(mainBody, BorderLayout.LINE_START);

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

/**
@author 	Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author 	Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@version    	1.2
@since  	1.0
*/
package edu.ucalgary.oop;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

/*
HomePageGUI is a class that extends JFrame and implements ActionListener, and MouseListener. It displays the 
homepage for the wildlife rescue program that will show the schedules for the volunteers.
*/

public class HomePageGUI extends JFrame implements ActionListener, MouseListener { 
    private LocalDate date = LocalDate.now(); //current date
    private JLabel scheduleHeader; //label for schedule header
    private JButton ScheduleButton;
    private JButton AnimalButton;
    private JButton TreatmentButton;
    private JButton TasksButton;


    /*
    HomePageGUI constructor sets up the Graphical User Interface by creating the window for the GUI by initializing its size
    and the title. It also calls setupGUI() to add buttons or text in the page.
     */
    public HomePageGUI(){
        super("Wildlife Rescue"); //call to JFrame constructor with title arguement
        setupGUI(); //calls to setupGUI method
        setSize(800,500); //sets the size of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set default close operation
    }
    /*
    This constructor sets up the GUI by creating and configurating the Schedule, Animals, Treatments and Tasks buttons, as well as 
    the schedule header lable which shows the date of which the schedule is being made for.
     */
    public void setupGUI(){
        //create and configure the Schedule Button
        JButton ScheduleButton  = new JButton("Schedule"); 
        ScheduleButton.setFont(new Font("Calibri",Font.PLAIN,30));
        ScheduleButton.addActionListener(this);
        
        //create and configure the Schedule Header to show the date for the schedule
        scheduleHeader = new JLabel("Schedule for " + date.plusDays(1));
        scheduleHeader.setFont(new Font("Calibri", Font.BOLD,30));
        scheduleHeader.setForeground(Color.BLUE);

        //create and configure the Animal Button
        JButton AnimalButton = new JButton("Animals");
        AnimalButton.setFont(new Font("Calibri",Font.PLAIN,30));
        AnimalButton.addActionListener(this);

        //create and configure the Treatments Button
        JButton TreatmentsButton =  new JButton("Treatments");
        TreatmentsButton.setFont(new Font("Calibri",Font.PLAIN,30));
        TreatmentsButton.addActionListener(this);

        //create and configure the Tasks Button
        JButton TasksButton = new JButton("Tasks");
        TasksButton.setFont(new Font("Calibri",Font.PLAIN,30));
        TasksButton.addActionListener(this);

        //create and configure headerpanel,mainbody
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        JPanel mainBody = new JPanel();
        mainBody.setLayout(new FlowLayout());

        //added the buttons to the headerPanel and label to mainBody
        headerPanel.add(ScheduleButton);
        headerPanel.add(AnimalButton);
        headerPanel.add(TreatmentsButton);
        headerPanel.add(TasksButton);
        mainBody.add(scheduleHeader);

        //added headerPanel and mainBody to the JFrame with relative positions
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(mainBody, BorderLayout.LINE_START);

    }
    /*
    
     */
    public void actionPerformed(ActionEvent event){
       if(event.getSource() == ScheduleButton){
            //get schedule
       }
       if(event.getSource() == AnimalButton){
            //get list of animals
       }
       if(event.getSource() == TreatmentButton){
            //get list of treatment
       }
       if(event.getSource() == TasksButton){
            //get list of tasks
       }
    }
    
    public void mouseClicked(MouseEvent event){
      
    }

    public void mouseEntered(MouseEvent event){}
    public void mouseExited(MouseEvent event){}
    public void mousePressed(MouseEvent event){}
    public void mouseReleased(MouseEvent event){}
    /*
    Invokes the GUI. Calls the HomePageGUI() constructor to create and configure the JFrame and this main method sets the visibility
    to true.
    @param args Optional command-line argument
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new HomePageGUI().setVisible(true);        
        });
    }
}

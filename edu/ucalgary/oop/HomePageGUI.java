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
import java.util.concurrent.Flow;

/*
HomePageGUI is a class that extends JFrame and implements ActionListener, and MouseListener. It displays the 
homepage for the wildlife rescue program that will show the schedules for the volunteers.
*/

public class HomePageGUI extends JFrame implements ActionListener, MouseListener { 
    private LocalDate date = LocalDate.now(); 


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
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        this.add(tabbedPane);

        JLabel scheduleLabel = new JLabel("Schedule for: " + date.plusDays(1));
        


        JLabel animalLabel = new JLabel ("This is the animal tab");
     

        JLabel treatmentLabel = new JLabel("This is the treatment tab");

        tabbedPane.add("Schedule", scheduleLabel);
        tabbedPane.add("Animal", animalLabel);
        tabbedPane.add("Treatment", treatmentLabel);


       
    }
      

    
    /*
    
     */
    public void actionPerformed(ActionEvent event){
      
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

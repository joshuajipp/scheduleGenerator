/**
@author     Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author     Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@version        1.2
@since      1.0
*/
package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.awt.*;
import java.io.*;
import java.util.*;

/*
HomePageGUI is a class that extends JFrame and implements ActionListener, and MouseListener. It displays the 
homepage for the wildlife rescue program that will show the schedules for the volunteers.
*/

public class HomePageGUI extends JFrame  { 
    private LocalDate date = LocalDate.now();
    ArrayList<String> scheduleList = new ArrayList<String>();


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
    public HomePageGUI(ArrayList<String> scheduleList){
    
        super("Wildlife Rescue"); //call to JFrame constructor with title arguement
        this.scheduleList = scheduleList;
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

        JPanel schedulePanel = new JPanel(new BorderLayout());
        JLabel scheduleLabel = new JLabel("Schedule for: " + date.plusDays(1));
        schedulePanel.add(scheduleLabel,BorderLayout.NORTH);
        JList<String> scheduleJList = new JList<>(scheduleList.toArray(new String[0]));
        JScrollPane scrollPanel = new JScrollPane(scheduleJList);
        scrollPanel.setPreferredSize(new Dimension(800,300));
       
        schedulePanel.add(scrollPanel,BorderLayout.CENTER);
      
        JLabel animalLabel = new JLabel ("This is the animal tab");
     

        JPanel treatmentPanel = new JPanel(new BorderLayout());
        JTable table  = treatmentTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800,300));
        treatmentPanel.add(scrollPane);
       
        tabbedPane.add("Schedule", schedulePanel);
        tabbedPane.add("Animal", animalLabel);
        tabbedPane.add("Treatment", treatmentPanel);
       
    }
   
  
    public JTable treatmentTable(){

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("Animal ID");
        tableModel.addColumn("Task ID");
        tableModel.addColumn("Start Hour");
        
        Connection dbConnection;
        Statement dbStatement;
        ResultSet dbResults;
        String dbQuery;
        try{
            dbConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/EWR", "root", "Mx.ze0218");
            dbStatement = dbConnection.createStatement();
            dbQuery = "SELECT * FROM TREATMENTS";
            dbResults = dbStatement.executeQuery(dbQuery);
            while (dbResults.next()) {
                // Add data to the table
                Object[] rowData = new Object[6];
                rowData[0] = dbResults.getInt("AnimalID");
                rowData[1] = dbResults.getInt("TaskID");
                rowData[2] = dbResults.getString("StartHour");
                
                tableModel.addRow(rowData);
            }
            dbStatement.close();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setFillsViewportHeight(true);
        return table;
       
    }
     public ArrayList<String> schedReadFile(){
        try{
            File schedFile = new File("sample.txt");
            Scanner schedReader = new Scanner(schedFile);
            while(schedReader.hasNextLine()){
                String data = schedReader.nextLine();
                scheduleList.add(data);
            }
            schedReader.close();
            return scheduleList;
        }catch(FileNotFoundException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }return null;
        
    }
    /*
    Invokes the GUI. Calls the HomePageGUI() constructor to create and configure the JFrame and this main method sets the visibility
    to true.
    @param args Optional command-line argument
     */
    public static void main(String[] args) { 
        EventQueue.invokeLater(() -> {
            new HomePageGUI().setVisible(true);        
        });

        Schedule.main(args);
        //String[] volunteerChecker = Schedule.main(args); //return either {"true"} or {"false","1,2"}
        String[] volunteerChecker = {"false","1,2"};
        String boolCheck = volunteerChecker[0];
        if (boolCheck.equals("true")){
            HomePageGUI homePage = new HomePageGUI();
            ArrayList<String> scheduleList = homePage.schedReadFile();
            EventQueue.invokeLater(() -> {
                new HomePageGUI(scheduleList).setVisible(true);        
            });
           
        }
    
    
        if (boolCheck.equals("false")){
           
            String volunteerTime = volunteerChecker[1];
            String[] arrOfVolunTime = volunteerTime.split(",");
            String message = "Vounteer is needed for the following times: \n";
            for (String time : arrOfVolunTime){
                message += time + "\n";
            }
            JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage("Volunteer is needed for the following hour: "+ message);
            optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
            Object[] option = {"Confirm"};
            optionPane.setOptions(option);
            JDialog dialog = optionPane.createDialog(null, "Custom Option Pane");
            dialog.setVisible(true);
            Object selectedValue = optionPane.getValue();
            if(selectedValue.equals("Confirm")){
            // Do something if the "Yes, please" button is clicked
            }
            
        }
        
    }
   
}
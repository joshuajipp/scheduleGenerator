/**
@author     Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author     Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@version        1.2
@since      1.0
*/
package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

/*
HomePageGUI is a class that extends JFrame and implements ActionListener, and MouseListener. It displays the 
homepage for the wildlife rescue program that will show the schedules for the volunteers.
*/

public class HomePageGUI extends JFrame implements ActionListener { 
    private LocalDate date = LocalDate.now();
    private ArrayList<String> scheduleList = new ArrayList<String>();
    private Connection dbConnection;
    private Statement dbStatement;
    private ResultSet dbResults;
    private String username;
    private String password;
    private JTable treatmentTable;
    public HomePageGUI(){}
    /*
    HomePageGUI constructor sets up the Graphical User Interface by creating the window for the GUI by initializing its size
    and the title. It also calls setupGUI() to add buttons or text in the page.
     */
    public HomePageGUI(String username,String password){
        super("Wildlife Rescue"); //call to JFrame constructor with title arguement
        this.username = username;
        this.password = password;
        setupGUI(); //calls to setupGUI method
        setSize(800,500); //sets the size of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set default close operation
    }
    public HomePageGUI(ArrayList<String> scheduleList,String username, String password){
        super("Wildlife Rescue"); //call to JFrame constructor with title arguement
        this.scheduleList = scheduleList;
        this.username = username;
        this.password = password;
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
      
        JPanel animalPanel = new JPanel(new BorderLayout());
        JTable animalTable  = animalTable();
        JScrollPane animalScrollPane = new JScrollPane(animalTable);
        animalScrollPane.setPreferredSize(new Dimension(800,300));
        animalPanel.add(animalScrollPane);

        JPanel taskPanel = new JPanel(new BorderLayout());
        JTable taskTable  = taskTable();
        JScrollPane taskScrollPane = new JScrollPane(taskTable);
        taskScrollPane.setPreferredSize(new Dimension(800,300));
        taskPanel.add(taskScrollPane);
        

        JPanel treatmentPanel = new JPanel(new BorderLayout());
        JTable treatmentTable  = treatmentTable();
        JScrollPane TreatmentScrollPane = new JScrollPane(treatmentTable);
        TreatmentScrollPane.setPreferredSize(new Dimension(800,300));
        treatmentPanel.add(TreatmentScrollPane);
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Calibri", Font.PLAIN,30));
        saveButton.addActionListener(this);
        treatmentPanel.add(saveButton,BorderLayout.SOUTH);

        tabbedPane.add("Schedule", schedulePanel);
        tabbedPane.add("Animal", animalPanel);
        tabbedPane.add("Task", taskPanel);
        tabbedPane.add("Treatment", treatmentPanel);
    }
    public void actionPerformed(ActionEvent event) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EWR",username,password);
             Statement stmt = conn.createStatement()) {
             
            // DefaultTableModel tableModel = (DefaultTableModel) treatmentTable.getModel();
            // int rowCount = tableModel.getRowCount();
            // for (int i = 0; i < rowCount; i++) {
            //     int animalID = (int) tableModel.getValueAt(i, 0);
            //     int taskID = (int) tableModel.getValueAt(i, 1);
            //     String startHour = (String) tableModel.getValueAt(i, 2);
            //     String updateQuery = "UPDATE TREATMENTS SET StartHour = '" + startHour
            //             + "' WHERE AnimalID = " + animalID + " AND TaskID = " + taskID;
            //     stmt.executeUpdate(updateQuery);
            // }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void createConnection(){
        try{
            dbConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/EWR",username, password);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void close(){
        try{
            dbResults.close();
            dbConnection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public JTable animalTable(){
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("Animal ID");
        tableModel.addColumn("Nickname");
        tableModel.addColumn("Species");
        try{  
            createConnection();
            dbStatement = dbConnection.createStatement();
            dbResults = dbStatement.executeQuery("SELECT * FROM ANIMALS");
            while (dbResults.next()) {
                // Add data to the table
                Object[] rowData = new Object[3];
                rowData[0] = dbResults.getInt("AnimalID");
                rowData[1] = dbResults.getString("AnimalNickname");
                rowData[2] = dbResults.getString("AnimalSpecies");
                tableModel.addRow(rowData);
            }
            close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        table.setFillsViewportHeight(true);
        return table;
    }
    public JTable taskTable(){
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("Task ID");
        tableModel.addColumn("Description");
        tableModel.addColumn("Duration");  
        tableModel.addColumn("Max Window");
        try{  
            createConnection();
            dbStatement = dbConnection.createStatement();
            dbResults = dbStatement.executeQuery("SELECT * FROM TASKS");
            while (dbResults.next()) {
                // Add data to the table
                Object[] rowData = new Object[4];
                rowData[0] = dbResults.getInt("TaskID");
                rowData[1] = dbResults.getString("Description");
                rowData[2] = dbResults.getInt("Duration");
                rowData[3] = dbResults.getInt("MaxWindow");
                tableModel.addRow(rowData);
            }
            close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        table.setFillsViewportHeight(true);
        return table;
    }
 
    public JTable treatmentTable(){
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("Animal ID");
        tableModel.addColumn("Task ID");
        tableModel.addColumn("Start Hour");
        try{
            createConnection();
            dbStatement = dbConnection.createStatement();
            dbResults = dbStatement.executeQuery("SELECT * FROM TREATMENTS");
            while (dbResults.next()) {
                // Add data to the table
                Object[] rowData = new Object[3];
                rowData[0] = dbResults.getInt("AnimalID");
                rowData[1] = dbResults.getInt("TaskID");
                rowData[2] = dbResults.getString("StartHour");

                tableModel.addRow(rowData);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setFillsViewportHeight(true);
        this.treatmentTable = table;
        return table;
    }
 
     public ArrayList<String> schedReadFile(){
        try{
            File filename  = new File(date.plusDays(1) + ".txt");
            Scanner schedReader = new Scanner(filename);
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
        String username = args[0];
        String password = args[1];
   

        //Schedule.main(args);
        //String[] volunteerChecker = Schedule.main(args); 
        String[] volunteerChecker = {"true","1 2"};
        String boolCheck = volunteerChecker[0];
        if (boolCheck.equals("true")){
            HomePageGUI homePage = new HomePageGUI();
            ArrayList<String> scheduleList = homePage.schedReadFile();
            EventQueue.invokeLater(() -> {
                new HomePageGUI(scheduleList,username,password).setVisible(true);        
            });
        }
        if (boolCheck.equals("false")){
            EventQueue.invokeLater(() -> {
                new HomePageGUI(username, password).setVisible(true);        
            });
            String volunteerTime = volunteerChecker[1];
            String[] arrOfVolunTime = volunteerTime.split(" ");
            String message = "Vounteer is needed for the following times: \n";
            for (String time : arrOfVolunTime){
                message += time + "\n";
            }
            JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(message);
            optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
            Object[] option = {"Confirm"};
            optionPane.setOptions(option);
            JDialog dialog = optionPane.createDialog(null, "VOLUNTEER REQUIRED");
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //disables the exit button
            dialog.setVisible(true);
            Object selectedValue = optionPane.getValue();
            if(selectedValue.equals("Confirm")){
                String [] arguments = {username,password,"false"};
                HomePageGUI.main(arguments);
                
            }   
        }
    }
}
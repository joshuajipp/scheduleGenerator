/**
@author     Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author     Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version        1.5
@since      1.0
*/
package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.time.LocalDate;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * HomePageGUI class handles the homepage of the program.
 * User will be able to interact with multiple tabs and make changes to the
 * treatment table.
 * Will display the schedule to the GUI and will handle if volunteer is needed.
 */

public class HomePageGUI extends JFrame {
    private LocalDate tomorrow = LocalDate.now().plusDays(1);
    private ArrayList<String> scheduleList = new ArrayList<String>();
    private Connection dbConnection;
    private Statement dbStatement;
    private ResultSet dbResults;
    private String username;
    private String password;
    private JTable treatmentTable;
    private DefaultTableModel tableModel = new DefaultTableModel();

    /** Constructor */
    public HomePageGUI() {
    }

    /**
     * Constructor
     * Sets up the frame with a title and the size. It also sets a default behaviour
     * when closed.
     * 
     * @param username. Passed from WildlifeRescue, which is the login page.
     * 
     * @param password. Passed from WildlifeRescue, which is the login page.
     */
    public HomePageGUI(String username, String password) {
        super("Wildlife Rescue"); // call to JFrame constructor with title arguement
        this.username = username;
        this.password = password;
        setupGUI(); // calls to setupGUI method
        setSize(800, 500); // sets the size of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation
    }

    /**
     * Constructor
     * Sets up the frame with a title and the size. It also sets a default behaviour
     * when closed.
     * 
     * @param arraylist of scheduleList. Contains schele read from the text file
     * 
     * @param username. Passed from WildlifeRescue, which is the login page.
     * 
     * @param password. Passed from WildlifeRescue, which is the login page.
     */
    public HomePageGUI(ArrayList<String> scheduleList, String username, String password) {
        super("Wildlife Rescue"); // call to JFrame constructor with title arguement
        this.scheduleList = scheduleList;
        this.username = username;
        this.password = password;
        setupGUI(); // calls to setupGUI method
        setSize(800, 500); // sets the size of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation
    }

    /**
     * Called by the constructor
     * Creates a tabbed Panel and adds necessary components within each tab.
     */
    public void setupGUI() {
        // initializes the tabs to the Frame
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        this.add(tabbedPane);

        // creates the panel which contains components which will be added to the
        // schedule tab
        JPanel schedulePanel = new JPanel(new BorderLayout());
        JLabel scheduleLabel = new JLabel("Schedule for: " + tomorrow);
        schedulePanel.add(scheduleLabel, BorderLayout.NORTH);
        JList<String> scheduleJList = new JList<>(scheduleList.toArray(new String[0]));
        JScrollPane scrollPanel = new JScrollPane(scheduleJList);
        scrollPanel.setPreferredSize(new Dimension(800, 300));
        schedulePanel.add(scrollPanel, BorderLayout.CENTER);

        // creates the panel which contains components which will be added to the animal
        // tab
        JPanel animalPanel = new JPanel(new BorderLayout());
        JTable animalTable = animalTable();
        JScrollPane animalScrollPane = new JScrollPane(animalTable);
        animalScrollPane.setPreferredSize(new Dimension(800, 300));
        animalPanel.add(animalScrollPane);

        // creates the panel which contains components which will be added to the task
        // tab
        JPanel taskPanel = new JPanel(new BorderLayout());
        JTable taskTable = taskTable();
        JScrollPane taskScrollPane = new JScrollPane(taskTable);
        taskScrollPane.setPreferredSize(new Dimension(800, 300));
        taskPanel.add(taskScrollPane);

        // creates the panel which contains components which will be added to the
        // treatment tab
        JPanel treatmentPanel = new JPanel(new BorderLayout());
        this.treatmentTable = treatmentTable();
        JScrollPane TreatmentScrollPane = new JScrollPane(treatmentTable);
        TreatmentScrollPane.setPreferredSize(new Dimension(800, 300));
        treatmentPanel.add(TreatmentScrollPane);

        // adds the panel made to each tab
        tabbedPane.add("Schedule", schedulePanel);
        tabbedPane.add("Animal", animalPanel);
        tabbedPane.add("Task", taskPanel);
        tabbedPane.add("Treatment", treatmentPanel);
    }

    /**
     * Helper method
     * Creates the connection to the database
     */
    private void createConnection() {
        try {
            dbConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/EWR", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method
     * Closes the connection to the database
     */
    public void close() {
        try {
            dbResults.close();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table and fills each row with data from animal query
     * 
     * @returns created animal table shown in the animal tab
     */
    public JTable animalTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("Animal ID");
        tableModel.addColumn("Nickname");
        tableModel.addColumn("Species");
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setFillsViewportHeight(true);
        return table;
    }

    /**
     * Creates a table and fills each row with data from task query
     * 
     * @returns created task table shown in the task tab
     */
    public JTable taskTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("Task ID");
        tableModel.addColumn("Description");
        tableModel.addColumn("Duration");
        tableModel.addColumn("Max Window");
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setFillsViewportHeight(true);
        return table;
    }

    /**
     * Creates a table and fills each row with data from treament query
     * 
     * @returns created treatment table shown in the treatment tab
     */
    public JTable treatmentTable() {
        JTable table = new JTable(tableModel);
        tableModel.addColumn("TreatmentID");
        tableModel.addColumn("AnimalID");
        tableModel.addColumn("TaskID");
        tableModel.addColumn("StartHour");
        try {
            createConnection();
            dbStatement = dbConnection.createStatement();
            dbResults = dbStatement.executeQuery("SELECT * FROM TREATMENTS");
            while (dbResults.next()) {
                // Add data to the table
                Object[] rowData = new Object[4];
                rowData[0] = dbResults.getInt("TreatmentID");
                rowData[1] = dbResults.getInt("AnimalID");
                rowData[2] = dbResults.getInt("TaskID");
                rowData[3] = dbResults.getInt("StartHour");

                tableModel.addRow(rowData);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setFillsViewportHeight(true);
        tableModel.addTableModelListener(new TableModelListener() {

            /**
             * Connects to the database and update the database based on changes made to the
             * table in the Frame.
             * Reloads with every change to attempt to create a schedule
             * 
             * @param e. event or changes to the table
             */
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                Object data = model.getValueAt(row, column);

                // Update the database with the new data
                try {
                    createConnection();
                    dbStatement = dbConnection.createStatement();
                    String updateQuery = "UPDATE TREATMENTS SET " + model.getColumnName(column) + " = '" + data
                            + "' WHERE TreatmentID = " + model.getValueAt(row, 0);
                    dbStatement.executeUpdate(updateQuery);
                    close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                String[] arguments = { username, password, "true" };
                HomePageGUI.main(arguments);
            }
        });
        return table;
    }

    /**
     * Helper Method
     * Looks for the file containing the schedule. It reads from the file and stores
     * the data to scheduleList.
     * 
     * @return arraylist of scheduleList containing the schedule read from the file
     */
    public ArrayList<String> schedReadFile() {
        try {
            File filename = new File(String.format("%s.txt", tomorrow));
            Scanner schedReader = new Scanner(filename);
            while (schedReader.hasNextLine()) {
                String data = schedReader.nextLine();
                scheduleList.add(data);
            }
            schedReader.close();
            return scheduleList;
        } catch (FileNotFoundException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Calls main of Schedule class and passes the argument.
     * Depending on the return array of the Schedule class, it involkes Frame
     * accordingly.
     * 
     * @param Array of string arguments. Passed by WildlifeRescue
     * 
     */
    public static void main(String[] args) {
        String username = args[0];
        String password = args[1];

        // closes or disposes any opened Frame
        for (Frame frame : JFrame.getFrames()) {
            frame.dispose();
        }
        try {
            String[] volunteerChecker = Schedule.main(args); // calls to main and store the return as an array of string
            String boolCheck = volunteerChecker[0]; // stores the String at index one to boolCheck
            if (boolCheck.equals("true")) {
                HomePageGUI homePage = new HomePageGUI();
                ArrayList<String> scheduleList = homePage.schedReadFile(); // if boolCheck = 'true, read schedule from
                                                                           // the textfile created by schedule class
                EventQueue.invokeLater(() -> {
                    new HomePageGUI(scheduleList, username, password).setVisible(true); // involkes to display Frame
                                                                                        // containing schedule
                });
            }
            if (boolCheck.equals("false")) {
                EventQueue.invokeLater(() -> {
                    new HomePageGUI(username, password).setVisible(true); // if boolCheck = 'false', involkes to display
                                                                          // Frame but schedule tab is empty.
                });
                String volunteerTime = volunteerChecker[1];
                String[] arrOfVolunTime = volunteerTime.split(" ");
                String message = "Vounteer is needed for the following time(s): \n";
                for (String time : arrOfVolunTime) {
                    message += time + ":00\n";
                }
                JOptionPane optionPane = new JOptionPane(); // creates a custom window that will pop up with a message
                optionPane.setMessage(message);
                optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
                Object[] option = { "Confirm" };
                optionPane.setOptions(option);
                JDialog dialog = optionPane.createDialog(null, "VOLUNTEER REQUIRED");
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // disables the exit button
                dialog.setVisible(true);
                Object selectedValue = optionPane.getValue();
                if (selectedValue.equals("Confirm")) { // handles onClick of confirm button
                    String[] arguments = { username, password, "false" };
                    // need to close previous eventqueue/GUI Frame
                    HomePageGUI.main(arguments); // calls main again with different argument
                }
            }
        } catch (ScheduleOverflowException e) {
            EventQueue.invokeLater(() -> {
                new HomePageGUI(username, password).setVisible(true); // if unable to create a schedule at all, involkes
                                                                      // a Frame with empty schedule tab
            });
            String message = "Unable to create a schedule. \n" + e.getMessage(); // creates a pop up window with message
            JOptionPane.showMessageDialog(null, message);
        }
    }
}
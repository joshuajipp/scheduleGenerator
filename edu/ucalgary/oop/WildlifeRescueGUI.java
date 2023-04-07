/**
@author 	Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author 	Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@version    	1.3
@since  	1.0
*/
package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/*
 * WildlifeRescueGUI class handles the login page of the application
 * It prompts the user with a username and password 
 * Verifies the given username and password 
 */

public class WildlifeRescueGUI extends JFrame implements ActionListener, MouseListener{
    private String username;
    private String password;
    private JLabel loginPrompt;
    private JLabel userPrompt;
    private JLabel passPrompt;
    private JTextField userInput;
    private JTextField passInput;
  
    /*
     * Constructor
     * Sets up the frame with the tile and the size. It also sets a default behaviour when closed.
     */
    public WildlifeRescueGUI(){
        super("Wildlife Rescue");
        setupGUI();
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     * Called by the constructor 
     * Adds the necessary prompts and button to the Frame.
     */
    public void setupGUI(){
        //styles and create the instruction
        loginPrompt = new JLabel("Enter your login information:");
        loginPrompt.setFont(new Font("Calibri", Font.BOLD,30));
        loginPrompt.setForeground(Color.BLUE);
        loginPrompt.setBounds(60,10,300,30);
        
        //creates and style the username text
        userPrompt = new JLabel ("Username");
        userPrompt.setFont(new Font("Calibri", Font.PLAIN,20));
        userPrompt.setBounds(60,10,300,30);
        
        //creates and style the username textfield
        userInput = new JTextField(10);
        userInput.setFont(new Font("Calibri", Font.PLAIN,30));
        userInput.setBounds(100,60,130,30);

        //creates and style the password text
        passPrompt = new JLabel ("Password");
        passPrompt.setFont(new Font("Calibri", Font.PLAIN,20));
        passPrompt.setBounds(60,10,300,30);

        //creates and style the password field 
        passInput = new JPasswordField(10);
        passInput.setFont(new Font("Calibri", Font.PLAIN,30));
        passInput.setBounds(100,100,120,30);
         
        //adds onClick behaviour to the username and password field
        userInput.addMouseListener(this);
        passInput.addMouseListener(this);
        
        //creates and style the login button. 
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Calibri", Font.PLAIN,30));
        loginButton.addActionListener(this); //adds onClick behaviour to the button

        //creates the layout
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout());
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout());

        //adds components made to each panel
        headerPanel.add(loginPrompt);
        userPanel.add(userPrompt);
        userPanel.add(userInput);
        userPanel.add(passPrompt);
        userPanel.add(passInput);
        loginPanel.add(loginButton);
        
        //adds the panel to the frame with its layout 
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(userPanel, BorderLayout.CENTER);
        this.add(loginPanel, BorderLayout.PAGE_END);
    }
    
    /*
     * grabs the userinput from the textfield and calls validateLogin.
     * If valid calls main of HomePageGUI with arguments for its param
     * @param event. Handles the login click
     */
    public void actionPerformed(ActionEvent event){
        username = userInput.getText();
        password = passInput.getText();
        if(validateLogin()){
            String[] arguments = {username,password,"true"};
            dispose();
            HomePageGUI.main(arguments);
        }
    }

    /*
     * sets the field to an empty string when clicked.
     * @param event. Handles onClick of the username and password field.
     */
    public void mouseClicked(MouseEvent event){
        if(event.getSource().equals(userInput))
            userInput.setText("");
        if(event.getSource().equals(passInput))
            passInput.setText("");
    }

    /*Unused implementation that came with Mouse event */
    public void mouseEntered(MouseEvent event){}
    public void mouseExited(MouseEvent event){}
    public void mousePressed(MouseEvent event){}
    public void mouseReleased(MouseEvent event){}
    
    /*
     * helper method
     * validates the username and password by attempting to connect to the database
     * @returns true if valid, false if unable to make a connection.
     */
    private boolean validateLogin(){
        boolean validateLogin = true;
            try{
                DriverManager.getConnection("jdbc:mysql://localhost:3306/ewr", username, password);
            } catch(SQLException e) {
                JOptionPane.showMessageDialog(null,"Incorrect login information");
                validateLogin = false;
            }
        return validateLogin;
    }

    /*
     * Involves the creation of the Frame and sets the visibility to true so that the user can see it.
     * @param string array of args 
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new WildlifeRescueGUI().setVisible(true);        
        });
    }
}


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
import java.sql.*;


public class WildlifeRescueGUI extends JFrame implements ActionListener, MouseListener{
    private String username;
    private String password;

    private JLabel loginPrompt;
    private JLabel userPrompt;
    private JLabel passPrompt;
    private JTextField userInput;
    private JTextField passInput;
  
    public WildlifeRescueGUI(){
        super("Wildlife Rescue");
        setupGUI();
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setupGUI(){
        loginPrompt = new JLabel("Enter your login information:");
        loginPrompt.setFont(new Font("Calibri", Font.BOLD,30));
        loginPrompt.setForeground(Color.BLUE);
        loginPrompt.setBounds(60,10,300,30);
        
        userPrompt = new JLabel ("Username");
        userPrompt.setFont(new Font("Calibri", Font.PLAIN,20));
        userPrompt.setBounds(60,10,300,30);

        userInput = new JTextField(10);
        userInput.setFont(new Font("Calibri", Font.PLAIN,30));
        userInput.setBounds(100,60,130,30);

        passPrompt = new JLabel ("Password");
        passPrompt.setFont(new Font("Calibri", Font.PLAIN,20));
        passPrompt.setBounds(60,10,300,30);

        passInput = new JPasswordField(10);
        passInput.setFont(new Font("Calibri", Font.PLAIN,30));
        passInput.setBounds(100,100,120,30);
        
        userInput.addMouseListener(this);
        passInput.addMouseListener(this);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Calibri", Font.PLAIN,30));
        loginButton.addActionListener(this);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout());
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout());

        headerPanel.add(loginPrompt);
        userPanel.add(userPrompt);
        userPanel.add(userInput);
        userPanel.add(passPrompt);
        userPanel.add(passInput);
        loginPanel.add(loginButton);

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(userPanel, BorderLayout.CENTER);
        this.add(loginPanel, BorderLayout.PAGE_END);
    }

    public void actionPerformed(ActionEvent event){
        username = userInput.getText();
        password = passInput.getText();
        if(validateLogin()){
            String[] arguments = {username,password,"true"};
            dispose();
            HomePageGUI.main(arguments);
         
       
        }
            
    }
    public void mouseClicked(MouseEvent event){
        if(event.getSource().equals(userInput))
            userInput.setText("");
        if(event.getSource().equals(passInput))
            passInput.setText("");
    }
    public void mouseEntered(MouseEvent event){}
    public void mouseExited(MouseEvent event){}
    public void mousePressed(MouseEvent event){}
    public void mouseReleased(MouseEvent event){}
    
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

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new WildlifeRescueGUI().setVisible(true);        
        });
    }
}


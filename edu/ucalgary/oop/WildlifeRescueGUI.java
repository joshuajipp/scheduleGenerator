package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class WildlifeRescueGUI extends JFrame implements ActionListener, MouseListener{
    private String username;
    private String password;

    private JLabel loginPrompt;
    private JTextField userInput;
    private JTextField passInput;
  
    public WildlifeRescueGUI(){
        super("Wildlife Rescue");
        setupGUI();
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setupGUI(){
        loginPrompt = new JLabel("Enter your login information:");
        loginPrompt.setFont(new Font("Calibri", Font.BOLD,30));
        loginPrompt.setForeground(Color.BLUE);
        loginPrompt.setBounds(60,10,300,30);
        

        userInput = new JTextField("Username");
        userInput.setFont(new Font("Calibri", Font.PLAIN,30));
        userInput.setBounds(100,60,130,30);

        passInput = new JTextField("Password");
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
        userPanel.add(userInput);
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
            String[] arguments = {username,password};
            Schedule.main(arguments);
            dispose();
            EventQueue.invokeLater(() -> {
                new HomePageGUI().setVisible(true);        
            });    
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
        // try{
        //     DriverManager.getConnection("jdbc:Mysql://localhost:3306/EWR", "root", "Mx.ze0218");
        // } catch(SQLException e) {
        //     JOptionPane.showMessageDialog(null,"Incorrect login information");
        //     validateLogin = false;
        // }
        // System.out.println(validateLogin);
        return validateLogin;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new WildlifeRescueGUI().setVisible(true);        
        });
    }
}


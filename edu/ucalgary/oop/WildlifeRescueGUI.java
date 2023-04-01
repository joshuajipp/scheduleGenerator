package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class WildlifeRescueGUI extends JFrame implements ActionListener, MouseListener{
    private String username;
    private String password;

    private JLabel loginPrompt;

    private JTextField userInput;
    private JTextField passInput;

    public WildlifeRescueGUI(){
        super("Wildlife Rescue");
        setupGUI();
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setupGUI(){
        loginPrompt = new JLabel("Enter your login information:");

        userInput = new JTextField("Username");
        passInput = new JTextField("Password");
        userInput.addMouseListener(this);
        passInput.addMouseListener(this);

        JButton loginButton = new JButton("Login");
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
            //new Schedule(username, password);
            JOptionPane.showMessageDialog(this, "Successfully signed in as " + username);
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
        //String user = "guestuser";
        //String pass ="ensf";
        boolean validateLogin = true;
        // if(username != user && password != pass){
        //     validateLogin = false;
        //     JOptionPane.showMessageDialog(null,"Incorrect login information");
        // }
        return validateLogin;
    }


    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new WildlifeRescueGUI().setVisible(true);        
        });
    }
}


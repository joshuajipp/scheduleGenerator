package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePageGUI extends JFrame implements ActionListener, MouseListener { 
    private String username;

    private JLabel loginPrompt;
    private JTextField userInput;
    private JTextField passInput;
    
    public HomePageGUI(){
        super("Wildlife Rescue");
        setupGUI();
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setupGUI(){
        loginPrompt = new JLabel("Enter your login information:");

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        headerPanel.add(loginPrompt);

        this.add(headerPanel, BorderLayout.NORTH);
       
    }
    
    public void actionPerformed(ActionEvent event){
        username = userInput.getText();
   
       
            
            
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
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new HomePageGUI().setVisible(true);        
        });
    }
}

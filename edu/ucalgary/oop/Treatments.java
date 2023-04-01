package edu.ucalgary.oop;

import java.sql.*;

public class Treatments {
    private int treatmentID;
    private int animalID;
    private int startHour;
    private String description;
    private int duration;
    private int maxWindow;

    public Treatments(int treatmentID, int animalID, int startHour, String description, int duration, int maxWindow) {
        this.treatmentID = treatmentID;
        this.animalID = animalID;
        this.startHour = startHour;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;

        // String url = "jdbc:mysql://localhost:3306/EWR";
        // Connection connection = null;
        // Statement statement = null;
        // ResultSet resultSet = null;
        // try {
        // connection = DriverManager.getConnection(url, user, password);
        // statement = connection.createStatement();
        // String query = "SELECT * FROM ewr.treatments as treats"
        // .concat("JOIN ewr.tasks tasks ON treats.TaskID = tasks.TaskID")
        // .concat(String.format(
        // "WHERE treats.TaskID = %d AND TreatmentID = %d", taskID, treatmentID));
        // resultSet = statement.executeQuery(query);
        // this.description = resultSet.getString("Description");
        // this.duration = resultSet.getInt("Duration");
        // this.maxWindow = resultSet.getInt("MaxWindow");

        // } catch (SQLException e) {
        // e.printStackTrace();
        // } finally {
        // try {
        // resultSet.close();
        // statement.close();
        // connection.close();
        // } catch (SQLException e) {
        // e.printStackTrace();
        // }
        // }
    }

    public int getTreatmentID() {
        return treatmentID;
    }

    public int getAnimalID() {
        return animalID;
    }

    public int getStartHour() {
        return startHour;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public int maxWindow() {
        return maxWindow;
    }
}
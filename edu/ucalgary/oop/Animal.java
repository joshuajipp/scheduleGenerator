package edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;

public class Animal {
    private final int ID;
    private final String NICKNAME;
    private final String SPECIES;
    private ArrayList<Treatments> animalTreatments = new ArrayList<Treatments>();

    public Animal(int id, String name, String species, String user, String password) {
        this.ID = id;
        this.NICKNAME = name;
        this.SPECIES = species;
        String url = "jdbc:mysql://localhost:3306/EWR";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM TREATMENTS");

            while (resultSet.next()) {
                Treatments treatment = new Treatments(resultSet.getInt("TreatmentID"),
                        resultSet.getInt("AnimalID"), resultSet.getInt("TaskID"),
                        resultSet.getInt("StartHour"), user, password);

                animalTreatments.add(treatment);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getID() {
        return ID;
    }

    public String getNickname() {
        return NICKNAME;
    }

    public String getSpecies() {
        return SPECIES;
    }

    public ArrayList<Treatments> getAnimalTreatments() {
        return animalTreatments;
    }
}
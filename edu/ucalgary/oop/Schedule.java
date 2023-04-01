package edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;

public class Schedule {
    private ArrayList<Animal> animalsArray;

    public Schedule(ArrayList<Animal> animalsArray) {
        this.animalsArray = animalsArray;
    }

    public ArrayList<Animal> getAnimalsArray() {
        return animalsArray;
    }

    public static void main(String args[]) {
        String url = "jdbc:mysql://localhost:3306/EWR";
        String user = args[0];
        String password = args[1];
        Connection dbConnection;
        Statement dbStatement;
        ResultSet dbResults;
        String dbQuery;
        ArrayList<Animal> animals = new ArrayList<Animal>();

        try {
            dbConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/EWR", user, password);
            dbStatement = dbConnection.createStatement();
            dbQuery = "SELECT * FROM ANIMALS";
            dbResults = dbStatement.executeQuery(dbQuery);
            while (dbResults.next()) {
                Animal animal = new Animal(dbResults.getInt("AnimalID"),
                        dbResults.getString("AnimalNickname"), dbResults.getString("AnimalSpecies"));
                animals.add(animal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
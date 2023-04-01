package edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;

public class Schedule {
    private ArrayList<Animal> animalsArray;

    public Schedule(ArrayList<Animal> animals) {
        this.animalsArray = animals;
    }

    public Schedule(ArrayList<Animal> animals, ArrayList<Treatments> treatments) {
        this.animalsArray = animals;
        addTreatments(treatments);
    }

    public void addTreatments(ArrayList<Treatments> treatments) {
        for (Animal animal : getAnimalsArray()) {
            animal.addTreatments(treatments);
        }
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
        ArrayList<Treatments> treatments = new ArrayList<Treatments>();

        try {
            dbConnection = DriverManager.getConnection(
                    url, user, password);
            dbStatement = dbConnection.createStatement();
            dbQuery = "SELECT * FROM ANIMALS";
            dbResults = dbStatement.executeQuery(dbQuery);
            while (dbResults.next()) {
                Animal animal = new Animal(dbResults.getInt("AnimalID"),
                        dbResults.getString("AnimalNickname"), dbResults.getString("AnimalSpecies"));
                animals.add(animal);
            }
            dbStatement.close();

            dbStatement = dbConnection.createStatement();
            dbQuery = "SELECT * FROM ewr.treatments as treats"
                    .concat("JOIN ewr.tasks tasks ON treats.TaskID = tasks.TaskID");
            dbResults = dbStatement.executeQuery(dbQuery);
            while (dbResults.next()) {
                Treatments treatment = new Treatments(dbResults.getInt("TreatmentID"),
                        dbResults.getInt("AnimalID"), dbResults.getInt("StartHour"),
                        dbResults.getString("Description"), dbResults.getInt("Duration"),
                        dbResults.getInt("MaxWindow"));
                treatments.add(treatment);
            }
            dbStatement.close();
            dbConnection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
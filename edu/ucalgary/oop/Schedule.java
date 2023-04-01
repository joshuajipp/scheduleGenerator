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

    // public void createConnection() {
    // try {
    // dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/EWR",
    // username, password);
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    // public ArrayList<Animal> selectAnimals() {
    // try {
    // Statement myStmt = dbConnect.createStatement();
    // results = myStmt.executeQuery("SELECT * FROM ANIMALS");
    // while (results.next()) {
    // int id = results.getInt("AnimalID");
    // String nickname = results.getString("AnimalNickname");
    // String species = results.getString("AnimalSpecies");

    // Animal animal = new Animal(id, nickname, species, username, password);
    // animalArray.add(animal);
    // }
    // myStmt.close();
    // } catch (SQLException ex) {
    // ex.printStackTrace();
    // }
    // return animalArray;
    // }

    // public ArrayList<Animal> getTreatmentArrayList() {
    // return animalArray;
    // }

    // public void close() {
    // try {
    // results.close();
    // dbConnect.close();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    public static void main(String args[]) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No args");
        }
        System.out.println(String.format("%s %s", args[0], args[1]));

        final String URL = "jdbc:mysql://localhost:3306/EWR";
        final String USER = args[0];
        final String PASSWORD = args[1];
    }

}
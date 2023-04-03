package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

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

    public ArrayList<Treatments> getSortedTreatments() {
        ArrayList<Treatments> treats = new ArrayList<Treatments>();
        for (Animal animal : getAnimalsArray()) {
            treats.addAll(animal.getAnimalTreatments());
        }

        return sortTreatments(treats);
    }

    private ArrayList<Treatments> sortTreatments(ArrayList<Treatments> treatments) {
        Comparator<Treatments> durationCompare = Comparator.comparingInt(Treatments::getDuration).reversed();
        Comparator<Treatments> setupCompare = Comparator.comparingInt(Treatments::getSetupTime).reversed();
        Comparator<Treatments> startHourCompare = Comparator.comparingInt(Treatments::getStartHour);
        Comparator<Treatments> maxWindowCompare = Comparator.comparingInt(Treatments::getMaxWindow);

        Collections.sort(treatments, durationCompare);
        Collections.sort(treatments, setupCompare);
        Collections.sort(treatments, startHourCompare);
        Collections.sort(treatments, maxWindowCompare);

        return treatments;
    }

    // private ArrayList<Treatments> groupSetupTimes(ArrayList<Treatments>
    // treatments) {
    // Map<Integer, List<Treatments>> prioTreatments = treatments.stream()
    // .collect(Collectors.groupingBy(Treatments::getSetupTime));
    // ArrayList<Treatments> groupedTreatments = new ArrayList<Treatments>();
    // for (Map.Entry<Integer, List<Treatments>> entry : prioTreatments.entrySet())
    // {
    // groupedTreatments.addAll(entry.getValue());
    // }

    // return groupedTreatments;

    // }

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
            dbQuery = "SELECT * FROM ewr.treatments as treats "
                    .concat("JOIN ewr.tasks tasks ON treats.TaskID = tasks.TaskID");
            dbResults = dbStatement.executeQuery(dbQuery);
            while (dbResults.next()) {
                Treatments treatment = new Treatments(dbResults.getInt("AnimalID"),
                        dbResults.getInt("StartHour"), dbResults.getString("Description"),
                        dbResults.getInt("Duration"), dbResults.getInt("MaxWindow"));
                treatments.add(treatment);
            }
            dbStatement.close();
            dbConnection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Schedule taskSchedule = new Schedule(animals, treatments);
        ArrayList<Treatments> sched = taskSchedule.getSortedTreatments();
        for (Treatments treat : sched) {
            System.out.println(String.format(
                    "animalID: %d, startHour: %d, desc: %s, duration: %d, maxWindow: %d, setupTime: %d",
                    treat.getAnimalID(), treat.getStartHour(), treat.getDescription(), treat.getDuration(),
                    treat.getMaxWindow(), treat.getSetupTime()));
        }
        System.out.println(sched.size());

    }
}

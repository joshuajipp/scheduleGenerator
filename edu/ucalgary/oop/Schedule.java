package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;

public class Schedule {
    private ArrayList<Animal> animalsArray;
    private Treatments[][] schedule = new Treatments[24][12];
    private Treatments[][] backupSchedule = new Treatments[24][12];

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

    private void addToBackupSchedule(Treatments treatment) throws IllegalArgumentException {
        for (int hour = 0; hour < 24; hour++) {
            for (int fiveMinBlock = 0; fiveMinBlock < 12; fiveMinBlock++) {
                if (backupSchedule[hour][fiveMinBlock] == null
                        && hour < treatment.getStartHour() + treatment.getMaxWindow()) {
                    int timeBlocks = treatment.getDuration() / 5;
                    if (12 - fiveMinBlock >= timeBlocks) {
                        while (timeBlocks > 0) {
                            backupSchedule[hour][fiveMinBlock] = treatment;
                            fiveMinBlock++;
                            timeBlocks--;
                        }
                        return;
                    }
                }
            }
        }
        throw new IllegalArgumentException("");
    }

    private void addToSchedule(Treatments treatment) {
        for (int hour = 0; hour < 24; hour++) {
            for (int fiveMinBlock = 0; fiveMinBlock < 12; fiveMinBlock++) {
                if (schedule[hour][fiveMinBlock] == null
                        && hour < treatment.getStartHour() + treatment.getMaxWindow()) {
                    int timeBlocks = treatment.getDuration() / 5;
                    if (12 - fiveMinBlock >= timeBlocks) {
                        while (timeBlocks > 0) {
                            schedule[hour][fiveMinBlock] = treatment;
                            fiveMinBlock++;
                            timeBlocks--;
                        }
                        return;
                    }
                }
            }
        }
        addToBackupSchedule(treatment);
    }

    public void createSchedule() {
        ArrayList<Treatments> sortedTreats = getSortedTreatments();
        for (Treatments treatment : sortedTreats) {
            addToSchedule(treatment);
            if (treatment.getMaxWindow() == 3) {
                break;
            }
        }

    }

    public Treatments[][] getSchedule() {
        return schedule;
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

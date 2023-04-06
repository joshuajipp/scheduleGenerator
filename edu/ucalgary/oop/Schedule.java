/**
@author     Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author     Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@version        2.2
@since          1.0
*/
package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;
import java.time.*;

public class Schedule {
    private ArrayList<Animal> animalsArray;
    private Treatments[][] schedule = new Treatments[24][12];
    private Treatments[][] backupSchedule = new Treatments[24][12];

    /*
     * Constructor
     * 
     * @param arraylist of animals.
     */
    public Schedule(ArrayList<Animal> animals) {
        this.animalsArray = animals;
    }

    /*
     * Constructor
     * 
     * @param arraylist of animals
     * 
     * @param arraylist of treatments
     */
    public Schedule(ArrayList<Animal> animals, ArrayList<Treatments> treatments) {
        this.animalsArray = animals;
        addTreatments(treatments);
    }

    /*
     * Adds an array of treatments for each animal in the array
     * 
     * @param arraylist of treatments
     */
    public void addTreatments(ArrayList<Treatments> treatments) {
        for (Animal animal : getAnimalsArray()) {
            animal.addTreatments(treatments);
        }
    }

    /*
     * Getter
     * 
     * @return The arraylist of animals in schedule class
     */
    public ArrayList<Animal> getAnimalsArray() {
        return animalsArray;
    }

    /*
     * Getter
     * 
     * @return The sorted arraylist of treatments in the schedule class
     */
    public ArrayList<Treatments> getSortedTreatments() {
        ArrayList<Treatments> treats = new ArrayList<Treatments>();
        for (Animal animal : getAnimalsArray()) {
            treats.addAll(animal.getAnimalTreatments());
        }

        return sortTreatments(treats);
    }

    /*
     * Sorts an ArrayList of treatments by : duration in descending order, setup in
     * descending order,
     * startHour in ascending order, and maxWindow in ascending order.
     * 
     * @param arraylist of treatments
     * 
     * @return the sorted arraylist of treatment
     */
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

    private Animal getAnimalFromTreatment(Treatments treat) {
        for (Animal animal : getAnimalsArray()) {
            if (animal.getAnimalTreatments().contains(treat)) {
                return animal;
            }
        }
        return null;
    }

    private void addToBackupSchedule(Treatments treatment) throws IllegalArgumentException {
        for (int hour = 0; hour < 24; hour++) {
            for (int fiveMinBlock = 0; fiveMinBlock < 12; fiveMinBlock++) {
                if (backupSchedule[hour][fiveMinBlock] == null
                        && hour < treatment.getStartHour() + treatment.getMaxWindow()
                        && hour >= treatment.getStartHour()) {
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
        throw new IllegalArgumentException(
                String.format("animalID: %d, startHour: %d, description: %s could not fit into schedule.",
                        treatment.getAnimalID(), treatment.getStartHour(), treatment.getDescription()));
    }

    private void addToSchedule(Treatments treatment) {
        for (int hour = 0; hour < 24; hour++) {
            for (int fiveMinBlock = 0; fiveMinBlock < 12; fiveMinBlock++) {
                if (schedule[hour][fiveMinBlock] == null
                        && hour < treatment.getStartHour() + treatment.getMaxWindow()
                        && hour >= treatment.getStartHour()) {
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

    private void scatterSetupTimes(AnimalType animalType) {
        ArrayList<Treatments> treatments = getSortedTreatments();
        int coyoteIndex = 0, foxIndex = 0;
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getSetupTime() == 10) {
                coyoteIndex = i;
                break;
            }
        }
        for (int j = 0; j < treatments.size(); j++) {
            if (treatments.get(j).getSetupTime() == 5) {
                foxIndex = j;
                break;
            }
        }
        for (int hour = 0; hour < 24; hour++) {
            for (int fiveMinBlock = 0; fiveMinBlock < 12; fiveMinBlock++) {
                if (schedule[hour][fiveMinBlock] == null) {
                    for (int i = 0; i < treatments.size(); i++) {
                        if (treatments.get(i).getSetupTime() == 10 && hour >= 19 && hour < 22 &&
                                12 - fiveMinBlock >= 3 && i == coyoteIndex && animalType == AnimalType.COYOTE) {
                            for (int j = 0; j < 2; j++) {
                                schedule[hour][fiveMinBlock] = treatments.get(coyoteIndex);
                                fiveMinBlock++;
                            }
                            while (fiveMinBlock < 12 && treatments.get(coyoteIndex).getSetupTime() == 10) {
                                schedule[hour][fiveMinBlock] = treatments.get(coyoteIndex);
                                fiveMinBlock++;
                                coyoteIndex++;
                            }

                        }
                        if (treatments.get(i).getSetupTime() == 5 && hour >= 0 && hour < 3 &&
                                12 - fiveMinBlock >= 2 && i == foxIndex && animalType == AnimalType.FOX) {

                            schedule[hour][fiveMinBlock] = treatments.get(foxIndex);
                            fiveMinBlock++;

                            while (fiveMinBlock < 12 && treatments.get(foxIndex).getSetupTime() == 5) {
                                schedule[hour][fiveMinBlock] = treatments.get(foxIndex);
                                fiveMinBlock++;
                                foxIndex++;
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleTreatmentSetupTimes() {
        ArrayList<Treatments> treatments = getSortedTreatments();
        int coyoteCount = 0;
        int foxCount = 0;
        Treatments firstCoyote = null;
        Treatments firstFox = null;
        for (Treatments treat : treatments) {
            if (treat.getSetupTime() == 10) {
                if (firstCoyote == null)
                    firstCoyote = treat;

                coyoteCount++;
            }
            if (treat.getSetupTime() == 5) {
                if (firstFox == null)
                    firstFox = treat;
                foxCount++;
            }
        }
        int openCoyoteIndex = -1;
        int openFoxIndex = -1;
        int foxOccupies = 5 + (foxCount * 5);
        int coyoteOccupies = 10 + (coyoteCount * 5);
        boolean foxAlreadyFound = false;
        boolean coyoteAlreadyFound = false;
        for (int hour = 0; hour < 3; hour++) {
            Treatments[] foxHour = schedule[0 + hour];
            Treatments[] coyoteHour = schedule[19 + hour];
            int coyoteSpaces = 0;
            int foxSpaces = 0;
            for (int i = 0; i < 12; i++) {
                if (foxHour[i] == null) {
                    foxSpaces++;
                }
                if (coyoteHour[i] == null) {
                    coyoteSpaces++;
                }
            }
            if (coyoteSpaces * 5 >= coyoteOccupies && !coyoteAlreadyFound) {
                openCoyoteIndex = hour + 19;
                coyoteAlreadyFound = true;
            }
            if (foxSpaces * 5 >= foxOccupies && !foxAlreadyFound) {
                openFoxIndex = hour;
                foxAlreadyFound = true;
            }
        }
        if (openCoyoteIndex != -1) {

            int i = 0;
            while (schedule[openCoyoteIndex][i] != null)
                i++;
            for (int j = 0; j < 2; j++) {
                schedule[openCoyoteIndex][i + j] = firstCoyote;
            }
            i += 2;
            for (Treatments treat : treatments) {

                if (treat.getSetupTime() == 10) {
                    schedule[openCoyoteIndex][i] = treat;
                    i++;
                }
            }

        } else {
            scatterSetupTimes(AnimalType.valueOf("COYOTE"));
        }
        if (openFoxIndex != -1) {

            int i = 0;
            while (schedule[openFoxIndex][i] != null)
                i++;

            schedule[openFoxIndex][i] = firstFox;
            i++;
            for (Treatments treat : treatments) {

                if (treat.getSetupTime() == 5) {
                    schedule[openFoxIndex][i] = treat;
                    i++;
                }
            }

        } else {
            scatterSetupTimes(AnimalType.valueOf("FOX"));
        }
    }

    public void createSchedule() {
        ArrayList<Treatments> sortedTreats = getSortedTreatments();
        Treatments holdKitFeeding = null;
        for (Treatments treatment : sortedTreats) {
            if (treatment.getMaxWindow() == 3) {
                break;
            }
            if (!(treatment.getStartHour() == 20 && treatment.getDescription().equals("Kit feeding"))) {
                addToSchedule(treatment);
            } else {
                holdKitFeeding = treatment;
            }
        }
        handleTreatmentSetupTimes();
        if (holdKitFeeding != null) {
            addToSchedule(holdKitFeeding);
        }
        for (Treatments treatment : sortedTreats) {
            if (treatment.getMaxWindow() >= 3 && treatment.getSetupTime() == 0) {
                addToSchedule(treatment);
            }
        }

    }

    public ArrayList<Integer> getBackupHours() {
        ArrayList<Integer> backupHours = new ArrayList<Integer>();
        for (int i = 0; i < 24; i++) {
            if (backupSchedule[i][0] != null) {
                backupHours.add(i);
            }
        }
        return backupHours;
    }

    public Treatments[][] getSchedule() {
        return schedule;
    }

    public Treatments[][] getBackupSchedule() {
        return backupSchedule;
    }

    public void writeSchedule(Boolean requiresBackup) {
        HashMap<Treatments, Integer> treatmentCount = new HashMap<Treatments, Integer>();

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 12; j++) {
                if (schedule[i][j] != null) {
                    if (treatmentCount.containsKey(schedule[i][j])) {
                        treatmentCount.put(schedule[i][j], treatmentCount.get(schedule[i][j]) + 1);
                    } else {
                        treatmentCount.put(schedule[i][j], 1);
                    }
                }
            }
        }
        String scheduleString = String.format("Schedule for %s\n", LocalDate.now().plusDays(1));

        for (int i = 0; i < 24; i++) {
            if (schedule[i][0] != null)
                scheduleString += String.format("%d:00 - %d:00\n", i, i + 1);
            int j = 0;
            while (j < 12) {
                if (schedule[i][j] != null && treatmentCount.get(schedule[i][j]) == 1) {
                    if (schedule[i][j].getDescription().equals("Feeding")
                            || schedule[i][j].getDescription().equals("Clean Cage")) {
                        Animal currAnimal = getAnimalFromTreatment(schedule[i][j]);
                        ArrayList<String> animalNames = new ArrayList<String>();
                        animalNames.add((getAnimalFromTreatment(schedule[i][j]).getNickname()));
                        j++;
                        while (j < 12 && schedule[i][j] != null
                                && currAnimal.getSpecies() == (getAnimalFromTreatment(
                                        schedule[i][j]).getSpecies())) {
                            animalNames.add((getAnimalFromTreatment(schedule[i][j]).getNickname()));
                            j++;
                        }
                        Set<String> animalNamesSet = new HashSet<String>(animalNames);
                        String animalNamesString = "";
                        Iterator<String> animalNamesIterator = animalNamesSet.iterator();
                        while (animalNamesIterator.hasNext()) {
                            animalNamesString += animalNamesIterator.next();
                            if (animalNamesIterator.hasNext()) {
                                animalNamesString += ", ";
                            }
                        }
                        scheduleString += String.format("%s - %s (%d: %s)\n", schedule[i][j - 1].getDescription(),
                                getAnimalFromTreatment(schedule[i][j - 1]).getSpecies().name().toLowerCase(),
                                animalNamesSet.size(), animalNamesString);
                    } else {
                        scheduleString += String.format("%s (%s)\n", schedule[i][j].getDescription(),
                                getAnimalFromTreatment(schedule[i][j]).getNickname());
                    }
                }
                if (j < 12 && schedule[i][j] != null) {
                    treatmentCount.put(schedule[i][j], treatmentCount.get(schedule[i][j]) - 1);
                }
                j++;

            }
        }
        System.out.println(scheduleString);

    }

    public static String[] main(String args[]) {
        String url = "jdbc:mysql://localhost:3306/EWR";
        String user = args[0];
        String password = args[1];
        // Boolean isFirst = Boolean.parseBoolean(args[2]);
        Boolean isFirst = true;
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
        taskSchedule.createSchedule();
        Treatments[][] schedule = taskSchedule.getSchedule();
        ArrayList<Integer> backupHours = taskSchedule.getBackupHours();

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 12; j++) {
                if (schedule[i][j] != null) {
                    System.out.println(String.format(
                            "{HOUR: %d animalID: %d, startHour: %d, desc: %s, duration: %d, maxWindow: %d, setupTime: %d},",
                            i,
                            schedule[i][j].getAnimalID(), schedule[i][j].getStartHour(),
                            schedule[i][j].getDescription(), schedule[i][j].getDuration(),
                            schedule[i][j].getMaxWindow(), schedule[i][j].getSetupTime()));
                } else {
                    System.out.println("null,");
                }
            }
        }
        if (isFirst && backupHours.size() == 0) {
            taskSchedule.writeSchedule(false);
            String[] returnArray = new String[] { "true" };
            return returnArray;
        }

        if (!isFirst && backupHours.size() > 0) {

            String[] returnArray = new String[] { "true" };
            return returnArray;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < backupHours.size(); i++) {
            sb.append(backupHours.get(i));
            if (i != backupHours.size() - 1) {
                sb.append(" ");
            }
        }
        String myString = sb.toString();

        return new String[] { "false", myString };

    }

}

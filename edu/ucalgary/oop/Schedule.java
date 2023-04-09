/**
@author     Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author     Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version        2.2
@since          1.0
*/
package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;
import java.time.*;
import java.io.*;

public class Schedule {
    private ArrayList<Animal> animalsArray;
    private Treatments[][] schedule = new Treatments[24][12];
    private Treatments[][] backupSchedule = new Treatments[24][12];

    /**
     * Constructor
     * 
     * @param arraylist of animals.
     */
    public Schedule(ArrayList<Animal> animals) {
        this.animalsArray = animals;
    }

    /**
     * Constructor
     * 
     * @param arraylist of treatments
     * @param arrayList of Animal
     */
    public Schedule(ArrayList<Animal> animals, ArrayList<Treatments> treatments) {
        this.animalsArray = animals;
        addTreatments(treatments);
    }

    /**
     * Adds an array of treatments for each animal in the array
     * 
     * @param arraylist of treatments
     */
    public void addTreatments(ArrayList<Treatments> treatments) {
        for (Animal animal : getAnimalsArray()) {
            animal.addTreatments(treatments);
        }
    }

    /**
     * Getter
     * 
     * @return The arraylist of animals in schedule class
     */
    public ArrayList<Animal> getAnimalsArray() {
        return animalsArray;
    }

    /**
     * Retrieves all treatments for all animals and sorts them in ascending order
     * based on multiple attributes.
     * 
     * @return an ArrayList of Treatments objects, sorted by multiple attributes.
     */
    public ArrayList<Treatments> getSortedTreatments() {
        ArrayList<Treatments> treats = new ArrayList<Treatments>();
        for (Animal animal : getAnimalsArray()) {
            treats.addAll(animal.getAnimalTreatments());
        }

        return sortTreatments(treats);
    }

    /**
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

    /**
     * This method takes a Treatment object as input and searches through an array
     * of Animal objects to find the first Animal that has the given Treatment in
     * its list of treatments.
     * 
     * @param treat A Treatments object to search for in the Animal objects'
     *              treatments lists.
     * 
     * @return An Animal object that has the given Treatment in its treatments list
     *         or null if no such Animal is found.
     */
    private Animal getAnimalFromTreatment(Treatments treat) {
        for (Animal animal : getAnimalsArray()) {
            if (animal.getAnimalTreatments().contains(treat)) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Adds the given treatment to the backup schedule.
     * 
     * @param treatment The treatment to add to the backup schedule.
     * 
     * @throws ScheduleOverflowException if there is no available space in the
     *                                   backup schedule for the given treatment.
     */
    private void addToBackupSchedule(Treatments treatment) throws ScheduleOverflowException {
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
        throw new ScheduleOverflowException(
                String.format("animalID: %d, startHour: %d, description: %s could not fit into schedule.",
                        treatment.getAnimalID(), treatment.getStartHour(), treatment.getDescription()));
    }

    /**
     * Adds the given treatment to the schedule. If there is not enough space in the
     * schedule, the treatment will be added to the backup schedule instead.
     * 
     * @param treatment The treatment to add to the schedule.
     * 
     * @throws ScheduleOverflowException if there is not enough space in the
     *                                   schedule to fit all treatments
     */
    private void addToSchedule(Treatments treatment) throws ScheduleOverflowException {
        // Iterates through each hour and five minute block in the schedule to find an
        // available time slot for the treatment.
        for (int hour = 0; hour < 24; hour++) {
            for (int fiveMinBlock = 0; fiveMinBlock < 12; fiveMinBlock++) {
                if (schedule[hour][fiveMinBlock] == null
                        && hour < treatment.getStartHour() + treatment.getMaxWindow()
                        && hour >= treatment.getStartHour()) {
                    // Calculates the number of time blocks needed for the treatment and checks if
                    // there is enough space to fit it in.
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

    /**
     * This method is responsible for scattering treatments with setup times
     * throughout the schedule
     * The method first retrieves the sorted list of treatments from the
     * getSortedTreatments() method.
     * It then finds the index of the treatments with a setup time of 10 for coyotes
     * and 5 for foxes.
     * Using nested for loops, the method iterates through each hour and five-minute
     * block of the schedule grid and checks if the current block is null.
     * If it is null, the method loops through the treatments list to find the
     * treatment with the corresponding setup time
     * and current animal type. If it finds a matching treatment, it checks if the
     * current hour and five-minute block fit within the treatment's scheduling
     * constraints.
     * If the scheduling constraints are met, the method schedules the treatment in
     * the current and subsequent blocks until the setup time is complete.
     * If the scheduling constraints are not met, the method continues to the next
     * available block in the schedule.
     * 
     * @param animalType - an AnimalType parameter representing the type of animal's
     *                   treatments to schedule (either COYOTE or FOX)
     */
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

    /**
     * This method handles the scheduling of treatments that have a setup time of 5
     * minutes or 10 minutes.
     * It sorts all treatments in the animal array and checks the number of coyote
     * and fox treatments.
     * It then finds the first available hour in the schedule that can accommodate
     * all coyote treatments and another hour for all fox treatments.
     * If an open hour is found, the treatments are scheduled in order, starting
     * with the first setup time of each animal.
     * If no open hour is found, it calls scatterSetupTimes() to schedule animal
     * feeding in multiple blocks.
     */
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

    /**
     * Creates a schedule for the animal treatments based on the sorted list of
     * treatments.
     * Treatments are added to the schedule in order, with the exception of Kit
     * feeding treatment which is held separately.
     * After all other treatments have been added to the schedule, treatment setup
     * times are handled to minimize empty spaces
     * and ensure all treatments fit into the schedule without overlap. If there is
     * a Kit feeding treatment, it is added to the
     * schedule after treatment setup times are handled. Finally, any treatments
     * with a maximum window of 3 or more and a setup
     * time of 0 are added to the schedule.
     * 
     * @throws ScheduleOverflowException if there is not enough space in the
     *                                   schedule to fit all treatments
     */
    public void createSchedule() throws ScheduleOverflowException {
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

    /**
     * Returns an ArrayList of backup hours for the treatments in the backup
     * schedule.
     * A backup hour is an hour in which a treatment is scheduled in the backup
     * schedule.
     * 
     * @return ArrayList of Integer representing the hours in which a treatment is
     *         scheduled in the backup schedule
     */
    public ArrayList<Integer> getBackupHours() {
        ArrayList<Integer> backupHours = new ArrayList<Integer>();
        for (int i = 0; i < 24; i++) {
            if (backupSchedule[i][0] != null) {
                backupHours.add(i);
            }
        }
        return backupHours;
    }

    /**
     * Getter
     * 
     * @return main schedule for the next day
     */
    public Treatments[][] getSchedule() {
        return schedule;
    }

    /**
     * Getter
     * 
     * @return backup schedule for the next day
     */
    public Treatments[][] getBackupSchedule() {
        return backupSchedule;
    }

    /**
     * Generates the backup schedule for a given hour and appends it to the overall
     * schedule string. The backup schedule is
     * created by counting the occurrences of each treatment in the backup schedule
     * and only adding treatments that occur once
     * to the final schedule. If a treatment is a feeding, it is grouped by animal
     * species and listed as a single entry with
     * the names of all the animals being fed. If the backup schedule is not
     * required, this method does nothing.
     * 
     * @param hour           the hour for which the backup schedule is to be
     *                       generated
     * 
     * @param scheduleString the current overall schedule string to which the backup
     *                       schedule will be appended
     * 
     * @return the updated overall schedule string with the backup schedule
     *         appended, or the original string if no backup schedule was required
     * 
     */

    private String writeBackup(int hour, String scheduleString) {
        HashMap<Treatments, Integer> treatmentCount = new HashMap<Treatments, Integer>();
        for (int j = 0; j < 12; j++) {
            if (backupSchedule[hour][j] != null) {
                if (treatmentCount.containsKey(backupSchedule[hour][j])) {
                    treatmentCount.put(backupSchedule[hour][j], treatmentCount.get(backupSchedule[hour][j]) + 1);
                } else {
                    treatmentCount.put(backupSchedule[hour][j], 1);
                }
            }
        }
        for (int j = 0; j < 12; j++) {
            if (backupSchedule[hour][j] != null && treatmentCount.get(backupSchedule[hour][j]) == 1) {
                if (backupSchedule[hour][j].getDescription().equals("Feeding")) {
                    Animal currAnimal = getAnimalFromTreatment(backupSchedule[hour][j]);
                    ArrayList<String> animalNames = new ArrayList<String>();
                    animalNames.add((getAnimalFromTreatment(backupSchedule[hour][j]).getNickname()));
                    j++;
                    while (j < 12 && backupSchedule[hour][j] != null
                            && currAnimal.getSpecies() == (getAnimalFromTreatment(
                                    backupSchedule[hour][j]).getSpecies())) {
                        animalNames.add((getAnimalFromTreatment(backupSchedule[hour][j]).getNickname()));
                        j++;
                    }
                    String animalNamesString = "";
                    for (int k = 0; k < animalNames.size(); k++) {
                        animalNamesString += animalNames.get(k);
                        if (k != animalNames.size() - 1) {
                            animalNamesString += ", ";
                        }

                    }
                    scheduleString += String.format("%s - %s (%d: %s)\n", backupSchedule[hour][j - 1].getDescription(),
                            getAnimalFromTreatment(backupSchedule[hour][j - 1]).getSpecies().name().toLowerCase(),
                            animalNames.size(), animalNamesString);
                } else {
                    scheduleString += String.format("%s (%s)\n", backupSchedule[hour][j].getDescription(),
                            getAnimalFromTreatment(backupSchedule[hour][j]).getNickname());
                }

            }
            if (j < 12 && backupSchedule[hour][j] != null) {
                treatmentCount.put(backupSchedule[hour][j], treatmentCount.get(backupSchedule[hour][j]) - 1);
            }

        }

        return scheduleString;
    }

    /**
     * Returns a HashMap that contains a count of the number of times each treatment
     * appears in the schedule.
     * 
     * @return a HashMap that contains a count of the number of times each treatment
     *         appears in the schedule
     */
    private HashMap<Treatments, Integer> countDuplicateTreatments() {
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
        return treatmentCount;
    }

    /**
     * This method generates a schedule string for the upcoming day based on the
     * treatments scheduled in the "schedule" array.
     * The schedule string includes the time of each treatment, the animal(s)
     * receiving the treatment, and the type of treatment.
     * If "requiresBackup" is true and a backup volunteer is available for a given
     * hour, the string will indicate that a backup volunteer
     * will be present. The schedule string is then written to a file using the
     * "writeScheduleTxt" method.
     * 
     * @param requiresBackup a boolean indicating whether a backup volunteer is
     *                       required for the schedule
     * 
     * @throws IOException if there is an error writing the schedule to the file
     */
    public void writeSchedule() {
        HashMap<Treatments, Integer> treatmentCount = countDuplicateTreatments();
        String scheduleString = "";
        for (int i = 0; i < 24; i++) {
            if (schedule[i][0] != null) {
                if (backupSchedule[i][0] != null) {
                    scheduleString += String.format("%d:00 - %d:00 [+ backup volunteer]\n", i, i + 1);

                } else {
                    scheduleString += String.format("%d:00 - %d:00\n", i, i + 1);
                }
            }
            for (int j = 0; j < 12; j++) {
                if (schedule[i][j] != null && treatmentCount.get(schedule[i][j]) == 1) {
                    if (schedule[i][j].getDescription().equals("Feeding")) {
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
                        String animalNamesString = "";
                        for (int k = 0; k < animalNames.size(); k++) {
                            animalNamesString += animalNames.get(k);
                            if (k != animalNames.size() - 1) {
                                animalNamesString += ", ";
                            }

                        }
                        scheduleString += String.format("%s - %s (%d: %s)\n", schedule[i][j - 1].getDescription(),
                                getAnimalFromTreatment(schedule[i][j - 1]).getSpecies().name().toLowerCase(),
                                animalNames.size(), animalNamesString);
                    } else {
                        scheduleString += String.format("%s (%s)\n", schedule[i][j].getDescription(),
                                getAnimalFromTreatment(schedule[i][j]).getNickname());
                    }
                    if (backupSchedule[i][0] != null) {
                        scheduleString = writeBackup(i, scheduleString);
                    }
                }
                if (j < 12 && schedule[i][j] != null) {
                    treatmentCount.put(schedule[i][j], treatmentCount.get(schedule[i][j]) - 1);
                }

            }
        }
        writeScheduleTxt(scheduleString);

    }

    /**
     * Writes a given string to a text file.
     * 
     * @param scheduleString the string to be written to a file
     */

    private void writeScheduleTxt(String scheduleString) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        try {
            File file = new File(String.format("%s.txt", tomorrow));
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(scheduleString);
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This method is the main method for the EWR application. It connects to a
     * MySQL database
     * to retrieve information about animals and their treatments, creates a
     * schedule using the
     * Schedule class, and writes the schedule to a text file.
     * 
     * @param args An array of command-line arguments containing the user's MySQL
     *             username, password,and a boolean flag indicating whether this is
     *             the first
     *             schedule generation (true) or a subsequent generation (false).
     * 
     * @return An array of strings containing "true" if the schedule was
     *         successfully generated and written, or "false" followed by a
     *         space-separated
     *         list of backup hours if backup volunteers are needed.
     * 
     * @throws ScheduleOverflowException If there are too many treatments scheduled
     *                                   for a single day.
     * 
     */
    public static String[] main(String args[]) throws ScheduleOverflowException {
        String url = "jdbc:mysql://localhost:3306/EWR";
        String user = args[0];
        String password = args[1];
        Boolean isFirst = Boolean.parseBoolean(args[2]);
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
        ArrayList<Integer> backupHours = taskSchedule.getBackupHours();
        System.out.println(isFirst);
        if (isFirst && backupHours.size() == 0) {
            taskSchedule.writeSchedule();
            String[] returnArray = new String[] { "true" };
            return returnArray;
        }

        if (!isFirst && backupHours.size() > 0) {
            taskSchedule.writeSchedule();
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

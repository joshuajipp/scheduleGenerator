package edu.ucalgary.oop;

public class Treatments {
    private int treatmentID;
    private int animalID;
    private int startHour;
    private String description;
    private int duration;
    private int maxWindow;
    private int setupTime;

    public Treatments(int treatmentID, int animalID, int startHour,
            String description, int duration, int maxWindow) {
        this.treatmentID = treatmentID;
        this.animalID = animalID;
        this.startHour = startHour;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
        this.setupTime = 0;
    }

    public Treatments(int treatmentID, int animalID, int startHour,
            String description, int duration, int maxWindow, int setupTime) {
        this.treatmentID = treatmentID;
        this.animalID = animalID;
        this.startHour = startHour;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
        this.setupTime = setupTime;
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

    public int getMaxWindow() {
        return maxWindow;
    }

    public int getSetupTime() {
        return setupTime;
    }
}
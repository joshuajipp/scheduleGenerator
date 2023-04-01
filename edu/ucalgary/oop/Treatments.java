package edu.ucalgary.oop;

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
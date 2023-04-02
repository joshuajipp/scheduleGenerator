package edu.ucalgary.oop;

public class Treatments {
    private int animalID;
    private int startHour;
    private String description;
    private int duration;
    private int maxWindow;
    private int setupTime;

    public Treatments(int animalID, int startHour,
            String description, int duration, int maxWindow) {

        this.animalID = animalID;
        this.startHour = startHour;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
        this.setupTime = 0;
    }

    public Treatments(int animalID, int startHour,
            String description, int duration, int maxWindow, int setupTime) {

        this.animalID = animalID;
        this.startHour = startHour;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
        this.setupTime = setupTime;
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

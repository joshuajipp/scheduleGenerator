package edu.ucalgary.oop;

public class Treatments {
    private Animal animalID;
    private Tasks taskID;
    private int startHour;

    public Treatments(int treatmentID, int animalID, int taskID, int startHour) {
        this.animalID = id;
        this.taskID = task;
        this.startHour = startHour;
    }

    public Animal getAnimalID() {
        return animalID;
    }

    public Tasks getTaskID() {
        return taskID;
    }

    public int getStartHour() {
        return startHour;
    }
}
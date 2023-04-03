/**
@author 	Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author 	Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@version    	1.1
@since  	    1.0
*/
package edu.ucalgary.oop;

public class Treatments {
    private int animalID;
    private int startHour;
    private String description;
    private int duration;
    private int maxWindow;
    private int setupTime;
    /*Constructor */
    public Treatments(int animalID, int startHour,
            String description, int duration, int maxWindow) {

        this.animalID = animalID;
        this.startHour = startHour;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
        this.setupTime = 0;
    }
    /*Constructor */
    public Treatments(int animalID, int startHour,
            String description, int duration, int maxWindow, int setupTime) {

        this.animalID = animalID;
        this.startHour = startHour;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
        this.setupTime = setupTime;
    }
    /*Getter*/
    public int getAnimalID() {
        return animalID;
    }
    /*Getter*/
    public int getStartHour() {
        return startHour;
    }
    /*Getter */
    public String getDescription() {
        return description;
    }
    /*Getter*/
    public int getDuration() {
        return duration;
    }
    /*Getter*/
    public int getMaxWindow() {
        return maxWindow;
    }
    /*Getter*/
    public int getSetupTime() {
        return setupTime;
    }
}

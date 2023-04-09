/**
@author 	Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author 	Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version    	1.1
@since  	   1.0
*/
package edu.ucalgary.oop;

public enum AnimalType {
    /**
     * Recognized types of species this program supports
     */
    COYOTE(19, 10, 5, 5),
    FOX(0, 5, 5, 5),
    PORCUPINE(19, 0, 5, 10),
    BEAVER(8, 0, 5, 5),
    RACCOON(0, 0, 5, 5),
    KITS(0, 0, 0, 30);

    private final int FEED_START_TIME;
    private final int PREP_DURATION;
    private final int FEED_DURATION;
    private final int CLEAN_DURATION;

    /* Constructor */
    AnimalType(int feedStartTime, int prepDuration,
            int feedDuration, int cleanDuration) {

        this.FEED_START_TIME = feedStartTime;
        this.PREP_DURATION = prepDuration;
        this.FEED_DURATION = feedDuration;
        this.CLEAN_DURATION = cleanDuration;

    }

    /** Getter */
    public int getFeedStartTime() {
        return FEED_START_TIME;
    }

    /** Getter */
    public int getPrepDuration() {
        return PREP_DURATION;
    }

    /** Getter */
    public int getFeedDuration() {
        return FEED_DURATION;
    }

    /** Getter */
    public int getCleanDuration() {
        return CLEAN_DURATION;
    }

}
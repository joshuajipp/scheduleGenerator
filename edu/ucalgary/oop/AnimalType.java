package edu.ucalgary.oop;

public enum AnimalType {
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

    AnimalType(int feedStartTime, int prepDuration,
            int feedDuration, int cleanDuration) {

        this.FEED_START_TIME = feedStartTime;
        this.PREP_DURATION = prepDuration;
        this.FEED_DURATION = feedDuration;
        this.CLEAN_DURATION = cleanDuration;

    }

    public int getFeedStartTime() {
        return FEED_START_TIME;
    }

    public int getPrepDuration() {
        return PREP_DURATION;
    }

    public int getFeedDuration() {
        return FEED_DURATION;
    }

    public int getCleanDuration() {
        return CLEAN_DURATION;
    }

}
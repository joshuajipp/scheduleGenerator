package edu.ucalgary.oop;

import java.util.ArrayList;

public class Animal {
    private final int ID;
    private final String NICKNAME;
    private final AnimalType SPECIES;
    private ArrayList<Treatments> animalTreatments = new ArrayList<Treatments>();

    public Animal(int id, String name, String species) {
        this.ID = id;
        this.NICKNAME = name;
        if (name.split("\\s+").length > 1) {
            this.SPECIES = AnimalType.KITS;

            animalTreatments.add(new Treatments(ID, 0, String.format(
                    "Clean cage for the Kits", name), SPECIES.getFeedDuration(), 24));
        } else {
            this.SPECIES = AnimalType.valueOf(species.toUpperCase());

            animalTreatments.add(new Treatments(ID, SPECIES.getFeedStartTime(),
                    String.format("Feed %s", name), SPECIES.getCleanDuration(),
                    3, SPECIES.getPrepDuration()));
            animalTreatments.add(new Treatments(ID, 0, String.format(
                    "Clean cage for %s", name), SPECIES.getFeedDuration(), 24));
        }

    }

    public void addTreatments(ArrayList<Treatments> treatments) {
        for (Treatments treat : treatments) {
            if (treat.getAnimalID() == getID()) {
                animalTreatments.add(treat);
            }
        }
    }

    public int getID() {
        return ID;
    }

    public String getNickname() {
        return NICKNAME;
    }

    public AnimalType getSpecies() {
        return SPECIES;
    }

    public ArrayList<Treatments> getAnimalTreatments() {
        return animalTreatments;
    }
}

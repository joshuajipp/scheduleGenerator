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
        this.SPECIES = AnimalType.valueOf(species.toUpperCase());
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
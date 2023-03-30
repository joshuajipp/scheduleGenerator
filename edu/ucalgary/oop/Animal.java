package edu.ucalgary.oop;

public class Animal {
    private final int ID;
    private final String NICKNAME;
    private final String SPECIES;

    public Animal(int id, String name, String species) {
        this.ID = id;
        this.NICKNAME = name;
        this.SPECIES = species;
    }

    public int getID() {
        return ID;
    }

    public String getNickname() {
        return NICKNAME;
    }

    public String getSpecies() {
        return SPECIES;
    }
}
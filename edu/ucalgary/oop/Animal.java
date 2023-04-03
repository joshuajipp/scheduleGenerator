/**
@author 	Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author 	Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@version    	1.2
@since  	1.0
*/
package edu.ucalgary.oop;

import java.util.ArrayList;

public class Animal {
    private final int ID;
    private final String NICKNAME;
    private final AnimalType SPECIES;
    private ArrayList<Treatments> animalTreatments = new ArrayList<Treatments>();

    /*
     * Constructor
     * 
     * @param id, name, species of the animal. If there is more than one name,
     * species is set to AnimalType Kits.
     * Otherwise, species is set if species appear in the AnimalType enum.
     * Sets specific treatments for type of species including prep,feed and clean
     * time.
     */
    public Animal(int id, String name, String species) {
        this.ID = id;
        this.NICKNAME = name;
        if (name.split("\\s+").length > 1) {
            this.SPECIES = AnimalType.KITS;

            animalTreatments.add(new Treatments(ID, 0, String.format(
                    "Clean cage for the Kits", name), SPECIES.getCleanDuration(), 24));
        } else {
            this.SPECIES = AnimalType.valueOf(species.toUpperCase());

            animalTreatments.add(new Treatments(ID, SPECIES.getFeedStartTime(),
                    String.format("Feed %s", name), SPECIES.getFeedDuration(),
                    3, SPECIES.getPrepDuration()));
            animalTreatments.add(new Treatments(ID, 0, String.format(
                    "Clean cage for %s", name), SPECIES.getCleanDuration(), 24));
        }

    }

    /*
     * add treatments in the animalTreatments arraylist if animalID matches with ID
     * on treatments database.
     * 
     * @param arrayList of treatments
     */
    public void addTreatments(ArrayList<Treatments> treatments) {
        for (Treatments treat : treatments) {
            if (treat.getAnimalID() == getID()) {
                animalTreatments.add(treat);
            }
        }
    }

    /* Getter */
    public int getID() {
        return ID;
    }

    /* Getter */
    public String getNickname() {
        return NICKNAME;
    }

    /* Getter */
    public AnimalType getSpecies() {
        return SPECIES;
    }

    /* Getter */
    public ArrayList<Treatments> getAnimalTreatments() {
        return animalTreatments;
    }
}

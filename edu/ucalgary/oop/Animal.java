/**
@author 	Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author 	Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version    	1.2
@since  	1.0
*/
package edu.ucalgary.oop;

import java.util.ArrayList;

/**
 * Animal class to store information about each animal
 * 
 */
public class Animal {
    private final int ID;
    private final String NICKNAME;
    private final AnimalType SPECIES;
    private ArrayList<Treatments> animalTreatments = new ArrayList<Treatments>();

    /**
     * Constructor
     * If there is more than one name,
     * species is set to AnimalType Kits.
     * Otherwise, species is set if species appear in the AnimalType
     * enum.
     * Sets specific treatments for type of species including
     * prep,feed and clean
     * time.
     * 
     * @param id      id of animal
     * @param name    name of animal
     * @param species species string
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
                    "Feeding", SPECIES.getFeedDuration(),
                    3, SPECIES.getPrepDuration()));
            animalTreatments.add(new Treatments(ID, 0,
                    String.format("Clean %s Cage", SPECIES.name().toLowerCase()), SPECIES.getCleanDuration(), 24));
        }

    }

    /**
     * add treatments in the animalTreatments arraylist if animalID matches with ID
     * on treatments database.
     * 
     * @param treatments ArrayList of Treatments
     */
    public void addTreatments(ArrayList<Treatments> treatments) {
        for (Treatments treat : treatments) {
            if (treat.getAnimalID() == getID()) {
                animalTreatments.add(treat);
            }
        }
    }

    /**
     * Getter
     * 
     * @return ID of animal
     */
    public int getID() {
        return ID;
    }

    /**
     * Getter
     * 
     * @return NICKNAME of animal
     */
    public String getNickname() {
        return NICKNAME;
    }

    /**
     * Getter
     * 
     * @return SPECIES of animal
     */
    public AnimalType getSpecies() {
        return SPECIES;
    }

    /**
     * Getter
     * 
     * @return animalTreatments
     */
    public ArrayList<Treatments> getAnimalTreatments() {
        return animalTreatments;
    }
}

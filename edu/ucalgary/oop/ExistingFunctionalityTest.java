/**
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version    	1.0
@since  	1.0
*/
package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

public class ExistingFunctionalityTest {

    /**@Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }*/
    @Test
    public void testAnimaltwonames() {        
        ArrayList<Treatments> animalTreatments = new ArrayList<Treatments>();
        ArrayList<Treatments> expanimalTreatments = new ArrayList<Treatments>();
        Animal kits = new Animal(1, "Sherlock Holmes", "Fox");
        AnimalType expectedanimal = AnimalType.KITS;

        animalTreatments = kits.getAnimalTreatments();
        expanimalTreatments.add(new Treatments(1, 0, String.format(
            "Clean cage for the Kits", kits.getNickname()), 5, 24));

        assertEquals(expectedanimal, kits.getSpecies());
        /**assertEquals(expanimalTreatments, animalTreatments);*/
        assertEquals(new HashSet<Treatments>(expanimalTreatments), new HashSet<Treatments>(animalTreatments));

    }
}

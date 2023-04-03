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
        int expID = 1;
        String expName = "Sherlock Holmes";
        String species = "Fox";
        ArrayList<Treatments> expanimalTreatments = new ArrayList<Treatments>();
        Animal kits = new Animal(expID, expName, species);
        AnimalType expectedanimal = AnimalType.KITS;

        expanimalTreatments.add(new Treatments(1, 0, String.format(
            "Clean cage for the Kits", kits.getNickname()), 5, 24));

        assertEquals(expectedanimal, kits.getSpecies());

        /**Fix assert equals for new arraylists*/

        /**assertEquals(expanimalTreatments, animalTreatments);*/
        /**assertEquals(new HashSet<Treatments>(expanimalTreatments), new HashSet<Treatments>(animalTreatments));*/

    }
}

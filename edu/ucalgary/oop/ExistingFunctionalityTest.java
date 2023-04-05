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
        String initspecies = "Fox";

        Animal animal = new Animal(expID, expName, initspecies);
        AnimalType expectedanimal = AnimalType.KITS;
        ArrayList <Treatments> treatments = animal.getAnimalTreatments();


        assertEquals(expectedanimal, animal.getSpecies());

        /**Fix assert equals for new arraylists*/

        /**assertEquals(expanimalTreatments, animalTreatments);*/

    }

    @Test
    /*
     * Singular animal name given, species remains unchanged as specified and treatments arraylist updated with feeding and cage cleaning 
     */
    public void testAnimalelsecondition() {
        assertTrue( true );
    }

    /*@Test
    public void*/ 
}

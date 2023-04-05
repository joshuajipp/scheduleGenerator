/**
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version    	1.0
@since  	1.0
*/
package edu.ucalgary.oop;

import org.junit.*;
import org.junit.runners.model.TestTimedOutException;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

public class ExistingFunctionalityTest {

    /* @Before */

    /**@Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }*/
    @Test 
    /*
     * Correct animal input test, animal has a one word name
     */
    public void testanimalcorrectinputonename() {
        Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");

        assertEquals(1, rightanimal.getID());
        assertEquals("Fluffy", rightanimal.getNickname());
        assertEquals(AnimalType.PORCUPINE, rightanimal.getSpecies());

    }

    @Test
    /*
     * Singular animal name given, species remains unchanged as specified and treatments arraylist updated with feeding and cage cleaning 
     */
    public void testAnimalelseconditiontreatments() {
        Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
        ArrayList <Treatments> testtreatments = rightanimal.getAnimalTreatments();
        ArrayList <Treatments> exptreatments = new ArrayList<Treatments>();
        exptreatments.add(new Treatments(1, 19, "Feed Fluffy", 5,
        3, 0));
        exptreatments.add(new Treatments(1, 0, "Clean cage for Fluffy", 10, 24));

        for (int i = 0; i < testtreatments.size(); i++) {
            Treatments test = testtreatments.get(i);
            Treatments exp = exptreatments.get(i);
        
            assertEquals(exp.getAnimalID(), test.getAnimalID()); /* Test case runs */
            assertEquals(exp.getStartHour(), test.getStartHour()); /* Test case runs */
            assertEquals(exp.getDescription(), test.getDescription()); /* Test case runs */
            assertEquals(exp.getDuration(), test.getDuration()); /* Test case runs */
            assertEquals(exp.getMaxWindow(), test.getMaxWindow()); /* Test case runs */
            if (i==1) {
                assertEquals(exp.getSetupTime(), test.getSetupTime());
            }
        }
    }

    @Test
    /*
     * Correct animal input test, animal has two names so checks for species change to kits and correct treatments
     * "if" statement is invoked
     */
    public void testAnimaltwonames() {        
        Animal animal = new Animal(5, "Sherlock Holmes", "Raccoon");
        AnimalType expectedanimal = AnimalType.KITS;
        ArrayList <Treatments> testtreatments = animal.getAnimalTreatments();
        ArrayList <Treatments> exptreatments = new ArrayList<Treatments>();
        exptreatments.add(new Treatments(5, 0, "Clean cage for the Kits", 30, 24));
        Treatments test = testtreatments.get(0);
        Treatments exp = exptreatments.get(0);

        assertEquals(expectedanimal, animal.getSpecies());
        assertEquals(exp.getAnimalID(), test.getAnimalID());
        assertEquals(exp.getStartHour(), test.getStartHour());
        assertEquals(exp.getDescription(), test.getDescription()); /*shouldn't description include pet name? */
        assertEquals(exp.getDuration(), test.getDuration());
        assertEquals(exp.getMaxWindow(), test.getMaxWindow());
       
       

    }

    @Test(expected = IllegalArgumentException.class)    
    /*
     * Testing species not included in enum gives IllegalArgumentException
     */
    public void testAnimalConstructorWithInvalidSpecies() {
    Animal wrongspecies = new Animal(1, "Hiss", "Basilisk");
    }


}

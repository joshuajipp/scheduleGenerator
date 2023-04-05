/**
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version    	1.1
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
    public void testAnimalCorrectInputOneName() {
        Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");

        assertEquals(1, rightanimal.getID());
        assertEquals("Fluffy", rightanimal.getNickname());
        assertEquals(AnimalType.PORCUPINE, rightanimal.getSpecies());

    }

    @Test
    /*
     * Animal name is one word, species remains unchanged as specified and treatments arraylist updated with feeding and cage cleaning 
     */
    public void testAnimalElseConditionTreatments() {
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
    public void TestAnimalTwonames() {        
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
    public void testAnimalWithInvalidSpecies() {
    Animal wrongspecies = new Animal(1, "Hiss", "Basilisk");
    }

    @Test 
    /*
     * Testing addTreatments method with correct input and matching ID that satisfies "if" expression 
    */
    public void testAddTreatments() {
    Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
    ArrayList<Treatments> exptreatments = new ArrayList<Treatments>();
    ArrayList<Treatments> testtreatments = new ArrayList<Treatments>();
    Treatments treatment1 = new Treatments(1, 19, "Feed Fluffy", 5, 3, 0);
    Treatments treatment2 = new Treatments(1, 0, "Clean cage for Fluffy", 10, 24);
    exptreatments.add(treatment1);
    exptreatments.add(treatment2);
    rightanimal.addTreatments(exptreatments);
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
     * Testing addTreatments with animal ID that does not match
     * Expected behaviour is treatment arraylist has a size of 0 because "if" statement is not satisfied
    */
    public void testAddTreatmentsWithInvalidAnimalID() {
    Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
    ArrayList<Treatments> treatments = new ArrayList<Treatments>();
    Treatments treatment1 = new Treatments(2, 19, "Feed Fluffy", 5, 3, 0);
    Treatments treatment2 = new Treatments(2, 0, "Clean cage for Fluffy", 10, 24);
    treatments.add(treatment1);
    treatments.add(treatment2);
    rightanimal.addTreatments(treatments);
    assertEquals("Treatments were added to animal's treatment list even though ID did not match!", 0, (rightanimal.getAnimalTreatments()).size());
    }

    @Test 
    /*
    * Test method addTreatments of the Animal class by adding more than 2, differing, treatments
    */
    public void testAddMultipleTreatments() {
    Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
    ArrayList<Treatments> treatments = new ArrayList<Treatments>();
    treatments.add(new Treatments(1, 19, "Feed Fluffy", 5, 3, 0));
    treatments.add(new Treatments(1, 10, "Give Fluffy medicine", 5, 2, 5));
    treatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
    rightanimal.addTreatments(treatments);
    
    for (Treatments treat : treatments) {
        assertTrue(rightanimal.getAnimalTreatments().contains(treat));
        }
    }

    @Test
    public void testAddTreatmentsForMultipleAnimals() {
    Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
    Animal animal2 = new Animal(2, "Woof", "Coyote");
    ArrayList<Treatments> treatments = new ArrayList<Treatments>();
    treatments.add(new Treatments(1, 19, "Feed Fluffy", 5, 3, 0));
    treatments.add(new Treatments(2, 10, "Feed Woof", 20, 2, 10));
    treatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
    treatments.add(new Treatments(2, 30, "Give Woof medicine", 5, 2));
    rightanimal.addTreatments(treatments);
    ArrayList<Treatments> treatments1 = rightanimal.getAnimalTreatments();
    ArrayList<Treatments> treatments2 = animal2.getAnimalTreatments();

    assertEquals(2, treatments1.size());
    assertEquals(2, treatments2.size());

    }

}

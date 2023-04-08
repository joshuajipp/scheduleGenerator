/**
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version    	1.3
@since  	1.0
*/
package edu.ucalgary.oop;

import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

 public class ExistingFunctionalityTest {
    
    private Animal rightanimal;
    private ArrayList<Animal> animalsArray;
    @Before
    public void setup() {
        rightanimal = new Animal(1, "Fluffy", "Porcupine");
        animalsArray = new ArrayList<>();
    }

    @Test 
     /*
     * Correct animal input test, animal has a one word name
     */
    public void testAnimalCorrectInputOneName() {
        assertEquals(1, rightanimal.getID());
        assertEquals("Fluffy", rightanimal.getNickname());
        assertEquals(AnimalType.PORCUPINE, rightanimal.getSpecies());

    }

    @Test
    /*
     * Animal name is one word, species remains unchanged as specified and treatments arraylist updated with feeding and cage cleaning 
     */
    public void testAnimalElseConditionTreatments() {
        ArrayList <Treatments> testtreatments = rightanimal.getAnimalTreatments();
        ArrayList <Treatments> exptreatments = new ArrayList<Treatments>();
        exptreatments.add(new Treatments(1, 19, "Feeding", 5,
        3, 0));
        exptreatments.add(new Treatments(1, 0, "Clean porcupine Cage", 10, 24));

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

    // Test constructor with invalid species
    @Test(expected = IllegalArgumentException.class)
    /*
     * 
     */
    public void testAnimalConstructor_invalidSpecies() {
        new Animal(1, "Fluffy", "Invalid Species");
    }
      
    @Test
    /*
     * 
     */
    public void testAnimalConstructor_idZero() {
        Animal animal = new Animal(0, "Fluffy", "Porcupine");
        assertEquals(0, animal.getID());
        assertEquals("Fluffy", animal.getNickname());
        assertEquals(AnimalType.PORCUPINE, animal.getSpecies());
      }
      
    @Test(expected = NullPointerException.class)
    /*
     * 
     */
    public void testAnimalConstructor_nullSpecies() {
        new Animal(1, "Fluffy", null);
        }
      
    @Test 
    /*
     * Testing addTreatments method with correct input and matching ID that satisfies "if" expression 
    */
    public void testAddTreatments() {
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
        assertEquals(exp.getAnimalID(), test.getAnimalID()); 
        assertEquals(exp.getStartHour(), test.getStartHour()); 
        assertEquals(exp.getDescription(), test.getDescription()); 
        assertEquals(exp.getDuration(), test.getDuration()); 
        assertEquals(exp.getMaxWindow(), test.getMaxWindow()); 
        if (i==1) {
            assertEquals(exp.getSetupTime(), test.getSetupTime());
            }
        }
    }

    @Test
    /*
     * Testing addTreatments with animal ID that does not match
     * 
    */
    public void testAddTreatmentsWithInvalidAnimalID() {
    ArrayList<Treatments> treatments = new ArrayList<Treatments>();
    Treatments treatment1 = new Treatments(2, 19, "Walk Fluffy", 5, 3, 0);
    Treatments treatment2 = new Treatments(3, 0, "Play with Fluffy", 10, 24);
    treatments.add(treatment1);
    treatments.add(treatment2);
    rightanimal.addTreatments(treatments);
    /* We expect 2 treatments to be in AnimalTreatments, the treatments that populate by way of the Animal constructor, meaning the above treatments were not added */
    assertEquals("Treatments were added to animal's treatment list even though ID did not match!", 2, (rightanimal.getAnimalTreatments()).size());
    }

    @Test 
    /*
    * Test method addTreatments of the Animal class by adding more than 2, differing, treatments
    */
    public void testAddMultipleTreatments() {
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
    /*
     * 
    */
    public void testAddTreatmentsForMultipleAnimals() {
    Animal animal2 = new Animal(2, "Woof", "Coyote");
    ArrayList<Treatments> treatments = new ArrayList<Treatments>();
    treatments.add(new Treatments(1, 19, "Feeding Fluffy", 5, 3, 0));
    treatments.add(new Treatments(2, 10, "Feeding Woof", 20, 2, 10));
    treatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
    treatments.add(new Treatments(2, 30, "Give Woof medicine", 5, 2));
    rightanimal.addTreatments(treatments);
    animal2.addTreatments(treatments);
    ArrayList<Treatments> treatments1 = rightanimal.getAnimalTreatments();
    ArrayList<Treatments> treatments2 = animal2.getAnimalTreatments();

    /*expected is 4 for both because new animal object will cause constructor "Animal" to populate AnimalTreatments with 2 treatments first, then the other 2 treatments we add here makes 4 */
     assertEquals(4, treatments1.size()); 
     assertEquals(4, treatments2.size()); 

    }

    @Test
    /*
     * 
     */
    public void testAddTreatmentsToAnimalWithNoExistingTreatments() {
        ArrayList<Treatments> treatments = new ArrayList<>();
        rightanimal.addTreatments(treatments);
        assertEquals(2, rightanimal.getAnimalTreatments().size());

    }

    @Test
    /*
     * 
     */
    public void testGetSortedTreatmentsEmptyAnimals() {
    
        Schedule schedule = new Schedule(animalsArray);
        ArrayList<Treatments> treatments = schedule.getSortedTreatments();
        assertTrue(treatments.isEmpty());
    }

    @Test
    /*
     * 
     */
    public void testGetSortedTreatmentsOneAnimalNoTreatments() {
     
        animalsArray.add(rightanimal);
        Schedule schedule = new Schedule(animalsArray);
        ArrayList<Treatments> sortedTreatments = schedule.getSortedTreatments();
        assertTrue(sortedTreatments.size() == 2);
    }

    @Test
    /*
     * 
     */
    public void testGetSortedTreatmentsOneAnimalOneTreatment() {
        ArrayList<Treatments> testtreatments = new ArrayList<Treatments>();
        animalsArray.add(rightanimal);
        Schedule schedule = new Schedule(animalsArray);
        Treatments treatment1 = new Treatments(1, 19, "Feed Fluffy", 5, 3, 0);
        testtreatments.add(treatment1);
        schedule.addTreatments(testtreatments);
        ArrayList<Treatments> sortedTreatments = schedule.getSortedTreatments();
        assertEquals(3, sortedTreatments.size());
    }
    
   
    @Test
    /*
     * 
     */
    public void testGetSortedTreatmentsManyAnimalsManyTreatments() {
        // Create test data
        ArrayList<Treatments> testTreatments = new ArrayList<>();
        Schedule schedule = new Schedule(animalsArray);
        Animal animal2 = new Animal(2, "Woof", "Coyote");

        animalsArray.add(animal2);
        animalsArray.add(rightanimal);
        testTreatments.add(new Treatments(1, 19, "Feeding", 5, 3, 0));
        testTreatments.add(new Treatments(2, 10, "Feeding", 20, 2, 10));
        testTreatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
        testTreatments.add(new Treatments(2, 30, "Give Woof medicine", 5, 2));
    
        // Sort the treatments
        ArrayList<Treatments> sortedTreatments = schedule.getSortedTreatments();
        assertEquals(sortedTreatments.size(), testTreatments.size());
        
        for (int i = 0; i < testTreatments.size(); i++) { 
            Treatments exp = sortedTreatments.get(i);
            Treatments last = sortedTreatments.get(testTreatments.size()-1);
            assertTrue(exp.getAnimalID() <= last.getAnimalID()); 
            assertTrue(exp.getDuration() >= last.getDuration());
            assertTrue(exp.getSetupTime() >= last.getSetupTime());
            assertTrue(exp.getStartHour() >= last.getStartHour()); /*CHECK, shouldn't starthour be arranged in ascending order? */
            assertTrue(exp.getMaxWindow() <= last.getMaxWindow());

        }

    
    }
    
    @Test
    /*
     * 
     */
    public void testGetSortedTreatmentsSameAnimalTwiceManyTreatments() {
        // Create test data
        Animal rightanimal2 = new Animal(1, "Fluffy", "Porcupine");
        animalsArray.add(rightanimal2);
        animalsArray.add(rightanimal);
        ArrayList<Treatments> testTreatments = new ArrayList<>();
        Schedule schedule = new Schedule(animalsArray);
        testTreatments.add(new Treatments(1, 19, "Feeding", 5, 3, 0));
        testTreatments.add(new Treatments(2, 10, "Feeding", 20, 2, 10));
        testTreatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
        testTreatments.add(new Treatments(2, 30, "Give Woof medicine", 5, 2));
    
        // Sort the treatments
        ArrayList<Treatments> sortedTreatments = schedule.getSortedTreatments();
        assertEquals(sortedTreatments.size(), testTreatments.size());
        
        for (int i = 0; i < testTreatments.size(); i++) { 
            Treatments exp = sortedTreatments.get(i);
            Treatments last = sortedTreatments.get(testTreatments.size()-1);
            assertTrue(exp.getAnimalID() <= last.getAnimalID()); 
            assertTrue(exp.getDuration() <= last.getDuration());
            assertTrue(exp.getSetupTime() >= last.getSetupTime());
            assertTrue(exp.getStartHour() >= last.getStartHour()); /*CHECK, shouldn't starthour be arranged in ascending order? */
            assertTrue(exp.getMaxWindow() <= last.getMaxWindow());

        }
}
    @Test
    public void testCreateScheduleNoTreatments() throws ScheduleOverflowException {
        animalsArray.add(rightanimal);
        Schedule schedule = new Schedule(animalsArray);
        schedule.createSchedule();
        Treatments[][] scheduleArray = schedule.getSchedule();
        assertEquals(24, scheduleArray.length);
        assertEquals(12, scheduleArray[0].length);
    }
    @Test(expected = ScheduleOverflowException.class)
    /*
     * 
     */
    public void testCreateScheduleThrowsException() throws ScheduleOverflowException {
        ArrayList<Treatments> manyTreatments = new ArrayList<>();
        animalsArray.add(rightanimal);
        Schedule schedule = new Schedule(animalsArray);
        for (int i = 0; i < 15; i++) {
            Treatments treatment =  new Treatments(1, 1, "Feeding", 5, 4, 0);
            manyTreatments.add(treatment);
            schedule.addTreatments(manyTreatments);
        }
        schedule.createSchedule();
    }

    @Test
    /*
     * 
     */
    public void testCreateScheduleAddsManyTreatments() throws ScheduleOverflowException {
        // test that all treatments are added to the schedule
        animalsArray.add(rightanimal);
        Schedule schedule = new Schedule(animalsArray);
        ArrayList<Treatments> testTreatments = new ArrayList<>();
        testTreatments.add(new Treatments(1, 19, "Feeding", 5, 3, 0));
        testTreatments.add(new Treatments(2, 10, "Feeding", 20, 2, 10));
        testTreatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
        schedule.createSchedule();
        schedule.addTreatments(testTreatments);
        int expectedScheduleSize = testTreatments.size();
        int actualScheduleSize = 0;
        Treatments[][] scheduleArray = schedule.getSchedule();
        for (int i = 0; i < scheduleArray.length; i++) {
            for (int j = 0; j < scheduleArray[i].length; j++) {
                if (scheduleArray[i][j] != null) {
                    actualScheduleSize += 1;
                }
            }
        }
        assertEquals(expectedScheduleSize, actualScheduleSize);
        
    }

    @Test
    /*
     * 
     */
    public void testCreateScheduleHoldsKitFeeding() throws ScheduleOverflowException {
        // test that Kit feeding treatment is held separately and added to the schedule later
        Animal animal = new Animal(5, "Sherlock Holmes", "Kits");
        animalsArray.add(animal);
        Schedule schedule = new Schedule(animalsArray);
        ArrayList<Treatments> testTreatments = new ArrayList<>(animal.getAnimalTreatments());
        testTreatments.add(new Treatments(2, 0, "Kit feeding", 1, 2, 10));
        schedule.addTreatments(testTreatments);
        schedule.createSchedule();
        Treatments[][] scheduleArray = schedule.getSchedule();
        assertEquals(testTreatments.get(0).getDescription(), scheduleArray[0][0].getDescription());
        assertEquals(testTreatments.get(0).getStartHour(), scheduleArray[0][0].getStartHour());
}



}  


    
  

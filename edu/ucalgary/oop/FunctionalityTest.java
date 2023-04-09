/**
@author 	Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author 	Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version    1.5
@since  	1.0
*/
package edu.ucalgary.oop;

import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;


 public class FunctionalityTest {
    
    private Animal rightanimal;
    private ArrayList<Animal> animalsArray;
    private Schedule schedule; 

    @Before
    public void setup() {
        rightanimal = new Animal(1, "Fluffy", "Porcupine");
        animalsArray = new ArrayList<>();
        schedule = new Schedule(animalsArray);
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
        // Create an ArrayList of treatments for the animal to be tested
        ArrayList<Treatments> testTreatments = rightanimal.getAnimalTreatments();

        // Create an ArrayList of expected treatments for the animal
        ArrayList<Treatments> expectedTreatments = new ArrayList<Treatments>();
        expectedTreatments.add(new Treatments(1, 19, "Feeding", 5, 3, 0));
        expectedTreatments.add(new Treatments(1, 0, "Clean porcupine Cage", 10, 24));

        // Compare each treatment to ensure it matches the expected treatment 
        for (int i = 0; i < testTreatments.size(); i++) {
            // Get the current treatment to be tested and the expected treatment
            Treatments test = testTreatments.get(i);
            Treatments exp = expectedTreatments.get(i);

            // Assert that each field of the treatment object matches the expected value
            assertEquals(exp.getAnimalID(), test.getAnimalID()); 
            assertEquals(exp.getStartHour(), test.getStartHour()); 
            assertEquals(exp.getDescription(), test.getDescription()); 
            assertEquals(exp.getDuration(), test.getDuration());
            assertEquals(exp.getMaxWindow(), test.getMaxWindow()); 

            // For the first treatment, also test the setup time field
            if (i==0) {
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
        // Create an animal with two names and check that the expected species is KITS
        Animal animal = new Animal(5, "Sherlock Holmes", "Raccoon");
        AnimalType expectedanimal = AnimalType.KITS;
        assertEquals(expectedanimal, animal.getSpecies());
        
        // Check that the animal's treatments match the expected treatments
        ArrayList<Treatments> testtreatments = animal.getAnimalTreatments();
        ArrayList<Treatments> exptreatments = new ArrayList<Treatments>();
        exptreatments.add(new Treatments(5, 0, "Clean cage for the Kits", 30, 24));
        Treatments test = testtreatments.get(0);
        Treatments exp = exptreatments.get(0);
        assertEquals(exp.getAnimalID(), test.getAnimalID());
        assertEquals(exp.getStartHour(), test.getStartHour());
        assertEquals(exp.getDescription(), test.getDescription()); 
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
    // Test to ensure an IllegalArgumentException is thrown for an invalid species input in the constructor
    public void testAnimalConstructor_invalidSpecies() {
        new Animal(1, "Fluffy", "Invalid Species");
    }
      
    @Test
    // Test to ensure the animal constructor correctly initializes the animal object when the ID is set to 0
    public void testAnimalConstructor_idZero() {
        Animal animal = new Animal(0, "Fluffy", "Porcupine");
        assertEquals(0, animal.getID());
        assertEquals("Fluffy", animal.getNickname());
        assertEquals(AnimalType.PORCUPINE, animal.getSpecies());
      }
      
    @Test(expected = NullPointerException.class)
    // Test to ensure a NullPointerException is thrown for null species input in the constructor
    public void testAnimalConstructor_nullSpecies() {
        new Animal(1, "Fluffy", null);
        }
      
    @Test 
    /*
     * Testing addTreatments method with correct input and matching ID that satisfies "if" expression 
    */
    public void testAddTreatments() {
        // Define the expected treatments
        ArrayList<Treatments> exptreatments = new ArrayList<Treatments>();
        Treatments treatment1 = new Treatments(1, 19, "Feed Fluffy", 5, 3, 0);
        Treatments treatment2 = new Treatments(1, 0, "Clean cage for Fluffy", 10, 24);
        exptreatments.add(treatment1);
        exptreatments.add(treatment2);
        
        // Add the expected treatments to the animal object
        rightanimal.addTreatments(exptreatments);
        
        // Get the treatments from the animal object
        ArrayList<Treatments> testtreatments = rightanimal.getAnimalTreatments();
        
        // Compare each treatment to ensure it matches the expected treatment
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
     * Test adding treatments for multiple animals and ensuring
     * that the treatments are added correctly for each animal
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
        // cause the Animal constructor to populate AnimalTreatments with 2 treatments
        // first, and then the other 2 treatments we add here make 4
        assertEquals(4, treatments1.size()); 
        assertEquals(4, treatments2.size()); 

    }

    @Test
    /*
     * Test adding an empty treatments list to an animal
     * and verify that the animal's existing treatments remain unchanged
     */
    public void testAddTreatmentsToAnimalWithNoExistingTreatments() {
        ArrayList<Treatments> treatments = new ArrayList<>();
        rightanimal.addTreatments(treatments);
        assertEquals(2, rightanimal.getAnimalTreatments().size());

    }

    @Test
    /*
     * Test getting a sorted list of treatments when the animals list is empty
     * and verify that the returned treatments list is empty
     */
    public void testGetSortedTreatmentsEmptyAnimals() {
    
        
        ArrayList<Treatments> treatments = schedule.getSortedTreatments();
        assertTrue(treatments.isEmpty());
    }

    @Test
    /*
     * Test getting a sorted list of treatments when there is one animal
     * with no additional treatments added (only the two initial treatments)
     */
    public void testGetSortedTreatmentsOneAnimalNoTreatments() {
     
        animalsArray.add(rightanimal);
        
        ArrayList<Treatments> sortedTreatments = schedule.getSortedTreatments();
        assertTrue(sortedTreatments.size() == 2);
    }

    @Test
    /*
     * Test getting a sorted list of treatments when there is one animal
     * with one additional treatment added
     */
    public void testGetSortedTreatmentsOneAnimalOneTreatment() {
        ArrayList<Treatments> testtreatments = new ArrayList<Treatments>();
        animalsArray.add(rightanimal);
        
        Treatments treatment1 = new Treatments(1, 19, "Feed Fluffy", 5, 3, 0);
        testtreatments.add(treatment1);
        schedule.addTreatments(testtreatments);
        ArrayList<Treatments> sortedTreatments = schedule.getSortedTreatments();
        assertEquals(3, sortedTreatments.size());
    }
    
   
    @Test
    /*
     * Test getting a sorted list of treatments when there are multiple animals
     * with multiple treatments added
     */
    public void testGetSortedTreatmentsManyAnimalsManyTreatments() {
        // Create test data
        ArrayList<Treatments> testTreatments = new ArrayList<>();
        
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
        
        // Check if the sorted list is sorted correctly     
        for (int i = 0; i < testTreatments.size(); i++) { 
            Treatments exp = sortedTreatments.get(i);
            Treatments last = sortedTreatments.get(testTreatments.size()-1);
            assertTrue(exp.getAnimalID() <= last.getAnimalID()); 
            assertTrue(exp.getDuration() >= last.getDuration());
            assertTrue(exp.getSetupTime() >= last.getSetupTime());
            assertTrue(exp.getStartHour() >= last.getStartHour()); 
            assertTrue(exp.getMaxWindow() <= last.getMaxWindow());

        }

    
    }
    
    @Test
    /*
     * Test sorting treatments for the same animal with different treatments
     */
    public void testGetSortedTreatmentsSameAnimalTwiceManyTreatments() {
        // Create test data
        Animal rightanimal2 = new Animal(1, "Fluffy", "Porcupine");
        animalsArray.add(rightanimal2);
        animalsArray.add(rightanimal);
        ArrayList<Treatments> testTreatments = new ArrayList<>();
        
        testTreatments.add(new Treatments(1, 19, "Feeding", 5, 3, 0));
        testTreatments.add(new Treatments(2, 10, "Feeding", 20, 2, 10));
        testTreatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
        testTreatments.add(new Treatments(2, 30, "Give Woof medicine", 5, 2));
    
        // Sort the treatments
        ArrayList<Treatments> sortedTreatments = schedule.getSortedTreatments();
        assertEquals(sortedTreatments.size(), testTreatments.size());
        
        // Check if the sorted list is sorted correctly     
        for (int i = 0; i < testTreatments.size(); i++) { 
            Treatments exp = sortedTreatments.get(i);
            Treatments last = sortedTreatments.get(testTreatments.size()-1);
            assertTrue(exp.getAnimalID() <= last.getAnimalID()); 
            assertTrue(exp.getDuration() <= last.getDuration());
            assertTrue(exp.getSetupTime() >= last.getSetupTime());
            assertTrue(exp.getStartHour() >= last.getStartHour()); 
            assertTrue(exp.getMaxWindow() <= last.getMaxWindow());

        }
}
    @Test
    /*
    * This test checks if the createSchedule method initializes the schedule correctly when no treatments are added
    */
    public void testCreateScheduleNoTreatments() throws ScheduleOverflowException {
        //Add an animal to the animalsArray 
        animalsArray.add(rightanimal); 
        
        // Call the createSchedule method and Retrieve the schedule array
        schedule.createSchedule();
        Treatments[][] scheduleArray = schedule.getSchedule();
        assertEquals(24, scheduleArray.length); // Check if the schedule array has 24 rows (one for each hour of the day)
        assertEquals(12, scheduleArray[0].length); // Check if the schedule array has 12 columns (one for each 5-minute interval within an hour)
    }
    @Test(expected = ScheduleOverflowException.class)
    /*
     * Test for ScheduleOverflowException when the number of treatments
     * exceeds the available schedule slots
     */
    public void testCreateScheduleThrowsException() throws ScheduleOverflowException {
        ArrayList<Treatments> manyTreatments = new ArrayList<>();
        animalsArray.add(rightanimal);
        
        for (int i = 0; i < 15; i++) {
            Treatments treatment =  new Treatments(1, 1, "Feeding", 5, 4, 0);
            manyTreatments.add(treatment);
            schedule.addTreatments(manyTreatments);
        }
        schedule.createSchedule();
    }
    @Test(expected = ScheduleOverflowException.class)
    /*
     * Test for ScheduleOverflowException when the startHour
     * exceeds the available hours of the day
     */
    public void testCreateScheduleThrowsExceptionForBigStartHour() throws ScheduleOverflowException {
        ArrayList<Treatments> manyTreatments = new ArrayList<>();
        animalsArray.add(rightanimal);
        
        Treatments treatment =  new Treatments(1, 30, "Feeding", 5, 4, 0);
        manyTreatments.add(treatment);
        schedule.addTreatments(manyTreatments);
        schedule.createSchedule();
    }

    @Test
    /*
     * Test if all treatments are added to the schedule correctly
     */
    public void testCreateScheduleAddsManyTreatments() throws ScheduleOverflowException {
        animalsArray.add(rightanimal);
        
        // Create a list of treatments to add to the schedule
        ArrayList<Treatments> testTreatments = new ArrayList<>();
        testTreatments.add(new Treatments(1, 19, "Feeding", 5, 3, 0));
        testTreatments.add(new Treatments(2, 10, "Feeding", 20, 2, 10));
        testTreatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
        
        // Create a schedule and add the treatments to it
        schedule.createSchedule();
        schedule.addTreatments(testTreatments);
        
        // Check that the expected number of treatments were added to the schedule
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
     * This test checks if the createSchedule method properly handles Kit feeding treatments
     */
    public void testCreateScheduleHoldsKitFeeding() throws ScheduleOverflowException {
        // test that Kit feeding treatment is held separately and added to the schedule later
        Animal animal = new Animal(5, "Sherlock Holmes", "Kits");
        animalsArray.add(animal);
        
        ArrayList<Treatments> testTreatments = new ArrayList<>(animal.getAnimalTreatments());
        testTreatments.add(new Treatments(2, 0, "Kit feeding", 1, 2, 10));
        schedule.addTreatments(testTreatments);
        schedule.createSchedule();
        Treatments[][] scheduleArray = schedule.getSchedule();
        assertEquals(testTreatments.get(0).getDescription(), scheduleArray[0][0].getDescription());
        assertEquals(testTreatments.get(0).getStartHour(), scheduleArray[0][0].getStartHour());
    }   

    @Test
    /*
    * Tests calling writeSchedule with a schedule that has no treatments 
    */
    public void testWriteScheduleNoTreatments() throws IOException, ScheduleOverflowException {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // create an empty schedule file
        Schedule schedule = new Schedule(new ArrayList<>());
        schedule.createSchedule();
        schedule.writeSchedule();
        String scheduleString = new String(Files.readAllBytes(Paths.get(String.format("%s.txt", tomorrow))), StandardCharsets.UTF_8);
        // Test that schedule string is empty when there are no treatments scheduled
        assertEquals("", scheduleString);
        // delete the schedule file
        Files.deleteIfExists(Paths.get(String.format("%s.txt", tomorrow)));
    }

    @Test
    /*
     * Tests writeSchedule with one extra feeding treatment added into schedule other than the initialized treatments
     */
    public void testWriteScheduleOneTreatment() throws IOException, ScheduleOverflowException {
        // Set up the test by initializing the animal and the schedule
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String animalNameOne = rightanimal.getSpecies().toString().toLowerCase();
        animalsArray.add(rightanimal);
        schedule.createSchedule();
        
        // Add the extra treatment to the schedule
        ArrayList<Treatments> treatments = new ArrayList<>();
        treatments.add(new Treatments(1, 19, "Feeding", 5, 3, 0));
        schedule.addTreatments(treatments);
        
        // Write the schedule to a file and read it back in as a string
        schedule.writeSchedule();
        String scheduleString = new String(Files.readAllBytes(Paths.get(String.format("%s.txt", tomorrow))), StandardCharsets.UTF_8);
        
        // Verify that the output string contains the correct information
        assertTrue(scheduleString.contains(rightanimal.getNickname()));
        assertTrue(scheduleString.contains(String.format("Feeding - %s", animalNameOne)));
        assertTrue(scheduleString.contains("Clean porcupine Cage"));
        assertFalse(scheduleString.contains("+ backup volunteer"));
        
        // Clean up by deleting the file
        Files.deleteIfExists(Paths.get(String.format("%s.txt", tomorrow)));
    }

    @Test
    /*
     * Tests when schedule is created with two animals in animalsArray, 
     * schedule should have just the default treatments and no backup volunteer is called in
     * Feeding times will be exactly the same, so one should overtake the other
     */
    public void testWriteScheduleTwoSAnimals() throws IOException, ScheduleOverflowException {
        // Test that schedule string includes more than one scheduled treatment
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Animal animal2 = new Animal(2, "Woof", "Coyote");
        String animalSpeciesOne = rightanimal.getSpecies().toString().toLowerCase();
        String animalSpeciesTwo = animal2.getSpecies().toString().toLowerCase();
        animalsArray.add(rightanimal);
        animalsArray.add(animal2);
        
        schedule.createSchedule();
        schedule.writeSchedule();
        String scheduleString = new String(Files.readAllBytes(Paths.get(String.format("%s.txt", tomorrow))), StandardCharsets.UTF_8);
        System.out.println(scheduleString);
        assertTrue(scheduleString.contains(rightanimal.getNickname()));
        assertTrue(scheduleString.contains(animal2.getNickname()));
        assertFalse(scheduleString.contains(String.format("Feeding - %s", animalSpeciesOne)));
        assertTrue(scheduleString.contains(String.format("Feeding - %s", animalSpeciesTwo)));
        assertTrue(scheduleString.contains(String.format("Clean %s Cage", animalSpeciesOne)));
        assertTrue(scheduleString.contains(String.format("Clean %s Cage", animalSpeciesTwo)));
        assertFalse(scheduleString.contains("+ backup volunteer"));

        Files.deleteIfExists(Paths.get(String.format("%s.txt", tomorrow)));
    }

    @Test
    /*
     * Tests writing schedule with more than one animal and many treatments, some unusual treatment types
     * As long as times are distinct, all treatments should show up and are tested by description
     * because treatment descriptions are what make up the schedule string
     */
    public void testWriteScheduleTwoSAnimalsManyTreatmentsAdded() throws IOException, ScheduleOverflowException {
        // Test that schedule string includes more than one scheduled treatment
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Animal animal2 = new Animal(2, "Woof", "Coyote");
        String animalSpeciesOne = rightanimal.getSpecies().toString().toLowerCase();
        String animalSpeciesTwo = animal2.getSpecies().toString().toLowerCase();
        String animalNameOne = rightanimal.getNickname().toString();
        animalsArray.add(rightanimal);
        animalsArray.add(animal2);
        
        ArrayList<Treatments> treatments = new ArrayList<>();
        treatments.add(new Treatments(1, 6, "Feeding", 5, 3, 0));
        treatments.add(new Treatments(2, 7, "Feeding", 20, 2, 10));
        treatments.add(new Treatments(1, 21, "Play with Fluffy", 15, 2, 10));
        treatments.add(new Treatments(2, 13, "Give Woof medicine", 5, 2));
        schedule.addTreatments(treatments);
        schedule.createSchedule();
        schedule.writeSchedule();
        String scheduleString = new String(Files.readAllBytes(Paths.get(String.format("%s.txt", tomorrow))), StandardCharsets.UTF_8);
        System.out.println(scheduleString);
        assertTrue(scheduleString.contains(rightanimal.getNickname()));
        assertTrue(scheduleString.contains(animal2.getNickname()));
        //Check for specific descriptions which will be displayed from the treatments, this works for specific spread out times as the schedule tries to optimize depending on startHour
        assertTrue(scheduleString.contains(String.format("Feeding - %s", animalSpeciesOne)));
        assertTrue(scheduleString.contains(String.format("Feeding - %s", animalSpeciesTwo)));
        assertTrue(scheduleString.contains(String.format("Clean %s Cage", animalSpeciesOne)));
        assertTrue(scheduleString.contains(String.format("Clean %s Cage", animalSpeciesTwo)));
        assertTrue(scheduleString.contains(String.format("Play with %s", animalNameOne)));
        assertFalse(scheduleString.contains("+ backup volunteer"));

        Files.deleteIfExists(Paths.get(String.format("%s.txt", tomorrow)));
    }
    
    @Test(expected = IOException.class)
    public void testWriteSchedule_IOException() throws IOException, ScheduleOverflowException {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // create an empty schedule file
        Schedule schedule = new Schedule(new ArrayList<>());
        schedule.createSchedule();
        schedule.writeSchedule();
        // invalid file name is given
        String scheduleString = new String(Files.readAllBytes(Paths.get(String.format("%HELLO.txt", tomorrow))), StandardCharsets.UTF_8);
        // Test that schedule string is empty when there are no treatments scheduled
        assertEquals("", scheduleString);
        // delete the schedule file
        Files.deleteIfExists(Paths.get(String.format("%sHELLO.txt", tomorrow)));
    }
    @Test(expected = ScheduleOverflowException.class)
    public void testWriteSchedule_ScheduleOverflowException() throws IOException, ScheduleOverflowException {
        // Test that schedule string includes one scheduled treatment
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        animalsArray.add(rightanimal);
        
        ArrayList<Treatments> treatments = new ArrayList<>();
        
        // Add one treatment at a time to the schedule until it overflows
        for (int i = 0; i < 100; i++) {
            Treatments overflowTreat = new Treatments(1, 30, "Feeding", 5, 3, 0);
            treatments.add(overflowTreat);
            schedule.addTreatments(treatments);
            try {
                schedule.createSchedule();
            } catch (ScheduleOverflowException e) {
                // Verify that the exception was thrown and delete the file if it was created
                Files.deleteIfExists(Paths.get(String.format("%s.txt", tomorrow)));
                throw e;
            }
        }
        // Write the schedule to a file 
        schedule.writeSchedule();
    }
   
}

    
  

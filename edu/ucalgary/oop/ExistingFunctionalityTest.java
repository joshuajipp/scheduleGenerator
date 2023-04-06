// /**
// @author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
// @author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
// @version    	1.1
// @since  	1.0
// */
// package edu.ucalgary.oop;

// import org.junit.*;
// import org.junit.runners.model.TestTimedOutException;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.junit.runners.Parameterized;
// import org.junit.runners.Parameterized.Parameters;

// import static org.junit.Assert.*;

// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.Arrays;
// import java.util.Collection;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionListener;
// import java.awt.event.MouseListener;

// public class ExistingFunctionalityTest {
    
//     private final int animalId;
//     private final String nickname;
//     private final AnimalType species;
    
//     public ExistingFunctionalityTest(int animalId, String nickname, AnimalType species) {
//         this.animalId = animalId;
//         this.nickname = nickname;
//         this.species = species;
//     }
    
//     @Parameters
//     public static Collection<Object[]> data() {
//         return Arrays.asList(new Object[][]{
//                 {1, "Fluffy", AnimalType.PORCUPINE},
//                 {2, "Woof", AnimalType.COYOTE},
//                 {3, "Sherlock Holmes", AnimalType.KITS}
//         });
//     }

//     /* @Before */

//     /**@Test
//     public void shouldAnswerWithTrue()
//     {
//         assertTrue( true );
//     }*/
//     @Test 
//     /*
//      * Correct animal input test, animal has a one word name
//      */
//     public void testAnimalCorrectInputOneName() {
//         Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");

//         assertEquals(1, rightanimal.getID());
//         assertEquals("Fluffy", rightanimal.getNickname());
//         assertEquals(AnimalType.PORCUPINE, rightanimal.getSpecies());

//     }

//     @Test
//     /*
//      * Animal name is one word, species remains unchanged as specified and treatments arraylist updated with feeding and cage cleaning 
//      */
//     public void testAnimalElseConditionTreatments() {
//         Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
//         ArrayList <Treatments> testtreatments = rightanimal.getAnimalTreatments();
//         ArrayList <Treatments> exptreatments = new ArrayList<Treatments>();
//         exptreatments.add(new Treatments(1, 19, "Feed Fluffy", 5,
//         3, 0));
//         exptreatments.add(new Treatments(1, 0, "Clean cage for Fluffy", 10, 24));

//         for (int i = 0; i < testtreatments.size(); i++) {
//             Treatments test = testtreatments.get(i);
//             Treatments exp = exptreatments.get(i);
        
//             assertEquals(exp.getAnimalID(), test.getAnimalID()); /* Test case runs */
//             assertEquals(exp.getStartHour(), test.getStartHour()); /* Test case runs */
//             assertEquals(exp.getDescription(), test.getDescription()); /* Test case runs */
//             assertEquals(exp.getDuration(), test.getDuration()); /* Test case runs */
//             assertEquals(exp.getMaxWindow(), test.getMaxWindow()); /* Test case runs */
//             if (i==1) {
//                 assertEquals(exp.getSetupTime(), test.getSetupTime());
//             }
//         }
//     }

//     @Test
//     /*
//      * Correct animal input test, animal has two names so checks for species change to kits and correct treatments
//      * "if" statement is invoked
//      */
//     public void TestAnimalTwonames() {        
//         Animal animal = new Animal(5, "Sherlock Holmes", "Raccoon");
//         AnimalType expectedanimal = AnimalType.KITS;
//         ArrayList <Treatments> testtreatments = animal.getAnimalTreatments();
//         ArrayList <Treatments> exptreatments = new ArrayList<Treatments>();
//         exptreatments.add(new Treatments(5, 0, "Clean cage for the Kits", 30, 24));
//         Treatments test = testtreatments.get(0);
//         Treatments exp = exptreatments.get(0);

//         assertEquals(expectedanimal, animal.getSpecies());
//         assertEquals(exp.getAnimalID(), test.getAnimalID());
//         assertEquals(exp.getStartHour(), test.getStartHour());
//         assertEquals(exp.getDescription(), test.getDescription()); /*shouldn't description include pet name? */
//         assertEquals(exp.getDuration(), test.getDuration());
//         assertEquals(exp.getMaxWindow(), test.getMaxWindow());
       
       

//     }

//     @Test(expected = IllegalArgumentException.class)    
//     /*
//      * Testing species not included in enum gives IllegalArgumentException
//      */
//     public void testAnimalWithInvalidSpecies() {
//     Animal wrongspecies = new Animal(1, "Hiss", "Basilisk");
//     }

//     @Test 
//     /*
//      * Testing addTreatments method with correct input and matching ID that satisfies "if" expression 
//     */
//     public void testAddTreatments() {
//     Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
//     ArrayList<Treatments> exptreatments = new ArrayList<Treatments>();
//     ArrayList<Treatments> testtreatments = new ArrayList<Treatments>();
//     Treatments treatment1 = new Treatments(1, 19, "Feed Fluffy", 5, 3, 0);
//     Treatments treatment2 = new Treatments(1, 0, "Clean cage for Fluffy", 10, 24);
//     exptreatments.add(treatment1);
//     exptreatments.add(treatment2);
//     rightanimal.addTreatments(exptreatments);
//     for (int i = 0; i < testtreatments.size(); i++) {
//         Treatments test = testtreatments.get(i);
//         Treatments exp = exptreatments.get(i);
//         assertEquals(exp.getAnimalID(), test.getAnimalID()); /* Test case runs */
//         assertEquals(exp.getStartHour(), test.getStartHour()); /* Test case runs */
//         assertEquals(exp.getDescription(), test.getDescription()); /* Test case runs */
//         assertEquals(exp.getDuration(), test.getDuration()); /* Test case runs */
//         assertEquals(exp.getMaxWindow(), test.getMaxWindow()); /* Test case runs */
//         if (i==1) {
//             assertEquals(exp.getSetupTime(), test.getSetupTime());
//             }
//         }
//     }

//     @Test
//     /*
//      * Testing addTreatments with animal ID that does not match
//      * Expected behaviour is treatment arraylist has a size of 0 because "if" statement is not satisfied
//     */
//     public void testAddTreatmentsWithInvalidAnimalID() {
//     Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
//     ArrayList<Treatments> treatments = new ArrayList<Treatments>();
//     Treatments treatment1 = new Treatments(2, 19, "Feed Fluffy", 5, 3, 0);
//     Treatments treatment2 = new Treatments(2, 0, "Clean cage for Fluffy", 10, 24);
//     treatments.add(treatment1);
//     treatments.add(treatment2);
//     rightanimal.addTreatments(treatments);
//     assertEquals("Treatments were added to animal's treatment list even though ID did not match!", 0, (rightanimal.getAnimalTreatments()).size());
//     }

//     @Test 
//     /*
//     * Test method addTreatments of the Animal class by adding more than 2, differing, treatments
//     */
//     public void testAddMultipleTreatments() {
//     Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
//     ArrayList<Treatments> treatments = new ArrayList<Treatments>();
//     treatments.add(new Treatments(1, 19, "Feed Fluffy", 5, 3, 0));
//     treatments.add(new Treatments(1, 10, "Give Fluffy medicine", 5, 2, 5));
//     treatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
//     rightanimal.addTreatments(treatments);
    
//     for (Treatments treat : treatments) {
//         assertTrue(rightanimal.getAnimalTreatments().contains(treat));
//         }
//     }

//     @Test
//     public void testAddTreatmentsForMultipleAnimals() {
//     Animal rightanimal = new Animal(1, "Fluffy", "Porcupine");
//     Animal animal2 = new Animal(2, "Woof", "Coyote");
//     ArrayList<Treatments> treatments = new ArrayList<Treatments>();
//     treatments.add(new Treatments(1, 19, "Feed Fluffy", 5, 3, 0));
//     treatments.add(new Treatments(2, 10, "Feed Woof", 20, 2, 10));
//     treatments.add(new Treatments(1, 20, "Play with Fluffy", 15, 2, 10));
//     treatments.add(new Treatments(2, 30, "Give Woof medicine", 5, 2));
//     rightanimal.addTreatments(treatments);
//     ArrayList<Treatments> treatments1 = rightanimal.getAnimalTreatments();
//     ArrayList<Treatments> treatments2 = animal2.getAnimalTreatments();

//     assertEquals(2, treatments1.size());
//     assertEquals(2, treatments2.size());

//     }
    
//     @Test
//     public void testAnimalTypeValues() {
//         assertEquals(19, AnimalType.COYOTE.getFeedStartTime());
//         assertEquals(10, AnimalType.COYOTE.getPrepDuration());
//         assertEquals(5, AnimalType.COYOTE.getFeedDuration());
//         assertEquals(5, AnimalType.COYOTE.getCleanDuration());

//         assertEquals(0, AnimalType.FOX.getFeedStartTime());
//         assertEquals(5, AnimalType.FOX.getPrepDuration());
//         assertEquals(5, AnimalType.FOX.getFeedDuration());
//         assertEquals(5, AnimalType.FOX.getCleanDuration());

//         assertEquals(19, AnimalType.PORCUPINE.getFeedStartTime());
//         assertEquals(0, AnimalType.PORCUPINE.getPrepDuration());
//         assertEquals(5, AnimalType.PORCUPINE.getFeedDuration());
//         assertEquals(10, AnimalType.PORCUPINE.getCleanDuration());

//         assertEquals(8, AnimalType.BEAVER.getFeedStartTime());
//         assertEquals(0, AnimalType.BEAVER.getPrepDuration());
//         assertEquals(5, AnimalType.BEAVER.getFeedDuration());
//         assertEquals(5, AnimalType.BEAVER.getCleanDuration());

//         assertEquals(0, AnimalType.RACCOON.getFeedStartTime());
//         assertEquals(0, AnimalType.RACCOON.getPrepDuration());
//         assertEquals(5, AnimalType.RACCOON.getFeedDuration());
//         assertEquals(5, AnimalType.RACCOON.getCleanDuration());

//         assertEquals(0, AnimalType.KITS.getFeedStartTime());
//         assertEquals(0, AnimalType.KITS.getPrepDuration());
//         assertEquals(0, AnimalType.KITS.getFeedDuration());
//         assertEquals(30, AnimalType.KITS.getCleanDuration());
//     }
    
//     //homepagegui test
//     private HomePageGUI homePageGUI;
//     private JButton scheduleButton;
//     private JButton animalButton;
//     private JButton treatmentButton;
//     private JButton tasksButton;
//     private JLabel scheduleHeader;
    
//     @Before
//     public void setUpHomePageGUITest() {
//         homePageGUI = new HomePageGUI();
//         Container contentPane = homePageGUI.getContentPane();
//         Component[] components = contentPane.getComponents();

//         JPanel headerPanel = (JPanel) components[0];
//         JPanel mainBody = (JPanel) components[1];

//         Component[] headerComponents = headerPanel.getComponents();
//         scheduleButton = (JButton) headerComponents[0];
//         animalButton = (JButton) headerComponents[1];
//         treatmentButton = (JButton) headerComponents[2];
//         tasksButton = (JButton) headerComponents[3];

//         Component[] mainBodyComponents = mainBody.getComponents();
//         scheduleHeader = (JLabel) mainBodyComponents[0];
//     }
//     @Test
//     public void testHomePageGUIInitialization() {
//         HomePageGUI homePageGUI = new HomePageGUI();
//         Container contentPane = homePageGUI.getContentPane();
//         Component[] components = contentPane.getComponents();

//         // Check if the JFrame has two main components (headerPanel and mainBody)
//         assertEquals(2, components.length);

//         JPanel headerPanel = (JPanel) components[0];
//         JPanel mainBody = (JPanel) components[1];

//         // Check if the headerPanel has 4 buttons (Schedule, Animals, Treatments, Tasks)
//         Component[] headerComponents = headerPanel.getComponents();
//         assertEquals(4, headerComponents.length);

//         assertTrue(headerComponents[0] instanceof JButton);
//         assertTrue(headerComponents[1] instanceof JButton);
//         assertTrue(headerComponents[2] instanceof JButton);
//         assertTrue(headerComponents[3] instanceof JButton);

//         JButton scheduleButton = (JButton) headerComponents[0];
//         JButton animalButton = (JButton) headerComponents[1];
//         JButton treatmentButton = (JButton) headerComponents[2];
//         JButton tasksButton = (JButton) headerComponents[3];

//         // Check button text
//         assertEquals("Schedule", scheduleButton.getText());
//         assertEquals("Animals", animalButton.getText());
//         assertEquals("Treatments", treatmentButton.getText());
//         assertEquals("Tasks", tasksButton.getText());

//         // Check if the mainBody has a schedule header label
//         Component[] mainBodyComponents = mainBody.getComponents();
//         assertEquals(1, mainBodyComponents.length);
//         assertTrue(mainBodyComponents[0] instanceof JLabel);

//         JLabel scheduleHeader = (JLabel) mainBodyComponents[0];
//         assertNotNull(scheduleHeader.getText());
//     }
//     }

//     @Test
//     public void testActionListeners() {
//         // Check if the buttons have action listeners assigned
//         ActionListener[] scheduleButtonListeners = scheduleButton.getActionListeners();
//         ActionListener[] animalButtonListeners = animalButton.getActionListeners();
//         ActionListener[] treatmentButtonListeners = treatmentButton.getActionListeners();
//         ActionListener[] tasksButtonListeners = tasksButton.getActionListeners();

//         assertEquals(1, scheduleButtonListeners.length);
//         assertEquals(1, animalButtonListeners.length);
//         assertEquals(1, treatmentButtonListeners.length);
//         assertEquals(1, tasksButtonListeners.length);

//         // Check if the listeners are instances of HomePageGUI
//         assertTrue(scheduleButtonListeners[0] instanceof HomePageGUI);
//         assertTrue(animalButtonListeners[0] instanceof HomePageGUI);
//         assertTrue(treatmentButtonListeners[0] instanceof HomePageGUI);
//         assertTrue(tasksButtonListeners[0] instanceof HomePageGUI);
//     }

//     @Test
//     public void testMouseListener() {
//         // Check if the HomePageGUI has a mouse listener assigned
//         MouseListener[] mouseListeners = homePageGUI.getMouseListeners();
//         assertEquals(1, mouseListeners.length);

//         // Check if the listener is the HomePageGUI itself
//         assertTrue(mouseListeners[0] instanceof HomePageGUI);
//     }

//     @Test
//     public void testJFrameProperties() {
//         // Check JFrame properties
//         assertEquals("Wildlife Rescue", homePageGUI.getTitle());
//         assertEquals(800, homePageGUI.getWidth());
//         assertEquals(500, homePageGUI.getHeight());
//         assertEquals(JFrame.EXIT_ON_CLOSE, homePageGUI.getDefaultCloseOperation());
//     }
    
// }

/**
@author 	Mizy Bermas <a href="mailto:mizy.bermas@ucalgary.ca">mizy.bermas@ucalgary.ca</a>
@author 	Joshua Jipp <a href="mailto:joshua.jipp@ucalgary.ca">joshua.jipp@ucalgary.ca</a>
@author 	Joshua Koshy <a href="mailto:joshua.koshy@ucalgary.ca">joshua.koshy@ucalgary.ca</a>
@author 	Nicole Lazarte <a href="mailto:nicole.lazarte@ucalgary.ca">nicole.lazarte@ucalgary.ca</a>
@version    	1.0
@since  	    1.0
*/
package edu.ucalgary.oop;

/**
 * Custom Exception
 * thrown by Schedule class
 * HomePageGUI catches the exception
 */
public class ScheduleOverflowException extends Exception {
    /**
     * @param message exception message
     * 
     */
    public ScheduleOverflowException(String message) {
        super(message);
    }
}
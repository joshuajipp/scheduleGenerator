package edu.ucalgary.oop;

import java.util.ArrayList;

public class Schedule{
    private String[][] timeArray;
    private ArrayList<Treatments> treatmentArray;

    public Schedule(String[][] timeArray, ArrayList<Treatments> treatmentArray){
        this.timeArray = timeArray;
        this.treatmentArray = treatmentArray;
    }
    public ArrayList<Treatments> getTreatmentArrayList() {
            return treatmentArray;
    }
}
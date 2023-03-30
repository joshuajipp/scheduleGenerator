package edu.ucalgary.oop;

public class Tasks{
    private int taskID;
    private String description;
    private int duration;
    private int maxWindow;

    public Tasks(int id, String description, int duration, int maxWindow){
        this.taskID = id;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
    }

    public int getTaskID(){
        return taskID;
    }
    public String getDescription(){
        return description;
    }
    public int getDuration(){
        return duration;
    }
    public int maxWindow(){
        return maxWindow;
    }
}
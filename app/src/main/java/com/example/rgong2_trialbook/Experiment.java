package com.example.rgong2_trialbook;

import java.io.Serializable;
import java.util.Date;

public abstract class Experiment implements Serializable {

    protected String title;
    protected Date startDate;
    protected String description;

    public Experiment() {
        //create new, empty experiment
        title = "New experiment";
        startDate = new Date();
        description = "No description yet!";

    }

    public Experiment(String title, Date startDate, String description) {
        //initialize an experiment with existing parameters, should be used for repopulating list with data saved previously, or for debug
        this.title = title;
        this.description = description;
        this.startDate = startDate;
    }



    public abstract String toString();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //have a query method for each possible subclasses, defaulting to false, subclasses can selectively override these methods to identify themselves.
    //I don't think this is the best way to implement this, but Dr.Hindle suggested this way(unless im misinterpreting)
    public boolean isBinary() {
        return false;
    }
}

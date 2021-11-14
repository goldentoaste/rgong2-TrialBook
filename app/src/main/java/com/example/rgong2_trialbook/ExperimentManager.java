package com.example.rgong2_trialbook;

import java.io.Serializable;
import java.util.ArrayList;

public class ExperimentManager   {

    private final ArrayList<Experiment> experiments;

    public ExperimentManager() {
        experiments = new ArrayList<>();
    }

    public ExperimentManager(ArrayList<Experiment> experiments) {
        this.experiments = experiments;
    }

    //return experiement removed on success, null otherwise
    public boolean removeExperiment(Experiment exp) {
        return experiments.remove(exp);
    }

    public Experiment removeByIndex(int index) {
        return experiments.remove(index);
    }

    public int getSize() {
        return experiments.size();
    }

    public Experiment getExperiment(int index) {

        if (index >= 0 && index < experiments.size()) {
            return experiments.get(index);
        }
        return null;

    }

    // index may be -1 to append to end
    public void addExperiment(int index, Experiment exp) {
        if (index == -1) {
            experiments.add(exp);
        } else {
            experiments.add(index, exp);
        }
    }

    public void updateExperiment(int index, Experiment exp) {
        experiments.set(index, exp);
    }
}

package com.example.rgong2_trialbook;

import java.util.Date;

public class BinaryExperiment extends Experiment {

    protected int successCount;
    protected int failCount;

    public BinaryExperiment() {
        super();
        successCount = 0;
        failCount = 0;
    }

    public BinaryExperiment(int successCount, int failCount) {
        super();
        this.successCount = successCount;
        this.failCount = failCount;

    }

    public BinaryExperiment(String title, Date startDate, String description, int successCount, int failCount) {
        super(title, startDate, description);
        this.successCount = successCount;
        this.failCount = failCount;
    }


    @Override
    public boolean isBinary() {
        return true;
    }

    //use increment and decrement to change the values by 1, with built in 0 checking
    public void incrementSuccess() {
        successCount += 1;
    }

    public void incrementFailure() {
        failCount += 1;
    }

    public void decrementSuccess() {
        if (successCount > 0) {
            successCount -= 1;
        }

    }

    public void decrementFailure() {
        if (failCount > 0) {
            failCount -= 1;
        }
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    // division by 0 returns NaN, should be handled outside of this method
    public float getPercentageSuccess() {
        return (1.0f * successCount) / (successCount + failCount);
    }

    public int getTotal() {
        return successCount + failCount;
    }

    //for debugging purposes
    public String toString() {
        return title + " " + String.format(" %d Successes, %d Failures", successCount, failCount) + " at " + System.identityHashCode(this);
    }
}

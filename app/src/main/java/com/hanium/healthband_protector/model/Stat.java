package com.hanium.healthband_protector.model;

public class Stat {
    private float mean;
    private float max;
    private float min;

    public Stat(int mean, int max, int min) {
        this.mean = mean;
        this.max = max;
        this.min = min;
    }

    public float getMean() {
        return mean;
    }

    public void setMean(int mean) {
        this.mean = mean;
    }

    public float getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}

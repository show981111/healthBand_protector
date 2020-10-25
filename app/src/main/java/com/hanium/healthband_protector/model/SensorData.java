package com.hanium.healthband_protector.model;

public class SensorData {
    private String temperature;
    private String humidity;
    private String heartRate;
    private String meter;

    public SensorData(String temperature, String humidity, String heartRate, String meter) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.heartRate = heartRate;
        this.meter = meter;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getMeter() {
        return meter;
    }


}
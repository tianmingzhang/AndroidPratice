package com.example.myapplication.logic.model;

public class RealtimeResponse {
    private String status;
    private double temperature;
    private String skycon;
    private AirQuality airQuality;
    private RealtimeLifeIndex lifeIndex;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getSkycon() {
        return skycon;
    }

    public void setSkycon(String skycon) {
        this.skycon = skycon;
    }

    public AirQuality getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQuality airQuality) {
        this.airQuality = airQuality;
    }

    public RealtimeLifeIndex getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(RealtimeLifeIndex lifeIndex) {
        this.lifeIndex = lifeIndex;
    }
}

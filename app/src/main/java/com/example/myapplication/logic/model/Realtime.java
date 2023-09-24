package com.example.myapplication.logic.model;

import com.google.gson.annotations.SerializedName;

public class Realtime {
    private double temperature;
    private String skycon;

    @SerializedName("air_quality")
    private AirQuality airQuality;

    @SerializedName("life_index")
    private LifeIndex lifeIndex;

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

    public LifeIndex getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(LifeIndex lifeIndex) {
        this.lifeIndex = lifeIndex;
    }
}

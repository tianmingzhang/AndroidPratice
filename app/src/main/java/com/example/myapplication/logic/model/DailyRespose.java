package com.example.myapplication.logic.model;

import java.util.ArrayList;

public class DailyRespose {
    private String status;
    private ArrayList<Temperature> temperature;
    private DailyLifeIndex dailyLifeIndex;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Temperature> getTemperature() {
        return temperature;
    }

    public void setTemperature(ArrayList<Temperature> temperature) {
        this.temperature = temperature;
    }

    public DailyLifeIndex getDailyLifeIndex() {
        return dailyLifeIndex;
    }

    public void setDailyLifeIndex(DailyLifeIndex dailyLifeIndex) {
        this.dailyLifeIndex = dailyLifeIndex;
    }
}

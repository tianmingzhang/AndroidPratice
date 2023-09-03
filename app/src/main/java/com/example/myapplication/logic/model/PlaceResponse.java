package com.example.myapplication.logic.model;

import java.util.ArrayList;
import java.util.List;

public class PlaceResponse {
    private String status;
    private ArrayList<Place> places;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}

package com.example.myapplication.logic.model;

public class Place {
    private String id;
    private String name;
    private String formatted_address;
    private String place_id;
    private LocationPlace  location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public LocationPlace getLocation() {
        return location;
    }

    public void setLocation(LocationPlace location) {
        this.location = location;
    }
}

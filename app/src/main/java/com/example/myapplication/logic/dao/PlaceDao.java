package com.example.myapplication.logic.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.MyApplication;
import com.example.myapplication.logic.model.Place;
import com.google.gson.Gson;

public class PlaceDao {
    private SharedPreferences sharedPreferences() {
        return MyApplication.getContext().getSharedPreferences("weather_sp", Context.MODE_PRIVATE);
    }

    public void SavePlace(Place place,String keyWord) {
        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.putString(keyWord,new Gson().toJson(place)).apply();
    }

    public Place GetPlace(String keyWord) {
        return new Gson().fromJson(sharedPreferences().getString(keyWord,""),Place.class) ;
    }

    public boolean existedPlace(String keyWord) {
        return sharedPreferences().contains(keyWord);
    }
}

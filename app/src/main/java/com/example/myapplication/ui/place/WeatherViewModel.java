package com.example.myapplication.ui.place;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.logic.model.LocationPlace;
import com.example.myapplication.logic.model.Place;
import com.example.myapplication.logic.model.Weather;
import com.example.myapplication.logic.network.WeatherSearchRepository;

import java.util.ArrayList;

import retrofit2.http.Path;

public class WeatherViewModel extends ViewModel {
    MutableLiveData<LocationPlace> searchWeatherData = new MutableLiveData<LocationPlace>();

    public final LiveData<Weather> weather =
            Transformations.switchMap(searchWeatherData, (locationPlace) -> {
                return WeatherSearchRepository.refreshWeather(locationPlace.getLat(),locationPlace.getLng());
            });

    public void refreshWeath(double lat, double lng) {
        LocationPlace locationPlace = new LocationPlace();
        locationPlace.setLat(lat);
        locationPlace.setLng(lng);
        searchWeatherData.setValue(locationPlace);
    }
}

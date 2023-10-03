package com.example.myapplication.ui.place;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.logic.model.Place;
import com.example.myapplication.logic.network.WeatherSearchRepository;

import java.util.ArrayList;

public class PlaceViewModel extends ViewModel {

    ArrayList<Place> placeArrayList = new ArrayList<>();
     MutableLiveData<String> searchLiveData = new MutableLiveData<String>();
    WeatherSearchRepository weatherSearchRepository = new WeatherSearchRepository();

    public final LiveData<ArrayList<Place>> postalCode =
            Transformations.switchMap(searchLiveData, (address) -> {
                return WeatherSearchRepository.searchPlaces(address);
            });

     void searchPlaces(String query) {
        searchLiveData.setValue(query);
    }

    public void SavePlace(Place place,String keyWord) {
        weatherSearchRepository.SavePlace(place,keyWord);
    }

    public Place GetPlace(String keyWord) {
        return weatherSearchRepository.GetPlace(keyWord);
    }

    public boolean existedPlace(String keyWord) {
        return weatherSearchRepository.existedPlace(keyWord);
    }
}

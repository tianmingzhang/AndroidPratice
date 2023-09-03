package com.example.myapplication.logic.network;

public class ApiUtils {
    public static final String BASE_URL = "https://api.caiyunapp.com/";

    public static PlaceService getPlaceService() {
        return RetrofitClient.getClient(BASE_URL).create(PlaceService.class);
    }

}

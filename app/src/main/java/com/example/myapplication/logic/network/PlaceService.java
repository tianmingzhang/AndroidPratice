package com.example.myapplication.logic.network;

import com.example.myapplication.logic.model.DailyRespose;
import com.example.myapplication.logic.model.PlaceResponse;
import com.example.myapplication.logic.model.RealtimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface  PlaceService {
    @GET("/v2/place?lang=zh_CN&token=${MyApplication.token}")
    Call<PlaceResponse> getSearchPlaces(@Query("query") String queryA);

    @GET("/v2.6/${MyApplication.token}/{lat},{lng}/realtime")
    Call<RealtimeResponse> getRealtimeWeather(@Path("lat") String lat, @Path("lng") String lng);

    @GET("/v2.6/${MyApplication.token}/{lat},{lng}/daily?dailysteps=5")
    Call<DailyRespose> getDailyWeather(@Path("lat") String lat, @Path("lng") String lng);
}

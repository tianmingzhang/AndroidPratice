package com.example.myapplication.logic.network;

import com.example.myapplication.logic.model.DailyRespose;
import com.example.myapplication.logic.model.PlaceResponse;
import com.example.myapplication.logic.model.RealtimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import io.reactivex.Observable;


public interface  PlaceService {
    @GET("v2/place?lang=zh_CN&token=${MyApplication.token}")
    Call<PlaceResponse> getSearchPlaces(@Query("query") String queryA);

    @GET("v2.6/{tokent}/{lng},{lat}/realtime")
    Observable<RealtimeResponse> getRealtimeWeather(@Path("tokent") String tokent,@Path("lat") double lat, @Path("lng") double lng);

    @GET("v2.6/{tokent}/{lng},{lat}/daily?dailysteps=5")
    Observable<DailyRespose> getDailyWeather(@Path("tokent") String tokent,@Path("lat") double lat, @Path("lng") double lng);
}

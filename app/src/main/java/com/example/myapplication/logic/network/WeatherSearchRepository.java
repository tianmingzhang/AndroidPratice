package com.example.myapplication.logic.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.logic.model.DailyRespose;
import com.example.myapplication.logic.model.Place;
import com.example.myapplication.logic.model.PlaceResponse;
import com.example.myapplication.logic.model.Weather;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherSearchRepository {
    ExecutorService executorService = Executors.newCachedThreadPool();

    public static MutableLiveData<ArrayList<Place>> searchPlaces(String queryA) {
        //构造需要暴露的数据结构
        final MutableLiveData<ArrayList<Place>> liveDataPlace = new MutableLiveData<>();

        
        ApiUtils.getPlaceService().getSearchPlaces(queryA).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {

                if (response.body().getStatus().equals("ok")) {
                    liveDataPlace.setValue(response.body().getPlaces());
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    liveDataPlace.setValue(null);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
                t.printStackTrace();
                liveDataPlace.setValue(null);
            }
        });
        return liveDataPlace;
    }

    public static MutableLiveData<Weather> searchPlaces(String lat,String lng) {
        //构造需要暴露的数据结构
        final MutableLiveData<Weather> liveDataWeather = new MutableLiveData<>();



        Observable dailyWeather = Observable.create((ObservableOnSubscribe<DailyRespose>) emitter ->
                ApiUtils.getPlaceService()
                .getDailyWeather(lat,lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyRespose>() {


                    public void onSubscribe(Disposable d) {

                    }


                    public void onNext(DailyRespose httpResult) {
                        emitter.onNext(httpResult.getStatus());
                        emitter.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        emitter.onNext(null);
                        emitter.onComplete();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        return liveDataWeather;
    }
}


package com.example.myapplication.logic.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.logic.model.DailyRespose;
import com.example.myapplication.logic.model.Place;
import com.example.myapplication.logic.model.PlaceResponse;
import com.example.myapplication.logic.model.RealtimeResponse;
import com.example.myapplication.logic.model.Weather;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
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

    public static MutableLiveData<Weather> refreshWeather(double lat,double lng) {
        //构造需要暴露的数据结构
        final MutableLiveData<Weather> liveDataWeather = new MutableLiveData<>();

        Observable dailyWeather = Observable.create((ObservableOnSubscribe<DailyRespose>) emitter ->
                ApiUtils.getPlaceService()
                .getDailyWeather(lat,lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, DailyRespose>() {
                    @Override
                    public DailyRespose apply(@NonNull Throwable throwable) {
                        DailyRespose dailyRespose = new DailyRespose();
                        dailyRespose.setStatus("NG");
                        //拦截到错误之后，返回一个结果发射，然后就正常结束了。
                        return dailyRespose;
                    }
                })
                .subscribe(new Observer<DailyRespose>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DailyRespose httpResult) {
                        emitter.onNext(httpResult);
                        emitter.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        emitter.onNext(null);
                        emitter.onComplete();
                    }

                    public void onComplete() {

                    }
                }));
        Observable realtimeWeather = Observable.create((ObservableOnSubscribe<RealtimeResponse>) emitter ->
                ApiUtils.getPlaceService()
                        .getRealtimeWeather(lat,lng)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn(new Function<Throwable, RealtimeResponse>() {
                            @Override
                            public RealtimeResponse apply(@NonNull Throwable throwable) {
                                RealtimeResponse realtimeResponse = new RealtimeResponse();
                                realtimeResponse.setStatus("NG");
                                //拦截到错误之后，返回一个结果发射，然后就正常结束了。
                                return realtimeResponse;
                            }
                        })
                        .subscribe(new Observer<RealtimeResponse>() {

                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(RealtimeResponse httpResult) {
                                emitter.onNext(httpResult);
                                emitter.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                emitter.onNext(null);
                                emitter.onComplete();
                            }

                            public void onComplete() {

                            }
                        }));

        Observable.zip(dailyWeather, realtimeWeather, new BiFunction<Object, Object, Object>() {
            @Override
            public Object apply(Object dailyWeather, Object realtimeWeather) throws Exception {
                DailyRespose t1 = (DailyRespose) dailyWeather;//o1得到的结果
                RealtimeResponse t2 = (RealtimeResponse) realtimeWeather;//o2得到的结果
                Weather f =null;//最终结果合并
                if (t1.getStatus().toUpperCase().equals("OK") && t2.getStatus().toUpperCase().equals("OK")) {
                    f=new Weather();//最终结果合并
                    f.setDailyRespose(t1);
                    f.setRealtimeResponse(t2);
                }
                return f;
            }
        }).subscribeOn(Schedulers.io()).subscribe(o -> {
            Weather f=(Weather)o;//获取最终结果
            //处理数据
            liveDataWeather.setValue(f);
        });
        return liveDataWeather;
    }
}


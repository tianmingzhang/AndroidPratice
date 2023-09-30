package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.view.LayoutInflater;

import com.example.myapplication.logic.model.Temperature;
import com.example.myapplication.logic.model.Weather;
import com.example.myapplication.ui.place.PlaceViewModel;
import com.example.myapplication.ui.place.WeatherViewModel;

import java.util.ArrayList;
import java.util.Optional;

public class WeatherActivity extends AppCompatActivity {

    String place_name;
    WeatherViewModel weatherViewModel;

    TextView placeName,currentTemp,currentSky,currentAQI,dateInfo,temperatureInfo,ultravioletText;
    RelativeLayout nowLayout;
    LinearLayout forecastLayout;
    ScrollView weatherLayout;

    SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_weather);

        placeName = findViewById(R.id.placeName);
        currentTemp = findViewById(R.id.currentTemp);
        currentSky = findViewById(R.id.currentSky);
        currentAQI = findViewById(R.id.currentAQI);
        nowLayout = (RelativeLayout)findViewById(R.id.nowLayout);
        forecastLayout = (LinearLayout)findViewById(R.id.forecastLayout);

        ultravioletText = findViewById(R.id.ultravioletText);
        weatherLayout = (ScrollView)findViewById(R.id.weatherLayout);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        double lng = getIntent().getDoubleExtra("location_lng",120.585294);
        double lat = getIntent().getDoubleExtra("location_lat",31.299758);
        place_name = Optional.ofNullable(getIntent().getStringExtra("place_name")).orElse("苏州");

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        weatherViewModel.weather.observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (weather != null){
                    showWeatherInfo(weather);
                }else {
                    Toast toast= Toast.makeText(MyApplication.getContext(), "未能查询到结果", Toast.LENGTH_SHORT);
                    toast.show();
                }
                swipeRefresh.setRefreshing(false);
            }
        });
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        refreshWeather(lat, lng);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWeather(lat, lng);
            }
        });
    }

    private void refreshWeather(double lat, double lng){
        weatherViewModel.refreshWeath(lat,lng);
        swipeRefresh.setRefreshing(true);
    }

    private void showWeatherInfo(Weather weather){
        placeName.setText(place_name);
        currentTemp.setText(weather.getRealtimeResponse().getResult().getRealtime().getTemperature()+"℃");
        currentSky.setText(weather.getRealtimeResponse().getResult().getRealtime().getSkycon());
        currentAQI.setText(weather.getRealtimeResponse().getResult().getRealtime().getAirQuality().getDescription().getChn());
        ultravioletText.setText(weather.getRealtimeResponse().getResult().getRealtime().getLifeIndex().getUltraviolet().getDesc());
        nowLayout.setBackgroundResource(R.drawable.bg_clear_day);
        forecastLayout.removeAllViews();

        ArrayList<Temperature> temperatureList = weather.getDailyRespose().getResult().getDaily().getTemperature();
        for(int i =0;i<temperatureList.size();i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout, false);
            dateInfo = view.findViewById(R.id.dateInfo);
            dateInfo.setText(temperatureList.get(i).getDate());
            temperatureInfo = view.findViewById(R.id.temperatureInfo);
            temperatureInfo.setText(String.valueOf(temperatureList.get(i).getAvg()));
            forecastLayout.addView(view);
        }

        weatherLayout.setVisibility(View.VISIBLE);
    }

}
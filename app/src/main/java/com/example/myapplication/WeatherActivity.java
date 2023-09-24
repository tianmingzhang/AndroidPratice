package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;

import com.example.myapplication.logic.model.Weather;
import com.example.myapplication.ui.place.PlaceViewModel;
import com.example.myapplication.ui.place.WeatherViewModel;

import java.util.Optional;

public class WeatherActivity extends AppCompatActivity {

    String place_name;
    WeatherViewModel weatherViewModel;

    TextView placeName,currentTemp,currentSky,currentAQI,dateInfo,temperatureInfo,ultravioletText;
    RelativeLayout nowLayout;
    LinearLayout forecastLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        placeName = findViewById(R.id.placeName);
        currentTemp = findViewById(R.id.currentTemp);
        currentSky = findViewById(R.id.currentSky);
        currentAQI = findViewById(R.id.currentAQI);
        nowLayout = (RelativeLayout)findViewById(R.id.nowLayout);
        forecastLayout = (LinearLayout)findViewById(R.id.forecastLayout);
        dateInfo = findViewById(R.id.dateInfo);
        temperatureInfo = findViewById(R.id.temperatureInfo);
        ultravioletText = findViewById(R.id.ultravioletText);


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
            }
        });
        weatherViewModel.refreshWeath(lat,lng);
    }

    private void showWeatherInfo(Weather weather){

    }

}
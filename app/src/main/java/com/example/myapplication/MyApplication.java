package com.example.myapplication;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;

    private static String token = new String("SFtTHjnIUjRmg2fN");

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}

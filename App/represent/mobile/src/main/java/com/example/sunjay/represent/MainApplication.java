package com.example.sunjay.represent;

import android.app.Application;

import com.example.sunjay.represent.services.API;

public class MainApplication extends Application {
    private static MainApplication applicationSingleton;

    public MainApplication getInstance() {
        return applicationSingleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationSingleton = this;
        API.initializeAPI(getInstance());
    }
}

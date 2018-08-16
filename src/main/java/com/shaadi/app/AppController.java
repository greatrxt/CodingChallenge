package com.shaadi.app;

import android.app.Application;

public class AppController extends Application {

    public static final String TAG = "Shaadi";
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}
package com.example.elewa.movie.VolleySingleTon;

import android.app.Application;
import android.content.Context;

/**
 * Created by elewa on 4/28/16.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
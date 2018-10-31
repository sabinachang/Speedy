package com.enhan.sabina.speedy;

import android.app.Application;
import android.content.Context;

public class SpeedyApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        SpeedyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return SpeedyApplication.context;
    }
}

package com.hvc.rockmusic;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;



public class FireApplication extends Application {
    public static Context appContext;

    public static Context getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

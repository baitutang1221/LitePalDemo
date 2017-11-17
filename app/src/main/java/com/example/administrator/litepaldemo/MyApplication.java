package com.example.administrator.litepaldemo;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;


public class MyApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePalApplication.initialize(this);//初始化LitePal数据库
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}


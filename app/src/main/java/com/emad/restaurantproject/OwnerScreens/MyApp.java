package com.emad.restaurantproject.OwnerScreens;

import android.app.Application;
import com.yariksoffice.lingver.Lingver;
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Lingver.init(this);
    }
}
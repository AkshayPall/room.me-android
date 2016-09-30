package me.akshaypall.roomme;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;


/**
 * Created by Akshay on 2016-09-30.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this); //to initialize the Joda Time for Android Library
    }
}

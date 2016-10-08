package me.akshaypall.roomme;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;


/**
 * Created by Akshay on 2016-09-30.
 */

public class App extends Application {
    private static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        JodaTimeAndroid.init(this); //to initialize the Joda Time for Android Library
    }

    public static Context getAppContext(){
        return AppContext;
    };
}

package com.twokwy.tetris;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by anita on 13/03/2016.
 */
public class TetrisApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}

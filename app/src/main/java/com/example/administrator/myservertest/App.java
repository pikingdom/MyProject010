package com.example.administrator.myservertest;

import android.app.Application;

/**
 * Created by Administrator on 2018/8/14.
 */

public class App extends Application {

    public static Application app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Application getContext(){
        return app;
    }
}

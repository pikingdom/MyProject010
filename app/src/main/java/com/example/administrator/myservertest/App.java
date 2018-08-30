package com.example.administrator.myservertest;

import android.app.Application;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.util.MyOKhttpHeler;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/8/14.
 */

public class App extends Application {

    private static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

//        //自定义OkHttp
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .build();
//        mMyOkHttp = new MyOkHttp(okHttpClient);
        MyOkHttp.getInstance().setApplicationConext(getApplicationContext());

    }

    public static synchronized App getContext(){
        return app;
    }

//    public MyOkHttp getMyOkHttp() {
//        return mMyOkHttp;
//    }
}

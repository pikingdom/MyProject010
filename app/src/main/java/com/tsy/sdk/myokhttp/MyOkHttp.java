package com.tsy.sdk.myokhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.tsy.sdk.myokhttp.builder.GetBuilder;
import com.tsy.sdk.myokhttp.builder.PostBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * MyOkhttp
 * Created by tsy on 16/9/14.
 */
public class MyOkHttp {
    private static OkHttpClient mOkHttpClient;
    public static Handler mHandler = new Handler(Looper.getMainLooper());

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    private static MyOkHttp instance;
    private Context applicationConext;

    public void setApplicationConext(Context conext){
        if(conext != null){
            applicationConext = conext.getApplicationContext();
        }
    }

    public Context getApplicationConext(){
        return applicationConext;
    }

    public static MyOkHttp getInstance() {
        if (instance == null) {
            synchronized (MyOkHttp.class) {
                if (instance == null) {
                    instance = new MyOkHttp();
                }
            }
        }
        return instance;
    }

    /**
     * construct
     */
    private MyOkHttp() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
    }

    private void check(){
        if(applicationConext == null){
            throw new IllegalArgumentException("applicationConext is null");
        }
    }

    public GetBuilder get() {
        check();
        return new GetBuilder(this,false);
    }
    public GetBuilder getH() {
        check();
        return new GetBuilder(this,true);
    }

    public PostBuilder post() {
        check();
        PostBuilder postBuilder = new PostBuilder(this,false);
        return postBuilder;
    }

    public PostBuilder postH() {
        check();
        PostBuilder postBuilder = new PostBuilder(this,true);
        return postBuilder;
    }

    /**
     * do cacel by tag
     *
     * @param tag tag
     */
    public void cancel(Object tag) {
        check();
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}

package com.tsy.sdk.myokhttp.response;

import android.text.TextUtils;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.util.LogUtils;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * json类型的回调接口
 * Created by tsy on 16/8/15.
 */
public abstract class JsonResponseHandler implements IResponseHandler {

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";

        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("onResponse fail read response body");

            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail read response body");
                }
            });
            return;
        } finally {
            responseBody.close();
        }

        final String finalResponseBodyStr = responseBodyStr;

        try {
            if(TextUtils.isEmpty(finalResponseBodyStr)){
                MyOkHttp.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFailure(response.code(), finalResponseBodyStr);
                    }
                });
            } else {
                MyOkHttp.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(response.code(), finalResponseBodyStr);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("onResponse fail parse jsonobject, body=" + finalResponseBodyStr);
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), finalResponseBodyStr);
                }
            });
        }
    }

    public void onSuccess(int statusCode, String response) {
        LogUtils.w("onSuccess(int statusCode, JSONObject response) was not overriden, but callback was received");
    }

}
package com.tsy.sdk.myokhttp.builder;

import android.content.Context;

import com.tsy.sdk.myokhttp.Common;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.callback.MyCallback;
import com.tsy.sdk.myokhttp.response.IResponseHandler;
import com.tsy.sdk.myokhttp.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * post builder
 * Created by tsy on 16/9/18.
 */
public class PostBuilder extends OkHttpRequestBuilderHasParam<PostBuilder> {

    private String mJsonParams = "";
    private boolean addCommonHead = false;

    public PostBuilder(MyOkHttp myOkHttp,boolean addHead) {
        super(myOkHttp);
        this.addCommonHead = addHead;
    }

    /**
     * json格式参数
     * @param json
     * @return
     */
    public PostBuilder jsonParams(String json) {
        this.mJsonParams = json;
        return this;
    }

    @Override
    public void enqueue(IResponseHandler responseHandler) {
        try {
            Request request = getRequest();

            mMyOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new MyCallback(responseHandler));
        } catch (Exception e) {
            LogUtils.e("Post enqueue error:" + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }

    @Override
    public Response execute() {
        try {
            Request request = getRequest();
            return mMyOkHttp.getOkHttpClient().newCall(request).execute();
        } catch (Exception e) {
            LogUtils.e("Post enqueue error:" + e.getMessage());
            return null;
        }
    }

    public String executeStr() {
        Response response = execute();
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Request getRequest() throws JSONException {
        if(mUrl == null || mUrl.length() == 0) {
            throw new IllegalArgumentException("url can not be null !");
        }

        Request.Builder builder = new Request.Builder().url(mUrl);
        appendHeaders(builder, mHeaders);

        if (mTag != null) {
            builder.tag(mTag);
        }

        if(mJsonParams.length() > 0) {      //上传json格式参数
            RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), mJsonParams);
            builder.post(body);
        } else {        //普通kv参数
//                FormBody.Builder encodingBuilder = new FormBody.Builder();
//                appendParams(encodingBuilder, mParams);
//                builder.post(encodingBuilder.build());
            if(mParams == null){
                RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), "");
                builder.post(body);
            } else {
                JSONObject jsonBody = new JSONObject();
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    jsonBody.put(entry.getKey(), entry.getValue());
                }
                mJsonParams = jsonBody.toString();
                RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), mJsonParams);
                builder.post(body);
            }
//                if(TextUtils.isEmpty(mJsonParams)){
//                    RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), "");
//                    builder.post(body);
//                } else {
//                    JSONObject jsonBody = new JSONObject();
//                    for (Map.Entry<String, String> entry : mParams.entrySet()) {
//                        jsonBody.put(entry.getKey(), entry.getValue());
//                    }
//                    mJsonParams = jsonBody.toString();
//                }
        }
        if(addCommonHead){
            Common.addCommonHeader(builder,mJsonParams);
        }
        return builder.build();
    }




    //append params to form builder
    private void appendParams(FormBody.Builder builder, Map<String, String> params) {

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }
}

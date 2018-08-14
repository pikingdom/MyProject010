package com.tsy.sdk.myokhttp.builder;

import com.example.administrator.myservertest.DigestUtil;
import com.example.administrator.myservertest.okhttp.AppHttpRequest;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.callback.MyCallback;
import com.tsy.sdk.myokhttp.response.IResponseHandler;
import com.tsy.sdk.myokhttp.util.LogUtils;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * post builder
 * Created by tsy on 16/9/18.
 */
public class PostBuilder extends OkHttpRequestBuilderHasParam<PostBuilder> {

    private String mJsonParams = "";

    public PostBuilder(MyOkHttp myOkHttp) {
        super(myOkHttp);
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
                JSONObject jsonBody = new JSONObject();
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    jsonBody.put(entry.getKey(), entry.getValue());
                }
                mJsonParams = jsonBody.toString();
                RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"), mJsonParams);
                builder.post(body);
            }
            addCommonHeader(builder);
            Request request = builder.build();

            mMyOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new MyCallback(responseHandler));
        } catch (Exception e) {
            LogUtils.e("Post enqueue error:" + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }

    private void addCommonHeader(Request.Builder builder){
        String sign = DigestUtil.md5Hex(AppHttpRequest.getPid() + AppHttpRequest.getMt() +
                AppHttpRequest.getDivideVersion() + AppHttpRequest.getSupPhone() + AppHttpRequest.getSupFirm() +
                AppHttpRequest.getImei() + AppHttpRequest.getImsi() + "" + AppHttpRequest.getCuid() +
                AppHttpRequest.ProtocolVersion + mJsonParams + AppHttpRequest.REQUEST_KEY);

        builder.addHeader("PID", AppHttpRequest.getPid() + "")
                .addHeader("MT", AppHttpRequest.getMt() + "")
                .addHeader("DivideVersion", AppHttpRequest.getDivideVersion())
                .addHeader("SupPhone", AppHttpRequest.getSupPhone())
                .addHeader("SupFirm", AppHttpRequest.getSupFirm())
                .addHeader("IMEI", AppHttpRequest.getImei())
                .addHeader("IMSI", AppHttpRequest.getImsi())
                .addHeader("SessionId", "")
                .addHeader("CUID", AppHttpRequest.getCuid())
                .addHeader("ProtocolVersion", AppHttpRequest.ProtocolVersion)
                .addHeader("Sign", sign);
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

package com.example.administrator.myservertest.okhttp;

import android.os.Build;

import com.example.administrator.myservertest.App;
import com.example.administrator.myservertest.DigestUtil;
import com.example.administrator.myservertest.NetOptApiHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AppHttpRequest {

    public static final String REQUEST_KEY = "27B1F81F-1DD8-4F98-8D4B-6992828FB6E2";
    public static final String VERSION = "3.0";
    public static final String ProtocolVersion = NetOptApiHelper.utf8URLencode(VERSION);
    public static String PID = "6";
    public static final String MT = "4";
    public static String DivideVersion;
    public static String SupPhone;
    public static String SupFirm;
    public static String IMEI;
    public static String IMSI;
    public static String CUID;

    private String sessionID;
    private String sign;
    private String url;
    private String body;


    public AppHttpRequest(Builder builder) {
        sessionID = builder.sessionID;
        sign = builder.sign;
        url = builder.url;
        body = builder.body;
    }

    public static Builder newBuilder() {
        Builder builder = new Builder();
        builder.prepare();
        return builder;
    }

    public Response execute() {
        try {
            Call call = getCall();
            return call.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public void equeue(Callback callback) {
        try {
            Call call = getCall();
            call.enqueue(callback);
            call.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Call getCall() {
        RequestBody reqBody = RequestBody.create(MediaType.parse("text/json"), body);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("PID", getPid() + "")
                .addHeader("MT", getMt() + "")
                .addHeader("DivideVersion", getDivideVersion())
                .addHeader("SupPhone", getSupPhone())
                .addHeader("SupFirm", getSupFirm())
                .addHeader("IMEI", getImei())
                .addHeader("IMSI", getImsi())
                .addHeader("SessionId", sessionID)
                .addHeader("CUID", getCuid())
                .addHeader("ProtocolVersion", ProtocolVersion)
                .addHeader("Sign", sign)
                .post(reqBody)
                .build();

        OkHttpClient client = new OkHttpClient();

        Call call = client.newCall(request);
        return call;
    }

    public static final class Builder {
        private String sessionID="";
        private String sign;
        private String url;
        private String body = "";

        private HashMap<String, Object> bodyParams = new HashMap<String, Object>();

        public Builder() {

        }

        private void prepare() {

        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder addBodyParameter(String key, Object val) {
            bodyParams.put(key, val);
            return this;
        }

        public AppHttpRequest build() {
            assemble();
            return new AppHttpRequest(this);
        }

        private void assemble() {
            if (!bodyParams.isEmpty()) {
                try {
                    JSONObject jsonBody = new JSONObject();
                    for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
                        jsonBody.put(entry.getKey(), entry.getValue());
                    }
                    body = jsonBody.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            sign = DigestUtil.md5Hex(getPid() + getMt() + getDivideVersion() + getSupPhone() + getSupFirm() + getImei() + getImsi() + sessionID + getCuid() + ProtocolVersion + body + REQUEST_KEY);
        }

    }

    public static String getPid(){
        return PID;
    }

    public static String getMt(){
        return MT;
    }

    public static String getDivideVersion(){
        if (null == DivideVersion)
            DivideVersion = NetOptApiHelper.utf8URLencode("9.5.1");
        else {
            NetOptApiHelper.utf8URLencode(NetOptApiHelper.getDivideVersion(App.getContext()));
        }
        return  DivideVersion;
    }

    public static String getSupPhone(){
        if (null == SupPhone)
            SupPhone = NetOptApiHelper.utf8URLencode(NetOptApiHelper.replaceIllegalCharacter(Build.MODEL, "_"));
        return SupPhone;
    }

    public static String getImei(){
        if (null == IMEI)
            IMEI = NetOptApiHelper.utf8URLencode(NetOptApiHelper.getIMEI(App.getContext()));
        return IMEI;
    }

    public static String getImsi(){
        if (null == IMSI)
            IMSI = NetOptApiHelper.utf8URLencode(NetOptApiHelper.getIMSI(App.getContext()));
        return IMSI;
    }

    public static String getCuid(){
        if (null == CUID)
            CUID = NetOptApiHelper.getCUID(App.getContext());
        return CUID;
    }

    public static String getSupFirm(){
        if (null == SupFirm)
            SupFirm = NetOptApiHelper.utf8URLencode(Build.VERSION.RELEASE);
        return SupFirm;
    }

}

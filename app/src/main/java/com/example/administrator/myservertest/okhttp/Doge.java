package com.example.administrator.myservertest.okhttp;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.nd.analytics.NdAnalytics;
import com.nd.hilauncherdev.core.TelephoneUtil;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Description: </br>
 * Author: cxy
 * Date: 2017/3/3.
 */

public class Doge {

    private static final String TAG = "Doge";

    private String divideVersion;
    private String supPhone;
    private String supFirm;
    private String imei;
    private String imsi;
    private String cuid;
    private String sessionID;
    private String pid;
    private String mt;
    private String protocolVersion;
    private String sign;
    private String requestKey;
    private String url;
    private String body;

    private String channelID;
    private String language = Locale.getDefault().toString();

    //是否是海外产品
    private boolean isForeign = true;

    public Doge(Builder builder) {
        divideVersion = builder.divideVersion;
        supFirm = builder.supFirm;
        supPhone = builder.supPhone;
        imei = builder.imei;
        imsi = builder.imsi;
        cuid = builder.cuid;
        sessionID = builder.sessionID;
        pid = builder.pid;
        mt = builder.mt;
        protocolVersion = builder.protocolVersion;
        sign = builder.sign;
        requestKey = builder.requestKey;
        url = builder.url;
        body = builder.body;
        channelID = builder.channelID;
        language = builder.language;
        isForeign = builder.isForeign;
    }

    public String getMt() {
        return mt;
    }

    public String getImsi() {
        return imsi;
    }

    public String getImei() {
        return imei;
    }

    public String getDivideVersion() {
        return divideVersion;
    }

    public String getCuid() {
        return cuid;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public String getRequestKey() {
        return requestKey;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getSupFirm() {
        return supFirm;
    }

    public String getSign() {
        return sign;
    }

    public String getSupPhone() {
        return supPhone;
    }

    public String getPid() {
        return pid;
    }

    public static Builder prepare(Context ctx) {
        Builder builder = new Builder();
        builder.prepare(ctx);
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
        Request request;
        if (isForeign) {
            request = new Request.Builder()
                    .url(url)
                    .addHeader("PID", pid + "")
                    .addHeader("MT", mt + "")
                    .addHeader("DivideVersion", divideVersion)
                    .addHeader("SupPhone", supPhone)
                    .addHeader("SupFirm", supFirm)
                    .addHeader("IMEI", imei)
                    .addHeader("SessionId", sessionID)
                    .addHeader("CUID", cuid)
                    .addHeader("ProtocolVersion", protocolVersion)
                    .addHeader("Sign", sign)
                    .addHeader("ChannelID", channelID)
                    .addHeader("Language", language)
                    .addHeader("HeaderVersion", "2.0")
                    .post(reqBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .addHeader("PID", pid + "")
                    .addHeader("MT", mt + "")
                    .addHeader("DivideVersion", divideVersion)
                    .addHeader("SupPhone", supPhone)
                    .addHeader("SupFirm", supFirm)
                    .addHeader("IMEI", imei)
                    .addHeader("IMSI", imsi)
                    .addHeader("SessionId", sessionID)
                    .addHeader("CUID", cuid)
                    .addHeader("ProtocolVersion", protocolVersion)
                    .addHeader("Sign", sign)
                    .post(reqBody)
                    .build();
        }

        OkHttpClient client = new OkHttpClient();

        Call call = client.newCall(request);
        return call;
    }

    public static final class Builder {
        private String divideVersion;
        private String supPhone;
        private String supFirm;
        private String imei;
        private String imsi;
        private String cuid;
        private String sessionID;
        private String pid = "117450";
        private String mt = "4";
        private String protocolVersion = "1.0";
        private String sign;
        private String requestKey = "8127B1FF-DD18-49F8-D48B-6928FB6E2928";
        private String url;
        private String body = "";

        private String channelID;
        private String language = Locale.getDefault().toString();

        //是否是海外产品
        private boolean isForeign = true;
        private Context ctx;

        private HashMap<String, Object> bodyParams = new HashMap<String, Object>();

        public Builder() {

        }

        private void prepare(Context ctx) {
            this.ctx = ctx;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder pid(String pid) {
            this.pid = pid;
            return this;
        }

        public Builder foreign(boolean isForeign) {
            this.isForeign = isForeign;
            return this;
        }

        public Builder requestKey(String requestKey) {
            this.requestKey = requestKey;
            return this;
        }

        public Builder protocolVersion(String protocolVer) {
            this.protocolVersion = utf8URLencode(protocolVer);
            return this;
        }

        public Builder addBodyParameter(String key, Object val) {
            bodyParams.put(key, val);
            return this;
        }

        public Doge build() {
            assemble();
            return new Doge(this);
        }

        private void assemble() {

            if (ctx == null) {
                Log.e(TAG, "corgi need to be prepared by invoke prepare(Context) before invoke other methods!");
                return;
            }

            try {
                divideVersion = utf8URLencode(NetLibUtil.getDivideVersion(ctx));
                if (isForeign) {
                    supFirm = Des2.encode(Des2.KEY, Des2.DESIV, utf8URLencode(Build.VERSION.RELEASE).getBytes());
                    supPhone = Des2.encode(Des2.KEY, Des2.DESIV, utf8URLencode(Build.MODEL).getBytes());
                    imei = Des2.encode(Des2.KEY, Des2.DESIV, utf8URLencode(TelephoneUtil.getIMSI(ctx)).getBytes());
                    cuid = utf8URLencode(NetLibUtil.getCUID(ctx));
                    cuid = cuid.replace("|", "%7c");
                } else {
                    supPhone = utf8URLencode(replaceIllegalCharacter(Build.MODEL, "_"));
                    supFirm = utf8URLencode(Build.VERSION.RELEASE);
                    imei = utf8URLencode(NetLibUtil.getIMEI(ctx));
                    imsi = utf8URLencode(NetLibUtil.getIMSI(ctx));
                    cuid = utf8URLencode(NetLibUtil.getCUID(ctx));
                }
                channelID = NdAnalytics.getChannel(ctx);
                sessionID = "";
            } catch (Exception e) {
                e.printStackTrace();
            }

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

            if (isForeign) {
                sign = DigestUtil.md5Hex(pid + mt + divideVersion + supPhone + supFirm + imei /*+ IMSI*/ + sessionID + cuid + protocolVersion + channelID + language + body + requestKey);
            } else {
                sign = DigestUtil.md5Hex(pid + mt + divideVersion + supPhone + supFirm + imei + imsi + sessionID + cuid + protocolVersion + body + requestKey);
            }
        }

        public static String utf8URLencode(String url) {
            StringBuffer result = new StringBuffer();
            if (url != null)
                for (int i = 0; i < url.length(); i++) {
                    char c = url.charAt(i);
                    if ((c >= 0) && (c <= 255)) {
                        result.append(c);
                    } else {
                        byte[] b = new byte[0];
                        try {
                            b = Character.toString(c).getBytes(HTTP.UTF_8);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        for (int j = 0; j < b.length; j++) {
                            int k = b[j];
                            if (k < 0)
                                k += 256;
                            result.append("%" + Integer.toHexString(k).toUpperCase());
                        }
                    }
                }
            return result.toString();
        }

        /**
         * <br>Description: 替换非法字符(去除非中文、字母、数字、空格、下划线、"-"的字符)
         * <br>Author:caizp
         * <br>Date:2016年10月13日下午3:11:21
         */
        private String replaceIllegalCharacter(String source, String replaceChar) {
            if (TextUtils.isEmpty(replaceChar)) {
                return source;
            }
            if (TextUtils.isEmpty(source)) {
                return "";
            }
            String regex = "[^\\sa-zA-Z0-9\u4e00-\u9fa5_-]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(source);
            String result = matcher.replaceAll(replaceChar);
            return result;
        }
    }

}

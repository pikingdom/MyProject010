package com.example.administrator.myservertest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.example.administrator.myservertest.data.Samples;
import com.example.administrator.myservertest.net.ServerResultHeader;
import com.example.administrator.myservertest.net.ThemeHttpCommon;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/8/2.
 */

public class NetOptApiHelper {

    public static void getGuessLikeThemeList_4034(Context ctx){
        getGuessLikeThemeList_4034(ctx,1,6);
    }

    public static void getGuessLikeThemeList_4034(Context ctx , int pageIndex, int pageSize){
        int acitonCode = 4034;
        String jsonParams = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Mo", 2);
            jsonObject.put("IsRandom", 1);
            jsonObject.put("PageSize", pageSize);
            jsonObject.put("PageIndex", pageIndex);
            jsonParams = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> paramsMap = new HashMap<String, String>();
        addGlobalRequestValue(paramsMap, ctx.getApplicationContext(), jsonParams);
        ThemeHttpCommon httpCommon = new ThemeHttpCommon(getThemeActionUrl(acitonCode));
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, jsonParams);

    }

    /**国内服务器*/
    public final static String Panda_Space_Inland_Server = Samples.HOST;
    /**
     * 六、	主题相关接口(4001-5000)
     * @param actionCode
     * @return
     */
    private static String getThemeActionUrl(int actionCode){

        return Panda_Space_Inland_Server+"action.ashx/ThemeAction/"+actionCode;
    }

    public static void addGlobalRequestValue(HashMap<String, String> paramsMap, Context ctx, String jsonParams) {
        addGlobalRequestValue(paramsMap, ctx, jsonParams, PID ,ProtocolVersion,REQUEST_KEY);
    }
    public static final String REQUEST_KEY = "BWG7X-J98B3-W34RT-33B3R-JVYW9";
    public static final String VERSION = "1.0";
    public static final String ProtocolVersion = utf8URLencode(VERSION);
    public static String PID = "6";
    public static String DivideVersion;
    public static final String MT = "4";
    public static String SupPhone;
    public static String SupFirm;
    public static String IMEI;
    public static String IMSI;
    public static String CUID;
    public static String PkgName;

    public static void addGlobalRequestValue(HashMap<String, String> paramsMap, Context ctx, String jsonParams, String pid,String protocolVersion,String requestKey) {
        if (paramsMap == null)
            return;
        if (jsonParams == null){
            jsonParams = "";
        }
        try {
            if (null == DivideVersion)
                DivideVersion =utf8URLencode(getDivideVersion(ctx));

            if (null == SupPhone)
                SupPhone = utf8URLencode(replaceIllegalCharacter(Build.MODEL, "_"));
            if (null == SupFirm)
                SupFirm = utf8URLencode(Build.VERSION.RELEASE);
            if (null == IMEI)
                IMEI = utf8URLencode(getIMEI(ctx));
            if (null == IMSI)
                IMSI = utf8URLencode(getIMSI(ctx));
            if (null == CUID)
                CUID = URLEncoder.encode(getCUID(ctx), "UTF-8");
            if( null == PkgName){
                PkgName = utf8URLencode(ctx.getPackageName());
            }

            String sessionID = "";

            paramsMap.put("PID", pid);
            paramsMap.put("MT", MT);
            paramsMap.put("DivideVersion", DivideVersion);
            paramsMap.put("SupPhone", SupPhone);
            paramsMap.put("SupFirm", SupFirm);
            paramsMap.put("IMEI", IMEI);
            paramsMap.put("IMSI", IMSI);
            paramsMap.put("SessionId", sessionID);
            paramsMap.put("CUID", CUID);//通用用户唯一标识 NdAnalytics.getCUID(ctx)
            paramsMap.put("PkgName", PkgName);
            paramsMap.put("ProtocolVersion", protocolVersion);
            String Sign = DigestUtil.md5Hex(pid + MT + DivideVersion + SupPhone + SupFirm + IMEI + IMSI + sessionID + CUID +PkgName+ protocolVersion + jsonParams + requestKey);
            paramsMap.put("Sign", Sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDivideVersion(Context context) {
        String versionName = "";
        try {
            PackageInfo packageinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            versionName = packageinfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static final String CHARSET_UTF_8 = "UTF-8";

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
                        b = Character.toString(c).getBytes(CHARSET_UTF_8);
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
    public static String replaceIllegalCharacter(String source, String replaceChar) {
        if(TextUtils.isEmpty(replaceChar)) {
            return source;
        }
        if(TextUtils.isEmpty(source)) {
            return "";
        }
        String regex = "[^\\sa-zA-Z0-9\u4e00-\u9fa5_-]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        String result = matcher.replaceAll(replaceChar);
        return result;
    }

    /**
     * 取得IMEI号
     *
     * @param ctx
     * @return
     */
    public static String getIMEI(Context ctx) {
        if (ctx == null)
            return "91";

        String imei = "91";
        try {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
            if (imei == null || "".equals(imei))
                return "91";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imei;
    }

    public static String getIMSI(Context ctx) {
        if (ctx == null)
            return "91";

        String imsi = "91";
        try {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            imsi = tm.getSubscriberId();
            if (imsi == null || "".equals(imsi))
                return "91";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imsi;
    }

    /**
     * 通过反射的方法，获取CUID
     *
     * @param ctx
     */
    public static String getCUID(Context ctx) {
        if (null == ctx)
            return "";
//        return getUniquePsuedoID();
        return "D2578223AB880515D1FE4FB83CF07410%7C981799330391368";
    }

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID() {
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}

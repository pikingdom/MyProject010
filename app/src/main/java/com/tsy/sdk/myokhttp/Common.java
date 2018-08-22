package com.tsy.sdk.myokhttp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.administrator.myservertest.App;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/8/22.
 */

public class Common {
    public static final String REQUEST_KEY = "BWG7X-J98B3-W34RT-33B3R-JVYW9";
    public static final String VERSION = "1.0";
    public static final String ProtocolVersion = utf8URLencode(VERSION);
    public static String PID = "6";
    public static final String MT = "4";
    public static String DivideVersion;
    public static String SupPhone;
    public static String SupFirm;
    public static String IMEI;
    public static String IMSI;
    public static String CUID;
    public static String getPid(){
        return PID;
    }

    public static String getMt(){
        return MT;
    }

    public static String getDivideVersion(){
        if (null == DivideVersion)
            DivideVersion = utf8URLencode(getDivideVersion(App.getContext()));
        return  DivideVersion;
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

    public static String getSupPhone(){
        if (null == SupPhone)
            SupPhone = utf8URLencode(replaceIllegalCharacter(Build.MODEL, "_"));
        return SupPhone;
    }

    public static String getImei(){
        if (null == IMEI)
            IMEI = utf8URLencode(getIMEI(App.getContext()));
        return IMEI;
    }

    public static String getImsi(){
        if (null == IMSI)
            IMSI = utf8URLencode(getIMSI(App.getContext()));
        return IMSI;
    }

    public static String getCuid(){
        if (null == CUID)
            CUID = getCUID(App.getContext());
        return CUID;
    }

    /**
     * 通过反射的方法，获取CUID
     *
     * @param ctx
     */
    public static String getCUID(Context ctx) {
        if (null == ctx)
            return "";
        return getUniquePsuedoID();
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

    public static String getSupFirm(){
        if (null == SupFirm)
            SupFirm = utf8URLencode(Build.VERSION.RELEASE);
        return SupFirm;
    }

    public static int getVersionCode(Context context) {
        int versionName  = -1;
        try {
            PackageInfo packageinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            versionName = packageinfo.versionCode;
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
}

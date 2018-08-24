package com.tsy.sdk.myokhttp.util;

import android.text.TextUtils;

/**
 * Created by Administrator on 2018/8/24.
 */

public class MyOKhttpHeler {

    public static boolean isEmpty(CharSequence str){
        if("{}".equals(str)){
            return true;
        }
        return TextUtils.isEmpty(str);
    }
}

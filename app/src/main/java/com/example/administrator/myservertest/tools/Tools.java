package com.example.administrator.myservertest.tools;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/8/20.
 */

public class Tools {

    //读取本地JSON字符
    public static String ReadDayDayString(Context context,String fileName) {
        InputStream is = null;
        String msg = null;
        try {
            is = context.getResources().getAssets().open(fileName);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            msg = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }

}

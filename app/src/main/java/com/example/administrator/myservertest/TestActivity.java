package com.example.administrator.myservertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.myservertest.tools.Tools;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/8/20.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void getWeatherCondition(View v){
        String content = Tools.ReadDayDayString(getApplicationContext(),"w_condition");
        Log.e("zhenghonglin",content);
        try{
            JSONObject jsonObject = new JSONObject(content);
            JSONObject weatherJsonObjec = jsonObject.getJSONArray("HeWeather6").getJSONObject(0);
            String cid = weatherJsonObjec.getJSONObject("basic").getString("cid");
            String location = weatherJsonObjec.getJSONObject("basic").getString("location");
            int cond_code = weatherJsonObjec.getJSONObject("now").getInt("cond_code");
            Log.e("zhenghonglin","cid:"+cid);
            Log.e("zhenghonglin","location:"+location);
            Log.e("zhenghonglin","cond_code:"+cond_code);
//            Log.e("zhenghonglin","cid:"+cid);
        }catch (Exception e){

        }
    }

    public void getWeatherLoc(View v){
        String content = Tools.ReadDayDayString(getApplicationContext(),"w_condition1");
        Log.e("zhenghonglin",content);
        try{
            JSONObject jsonObject = new JSONObject(content);
            JSONObject weatherJsonObjec = jsonObject.getJSONArray("HeWeather6").getJSONObject(0);
            JSONObject basicJsonObjec = weatherJsonObjec.getJSONArray("basic").getJSONObject(0);
            Log.e("zhenghonglin","location:"+basicJsonObjec.getString("location"));
        }catch (Exception e){

        }
    }

    @Override
    public void onClick(View v) {

    }
}

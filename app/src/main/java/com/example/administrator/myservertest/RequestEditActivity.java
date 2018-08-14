package com.example.administrator.myservertest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myservertest.data.Samples;
import com.example.administrator.myservertest.okhttp.AppHttpRequest;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RequestEditActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_INDEX = "EXTRA_INDEX";

    private TextView title_tv;
    private EditText url_et;
    private EditText body_et;
    private Button start_btn;
    private TextView result_tv;

    private int selectedIndex;

    private Handler handler = new Handler();

    private MyOkHttp mMyOkhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        findViews();
        Bundle extras = getIntent().getExtras();
        selectedIndex = extras.getInt(EXTRA_INDEX, 0);
        initData();
        mMyOkhttp = App.getContext().getMyOkHttp();

    }

    private void initData() {
        Samples.Sample sample = Samples.getVideoSamples().get(selectedIndex);
        if(sample != null){
            title_tv.setText(sample.getTitle());
            url_et.setText(sample.getRequestUrl());
            body_et.setText(sample.getRequestBody());
        }
    }

    private void findViews() {
        title_tv = findViewById(R.id.title_tv);
        url_et = findViewById(R.id.url_et);
        body_et = findViewById(R.id.body_et);
        start_btn = findViewById(R.id.start_btn);
        result_tv = findViewById(R.id.result_tv);
        start_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == start_btn){
            startRequest();
        }
    }

    private void startRequest(){
        result_tv.setText("");
        final String jsonParams  = body_et.getText().toString();
        final String url = url_et.getText().toString();
        if(TextUtils.isEmpty(jsonParams) || TextUtils.isEmpty(url)){
            return;
        }
        //1、方法一
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HashMap<String, String> paramsMap = new HashMap<String, String>();
//                NetOptApiHelper.addGlobalRequestValue(paramsMap, getApplicationContext(), jsonParams);
//                ThemeHttpCommon httpCommon = new ThemeHttpCommon(url);
//                ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, jsonParams);
//                final String respondStr =  csResult.getResponseJson();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        result_tv.setText(respondStr);
//                    }
//                });
//            }
//        }).start();
//        AppHttpRequest doge = new AppHttpRequest.Builder().url(url).body(jsonParams).build();
        //1、方法二
//        AppHttpRequest.newBuilder()
//                .url(url)
//                .body(jsonParams)
//                .build()
//                .equeue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, final IOException e) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                result_tv.setText(e.getMessage());
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        final String responseJson = response.body().string();
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                result_tv.setText(responseJson);
//                            }
//                        });
//                    }
//                });
        //1、方法三
        Map<String, String> params = new HashMap<>();
        params.put("PageIndex", "2");
        params.put("PageSize", "3");
        params.put("Mo", "2");
        mMyOkhttp.post().url(url).params(params).tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        mMyOkhttp.cancel(this);
        super.onDestroy();
    }
}

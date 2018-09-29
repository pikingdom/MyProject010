package com.example.administrator.myservertest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.myservertest.adapter.SampleListAdapter;
import com.example.administrator.myservertest.data.Samples;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.DownloadResponseHandler;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView exampleList = findViewById(R.id.selection_activity_list);
        exampleList.setAdapter(new SampleListAdapter(this, Samples.getVideoSamples()));
        exampleList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("zhenghonglin","11111111111111111");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                NetOptApiHelper.getGuessLikeThemeList_4034(getApplicationContext());
//            }
//        }).start();

//        String str = NetOptApiHelper.getUniquePsuedoID();
//        Log.e("zhenghonglin","id:"+str);
//        Log.e("zhenghonglin","id:"+DigestUtil.md5Hex(str));
//        Log.e("zhenghonglin","id:"+DigestUtil.md5Hex("dfafejojo"));
        if(position == 3){
            //正常下载
//            String downloadUrl = Samples.HOST+"soft/download.aspx?Identifier=%s";
//            String pkg = "com.nd.hilauncherdev.plugin.navigation";
//            String url= String.format(downloadUrl,pkg);
//            final BaseDownloadInfo downloadInfo = new BaseDownloadInfo(pkg,
//                    BaseDownloadInfo.FILE_TYPE_APK,url, "zheng", "/sdcard/", "honglin", null);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    DownloadManager.getInstance(getApplicationContext()).addNormalTask(downloadInfo, null);
//                }
//            }).start();
            //MyOkhttp 下载
            String url = "http://api.zlauncher.cn/soft/download.aspx?Identifier=com.nd.hilauncherdev.plugin.navigation";
            MyOkHttp.getInstance().download().url(url)
                    .filePath(Environment.getExternalStorageDirectory() + "/ahome/navigation.jar").tag(this)
                    .enqueue(new DownloadResponseHandler() {
                        @Override
                        public void onFinish(File downloadFile) {
                            Log.d(TAG, "doDownload onFinish:");
                        }

                        @Override
                        public void onProgress(long currentBytes, long totalBytes) {
                            Log.d(TAG, "doDownload onProgress:" + currentBytes + "/" + totalBytes);
                        }

                        @Override
                        public void onFailure(String error_msg) {
                            Log.d(TAG, "doDownload onFailure:" + error_msg);
                        }

                        @Override
                        public void onStart(long totalBytes) {
                            super.onStart(totalBytes);
                            Log.d(TAG, "doDownload onStart:"+totalBytes);
                        }
                    });
        } else {
            Intent intent = new Intent(this, RequestEditActivity.class);
            intent.putExtra(RequestEditActivity.EXTRA_INDEX, position);
            startActivity(intent);
        }

    }
}

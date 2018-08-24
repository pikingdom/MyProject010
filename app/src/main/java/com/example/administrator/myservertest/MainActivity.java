package com.example.administrator.myservertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.administrator.myservertest.adapter.SampleListAdapter;
import com.example.administrator.myservertest.data.Samples;
import com.example.administrator.myservertest.download.DownloadManager;
import com.nd.hilauncherdev.webconnect.downloadmanage.model.BaseDownloadInfo;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

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
            String downloadUrl = Samples.HOST+"soft/download.aspx?Identifier=%s";
            String pkg = "com.nd.hilauncherdev.plugin.navigation";
            String url= String.format(downloadUrl,pkg);
            final BaseDownloadInfo downloadInfo = new BaseDownloadInfo(pkg,
                    BaseDownloadInfo.FILE_TYPE_APK,url, "zheng", "/sdcard/", "honglin", null);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DownloadManager.getInstance(getApplicationContext()).addNormalTask(downloadInfo, null);
                }
            }).start();
        } else {
            Intent intent = new Intent(this, RequestEditActivity.class);
            intent.putExtra(RequestEditActivity.EXTRA_INDEX, position);
            startActivity(intent);
        }

    }
}

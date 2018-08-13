package com.example.administrator.myservertest.download;

import android.util.Log;

import com.nd.hilauncherdev.webconnect.downloadmanage.model.AbstractDownloadCallback;
import com.nd.hilauncherdev.webconnect.downloadmanage.model.BaseDownloadInfo;

public class DownloadCallback extends AbstractDownloadCallback {

    @Override
    public void onBeginDownload(BaseDownloadInfo downloadInfo) {
        if (downloadInfo != null) {
            Log.e("zhenghonglin",""+downloadInfo.getDownloadUrl());
            Log.e("zhenghonglin",""+downloadInfo.getSavedName());
        }

    }

    @Override
    public void onDownloadCompleted(final BaseDownloadInfo downloadInfo, boolean fileExist) {
        if (downloadInfo != null) {
            Log.e("zhenghonglin","1:"+downloadInfo.getDownloadUrl());
            Log.e("zhenghonglin","1:"+downloadInfo.getSavedName());
        }
    }

    @Override
    public void onDownloadFailed(BaseDownloadInfo downloadInfo, Exception e) {
        if (downloadInfo != null) {
            Log.e("zhenghonglin","2:"+downloadInfo.getDownloadUrl());
            Log.e("zhenghonglin","2:"+downloadInfo.getSavedName());
        }
    }


    @Override
    public void onDownloadWorking(BaseDownloadInfo downloadInfo) {
        downloadInfo.getFilePath();
    }

    @Override
    public void onHttpReqeust(BaseDownloadInfo downloadInfo, int requestType) {

    }

}

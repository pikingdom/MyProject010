package com.nd.hilauncherdev.framework.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * 视频壁纸数据库工具类
 * Created by linliangbin on 2016/10/18.
 */

public class BaseDataBase extends AbstractDataBase {


    public static final String DB_NAME = "viewpager.db";
    public static final String DOWNLOAD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS \'log_download\' (\'_id\' VARCHAR(32) PRIMARY KEY  NOT NULL ,\'download_url\' VARCHAR(32) NOT NULL ,\'progress\' INTEGER NOT NULL  DEFAULT (0) ,\'total_size\' VARCHAR(32) NOT NULL  DEFAULT (\'0.0MB\') ,\'title\' VARCHAR(8) NOT NULL ,\'icon_path\' VARCHAR(32) ,\'download_size\' VARCHAR(32) NOT NULL  DEFAULT (\'0.0MB\') , \'file_path\' VARCHAR(32) ,\'file_type\' INTEGER NOT NULL  DEFAULT (0) , addition_info)";
    private static final int VERSION = 2;
    private static BaseDataBase mMyPhoneDB;

    public BaseDataBase(Context c) {
        super(c, DB_NAME, VERSION);
    }

    public BaseDataBase(Context c, String dbName, int version) {
        super(c, dbName, version);
    }

    public static BaseDataBase getInstance(Context c) {
        if (mMyPhoneDB == null) {
            mMyPhoneDB = new BaseDataBase(c.getApplicationContext());
        }
        return mMyPhoneDB;
    }


    @Override
    public void onDataBaseCreate(SQLiteDatabase var1) {
        var1.execSQL(DOWNLOAD_CREATE_TABLE);
    }

    @Override
    public void onDataBaseUpgrade(SQLiteDatabase var1, int var2, int var3) {

    }

}

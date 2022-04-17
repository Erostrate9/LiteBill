package com.syx.litebill;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.syx.litebill.db.DBManager;

/*
* 表示全局应用的类
* */
public class UniteApp extends Application {
    @Override
    public void onCreate() {
        // 初始化数据库
        super.onCreate();
        DBManager.initDB(getApplicationContext());
    }
}

package com.wenld.junitdemo;

import android.app.Application;

import com.wenld.junitdemo.database.AbstractDatabaseManager;

/**
 * <p/>
 * Author: wenld on 2017/8/21 17:09.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class RobolectApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();

    }

    private void initConfig() {
        AbstractDatabaseManager.initOpenHelper(getApplicationContext());//初始化数据库
    }
}

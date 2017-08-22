package com.wenld.junitdemo;

import android.content.SharedPreferences;

/**
 * <p/>
 * Author: wenld on 2017/8/22 15:14.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class ShareDAO {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public ShareDAO(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
        this.editor = sharedPref.edit();
    }

    public ShareDAO() {
//        this(App.getContext().getSharedPreferences("myShare", Context.MODE_PRIVATE));
    }

    public void put(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key) {
        return sharedPref.getString(key, "");
    }
}

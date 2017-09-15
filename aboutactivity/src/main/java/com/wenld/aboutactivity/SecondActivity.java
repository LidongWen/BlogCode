package com.wenld.aboutactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Logger.e(this.getClass().getSimpleName()+" "+ Thread.currentThread().getStackTrace()[2].getMethodName());
    }
    @Override
    protected void onStart() {
        super.onStart();
        Logger.e(this.getClass().getSimpleName()+" "+ Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.e(this.getClass().getSimpleName()+" "+ Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.e(this.getClass().getSimpleName()+" "+ Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.e(this.getClass().getSimpleName()+" "+ Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.e(this.getClass().getSimpleName()+" "+ Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e(this.getClass().getSimpleName()+" "+ Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}

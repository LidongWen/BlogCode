package com.wenld.aboutactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

//        Log.d("Tag", "I'm a log which you don't see easily, hehe");
//        Log.d("json content", "{ \"key\": 3, \n \"value\": something}");
//        Log.d("error", "There is a crash somewhere or any warning");
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("ActivityLife")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Logger.e(this.getClass().getSimpleName() + " " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.e(this.getClass().getSimpleName() + " " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.e(this.getClass().getSimpleName() + " " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.e(this.getClass().getSimpleName() + " " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.e(this.getClass().getSimpleName() + " " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.e(this.getClass().getSimpleName() + " " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e(this.getClass().getSimpleName() + " " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
    }
}

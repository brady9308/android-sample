package com.ryg.chapter_1;

import com.ryg.chapter_1.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import static android.os.SystemClock.sleep;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            String test = savedInstanceState.getString("extra_test");
            Log.e(TAG, "[onCreate]restore extra_test:" + test);
        }
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.ryg.charpter_1.c");
                intent.setClass(MainActivity.this, SecondActivity.class);
                //intent.putExtra("time", System.currentTimeMillis());
                //intent.addCategory("com.ryg.category.c");
                //intent.setDataAndType(Uri.parse("file://abc"), "text/plain");
                startActivity(intent);
            }
        });
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent, time=" + intent.getLongExtra("time", 0));
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onStart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged, newOrientation:" + newConfig.orientation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");
        outState.putString("extra_test", "test");
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Log.e(TAG, "onRestoreInstanceState");
        String test = savedInstanceState.getString("extra_test");
        Log.e(TAG, "[onRestoreInstanceState]restore extra_test:" + test);
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
        sleep(1500);
        Log.e(TAG, "onPauseEnd");
    }
    
    @Override
    protected void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
        sleep(1500);
        Log.e(TAG, "onStopEnd");
    }
    
    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        sleep(1500);
        Log.e(TAG, "onDestroyEnd");
    }
}

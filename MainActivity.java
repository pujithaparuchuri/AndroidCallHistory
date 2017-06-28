package com.calllog;

import android.content.Intent;
import android.provider.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
/**
* This class is used to run the new instance of call log class
*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        TimerTask ts=new TimerTask() {
            @Override
            public void run() {
                Intent it=new Intent(MainActivity.this,CallLog.class);
                startActivity(it);
                finish();
            }
        };
        new Timer().schedule(ts,3000);
    }
}

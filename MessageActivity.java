package com.calllog;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;                                                    
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
* This class is related to message related activity with method which send messages and message delivery related messages
*/


public class MessageActivity extends AppCompatActivity {

    SmsManager manager;
    String no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        manager=SmsManager.getDefault();
        no=getIntent().getStringExtra("no");
        EditText et=(EditText)findViewById(R.id.mno);
        et.setText(no);
    }

    public void doSend(View v)
    {
        EditText et=(EditText)findViewById(R.id.mmes);
        String s=et.getText().toString();
        PendingIntent sp=PendingIntent.getBroadcast(this,1,new Intent("SENT"),0);
        manager.sendTextMessage(no,null,s,sp,null);
registerReceiver(new SentReceiver(),new IntentFilter("SENT"));
    }

    class SentReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode())
            {
                case RESULT_OK:
                    Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    Toast.makeText(context,"No Service !Please try again later",Toast.LENGTH_LONG).show();

            }
        }
    }
}

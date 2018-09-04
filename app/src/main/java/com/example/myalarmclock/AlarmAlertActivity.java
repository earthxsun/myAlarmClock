package com.example.myalarmclock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class AlarmAlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_alert);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        long time = intent.getLongExtra("time",0);
        Log.d("mytest", "闹钟ID" + id);
        Log.d("mytest", "闹钟time" + time);
        InitData.sAlarmManagerHelper.openAlarm(id, "ddd",
                "ffff", time + 1000 * 60);
        new AlertDialog.Builder(AlarmAlertActivity.this)
                .setTitle("闹钟")
                .setMessage("时间到了。。。。")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
//                                System.exit(0);
//                                android.os.Process
//                                        .killProcess(android.os.Process
//                                                .myPid());
                            }
                        }).show();
    }
}

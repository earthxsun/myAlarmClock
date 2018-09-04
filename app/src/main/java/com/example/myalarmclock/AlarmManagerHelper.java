package com.example.myalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmManagerHelper {

    private Context mContext;
    private AlarmManager mAlarmManager;

    public AlarmManagerHelper(Context context) {
        mContext = context;
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void openAlarm(int id, String title, String content, long time) {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("time",time);
        intent.setClass(mContext, AlarmAlertReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pi);
    }
}

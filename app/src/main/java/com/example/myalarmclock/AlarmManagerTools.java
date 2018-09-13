package com.example.myalarmclock;

import android.content.Context;
import android.util.Log;

import com.example.myalarmclock.Db.Alarm;
import com.example.myalarmclock.Db.DbTools;

import org.litepal.LitePal;

import java.util.Calendar;
import java.util.List;

public class AlarmManagerTools {

    private static Calendar calendar = Calendar.getInstance();

    public static void LoadExistingAlarm(Context context) {
        AlarmManagerHelper alarmManagerHelper = new AlarmManagerHelper(context);
        Calendar currentCalendar = Calendar.getInstance();
        long currentTime = currentCalendar.getTimeInMillis();
        List<Alarm> mAlarmList = DbTools.queryData();
        for (Alarm alarm : mAlarmList) {
            if (alarm.isOpen()) {

                calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
                calendar.set(Calendar.MINUTE, alarm.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                switch (alarm.getStatus()) {
                    case InitData.NORMAL:
                        if (alarm.isOnce() && (calendar.getTimeInMillis() - currentTime > 0)) {
                            alarmManagerHelper.openAlarm((int) alarm.getId(), alarm.getTitle(), "aaaa", calendar.getTimeInMillis());
                            Log.d("mytest", "闹钟时间正常加载");
                        } else if (alarm.isOnce() && (calendar.getTimeInMillis() - currentTime <= 0)) {
                            alarm.setStatus(InitData.DISMISS);
                            alarm.setOpen(false);
                            alarm.save();
                            Log.d("mytest", "闹钟时间过了就不应该加载");
                            InitData.mAlarmItemList.clear();
                            InitData.initAlarmItem();
                            InitData.mAlarmlistAdapter.notifyDataSetChanged();
                        }

                        if (alarm.isEveryday()) {
                            Log.d("mytest", "每天闹钟");
                        }
                        break;
                    case InitData.DISMISS:
                        alarmManagerHelper.closeAlarm((int) alarm.getId(), alarm.getTitle(), "aaaa", calendar.getTimeInMillis());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void UpdateExistingAlarm(Context context, int id) {
        AlarmManagerHelper alarmManagerHelper = new AlarmManagerHelper(context);
        Alarm alarm = LitePal.find(Alarm.class, id);
        Calendar currentCalendar = Calendar.getInstance();
        long hour24 = 24 * 60 * 60 * 1000;
        long currentTime = currentCalendar.getTimeInMillis();

        if (alarm.isOpen() && alarm.getStatus() == InitData.NORMAL) {
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            if (alarm.isOnce()) {
                //单次闹钟
                alarmManagerHelper.openAlarm((int) alarm.getId(), alarm.getTitle(), "aaaa", calendar.getTimeInMillis());

            } else if (alarm.isEveryday()) {
                //每天闹钟
                if (calendar.getTimeInMillis() - currentTime > 0) {
                    alarmManagerHelper.openAlarm((int) alarm.getId(), alarm.getTitle(), "aaaa", calendar.getTimeInMillis());
                    Log.d("mytest", "闹钟：" + id + " 当天响铃");
                } else {
                    long nextTimeMillis = hour24 - (currentTime - calendar.getTimeInMillis());//计算下一次响铃的毫秒数
                    alarmManagerHelper.openAlarm((int) alarm.getId(), alarm.getTitle(), "aaaa",
                            System.currentTimeMillis() + nextTimeMillis);
                    Log.d("mytest", "闹钟：" + id + " 明天响铃");
                }
            }

        } else {
            Log.d("mytest", "闹钟注册失败：" + alarm.getId());
            Log.d("mytest", "闹钟注册失败：" + alarm.isOpen());
            Log.d("mytest", "闹钟注册失败：" + alarm.getStatus());
        }
    }

}

package com.example.myalarmclock.Db;

import android.content.ContentValues;

import com.example.myalarmclock.InitData;

import org.litepal.LitePal;

import java.util.List;

public class DbTools {
//    public static MediaPlayer mMediaPlayer = new MediaPlayer();

    public static List<Alarm> queryData(){
        List<Alarm> alarmList = LitePal.findAll(Alarm.class);
//        for (Alarm mAlarm:alarmList){
//            Log.d("mytest", "Alarm名称" + mAlarm.getTitle());
//            Log.d("mytest", "Alarm日期" + mAlarm.getYear()+"年"+mAlarm.getMonth()+"月"+mAlarm.getDay()+"日");
//            Log.d("mytest", "Alarm时间" + mAlarm.getHour()+":"+mAlarm.getMinute()+":");
//            Log.d("mytest", "Alarm铃声URi" + mAlarm.getRingUri());
//            Log.d("mytest", "Alarm铃声名称" + mAlarm.getRingName());
//            Log.d("mytest", "Alarm单次：" + mAlarm.isOnce());
//            Log.d("mytest", "Alarm每天：" + mAlarm.isEveryday());
//            Log.d("mytest", "Alarm工作日：" + mAlarm.isStatutoryholiday());
//            Log.d("mytest", "Alarm周1:" + mAlarm.isMonday());
//            Log.d("mytest", "Alarm周2:" + mAlarm.isTuesday());
//            Log.d("mytest", "Alarm周3:" + mAlarm.isWednesday());
//            Log.d("mytest", "Alarm周4:" + mAlarm.isThursday());
//            Log.d("mytest", "Alarm周5:" + mAlarm.isFriday());
//            Log.d("mytest", "Alarm周6:" + mAlarm.isSaturday());
//            Log.d("mytest", "Alarm周日:" + mAlarm.isSunday());
//        }
        return alarmList;
    }

    //闹钟响铃后更新status和isOpen的状态
    public static void updateAlarmStatus(final int id){
        Alarm alarm = LitePal.find(Alarm.class,id);
        ContentValues values = new ContentValues();

        if (alarm.isOnce()){
            values.put("status", InitData.DISMISS);
            values.put("isOpen",false);
            LitePal.update(Alarm.class,values,id);
        }

        InitData.mAlarmItemList.clear();
        InitData.initAlarmItem();
        InitData.mAlarmlistAdapter.notifyDataSetChanged();
    }

    public static void updateAlarmRepeat(Alarm alarm){
        ContentValues values = new ContentValues();
        values.put("everyday",alarm.isEveryday());
        values.put("once",alarm.isOnce());
        values.put("statutoryholiday",alarm.isStatutoryholiday());
        values.put("monday",alarm.isMonday());
        values.put("tuesday",alarm.isTuesday());
        values.put("wednesday",alarm.isWednesday());
        values.put("thursday",alarm.isThursday());
        values.put("friday",alarm.isFriday());
        values.put("saturday",alarm.isSaturday());
        values.put("sunday",alarm.isSunday());
        LitePal.update(Alarm.class,values,alarm.getId());
    }
}

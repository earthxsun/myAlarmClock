package com.example.myalarmclock.Db;

import org.litepal.LitePal;

import java.util.List;

public class DbTools {

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
}

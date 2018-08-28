package com.example.myalarmclock;

import com.example.myalarmclock.Db.Alarm;
import com.example.myalarmclock.Db.DbTools;

import java.util.List;

public class InitData {

    public static final String[] items = new String[]{"单次", "周一到周五", "法定工作日", "每天", "自定义"};

    public static void initAlarmItem(List<AlarmItem> alarmItems) {
        List<Alarm> alarmList = DbTools.queryData();
        String alarmRepeat;
        String time;
        String date;
        String name;

        for (int i = 0; i < alarmList.size(); i++) {
            Alarm alarm = alarmList.get(i);
            name = alarm.getTitle();
            time = alarm.getHour()+":"+alarm.getMinute();
            date = alarm.getYear()+"年"+(alarm.getMonth()+1)+"月"+alarm.getDay()+"日";

            StringBuilder stringBuilder = new StringBuilder();

            if (alarm.isMonday()){
                stringBuilder.append("周一 ");
            }
            if (alarm.isTuesday()){
                stringBuilder.append("周二 ");
            }
            if (alarm.isWednesday()){
                stringBuilder.append("周三 ");
            }
            if (alarm.isThursday()){
                stringBuilder.append("周四 ");
            }
            if (alarm.isFriday()){
                stringBuilder.append("周五 ");
            }
            if (alarm.isSaturday()){
                stringBuilder.append("周六 ");
            }
            if (alarm.isSunday()){
                stringBuilder.append("周日 ");
            }

            alarmRepeat = stringBuilder.toString();

            if(alarmRepeat.equals("周一 周二 周三 周四 周五 ")){
                alarmRepeat = "周一到周五";
            }

            if(alarmRepeat.equals("周日 周一 周二 周三 周四 周五 周六 ")){
                alarmRepeat = "每天";
            }

            if (alarm.isOnce()){
                alarmRepeat = InitData.items[0];
            }

            if (alarm.isMonday() && alarm.isTuesday() && alarm.isWednesday() && alarm.isThursday()
                    && alarm.isFriday()){
                alarmRepeat = InitData.items[1];
            }

            if (alarm.isStatutoryholiday()){
                alarmRepeat = InitData.items[2];
            }

            if (alarm.isEveryday()){
                alarmRepeat = InitData.items[3];
            }

            alarmItems.add(new AlarmItem(name,time,date,alarmRepeat, alarm.isOpen()));
        }
    }
}

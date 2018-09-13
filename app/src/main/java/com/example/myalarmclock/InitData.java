package com.example.myalarmclock;

import com.example.myalarmclock.Db.Alarm;
import com.example.myalarmclock.Db.AlarmlistAdapter;
import com.example.myalarmclock.Db.DbTools;

import java.util.ArrayList;
import java.util.List;

public class InitData {

    public static final String[] items = new String[]{"单次", "周一到周五", "法定工作日", "每天", "自定义"};

    public static final int DISMISS = 0;
    public static final int NORMAL = 1;

    public static AlarmlistAdapter mAlarmlistAdapter;

    public static List<AlarmItem> mAlarmItemList = new ArrayList<>();

    static final String itemName1 = "闹钟标题";
    static final String itemName2 = "响铃日期";
    static final String itemName3 = "重复";
    static final String itemName4 = "铃声设置";

    //初始化闹钟项数据
    public static void initAlarmItem() {
        List<Alarm> alarmList = DbTools.queryData();
        String alarmRepeat;
        String time;
        String date;
        String name;
        long alarmId;

        mAlarmItemList.clear();

        for (int i = 0; i < alarmList.size(); i++) {
            Alarm alarm = alarmList.get(i);

            alarmId = alarm.getId();
            name = alarm.getTitle();
            time = alarm.getHour() + ":" + alarm.getMinute();
            date = alarm.getYear() + "年" + alarm.getMonth() + "月" + alarm.getDay() + "日";

            StringBuilder stringBuilder = new StringBuilder();

            if (alarm.isMonday()) {
                stringBuilder.append("周一 ");
            }
            if (alarm.isTuesday()) {
                stringBuilder.append("周二 ");
            }
            if (alarm.isWednesday()) {
                stringBuilder.append("周三 ");
            }
            if (alarm.isThursday()) {
                stringBuilder.append("周四 ");
            }
            if (alarm.isFriday()) {
                stringBuilder.append("周五 ");
            }
            if (alarm.isSaturday()) {
                stringBuilder.append("周六 ");
            }
            if (alarm.isSunday()) {
                stringBuilder.append("周日 ");
            }

            alarmRepeat = stringBuilder.toString();

            if (alarmRepeat.equals("周一 周二 周三 周四 周五 ")) {
                alarmRepeat = "周一到周五";
            }

            if (alarmRepeat.equals("周日 周一 周二 周三 周四 周五 周六 ")) {
                alarmRepeat = "每天";
            }

            if (alarm.isOnce()) {
                alarmRepeat = InitData.items[0];
            }

            if (alarm.isMonday() && alarm.isTuesday() && alarm.isWednesday() && alarm.isThursday()
                    && alarm.isFriday()) {
                alarmRepeat = InitData.items[1];
            }

            if (alarm.isStatutoryholiday()) {
                alarmRepeat = InitData.items[2];
            }

            if (alarm.isEveryday()) {
                alarmRepeat = InitData.items[3];
            }

            mAlarmItemList.add(new AlarmItem(alarmId, name, time, date, alarmRepeat, alarm.isOpen()));
        }
    }

    //初始化闹钟设置项数据
    public static void initSetAlarmItems(Alarm mAlarm, List<SetAlarmItem> mSetAlarmItems, boolean is_new_alarm) {
        SetAlarmItem item1;
        SetAlarmItem item2;
        SetAlarmItem item3;
        SetAlarmItem item4;

        if (is_new_alarm) {
            String ringDate = mAlarm.getYear() + "年" + mAlarm.getMonth() + "月" + mAlarm.getDay() + "日";
            item1 = new SetAlarmItem(itemName1, mAlarm.getTitle(), R.drawable.arrow_right_gray);
            item2 = new SetAlarmItem(itemName2, ringDate, R.drawable.arrow_right_gray);
            item3 = new SetAlarmItem(itemName3, "单次", R.drawable.arrow_right_gray);
            item4 = new SetAlarmItem(itemName4, mAlarm.getRingName(), R.drawable.arrow_right_gray);
        } else {
            String ringDate = mAlarm.getYear() + "年" + mAlarm.getMonth() + "月" + mAlarm.getDay() + "日";
            item1 = new SetAlarmItem(itemName1, mAlarm.getTitle(), R.drawable.arrow_right_gray);
            item2 = new SetAlarmItem(itemName2, ringDate, R.drawable.arrow_right_gray);

            StringBuilder stringBuilder = new StringBuilder();

            if (mAlarm.isMonday()) {
                stringBuilder.append("周一 ");
            }
            if (mAlarm.isTuesday()) {
                stringBuilder.append("周二 ");
            }
            if (mAlarm.isWednesday()) {
                stringBuilder.append("周三 ");
            }
            if (mAlarm.isThursday()) {
                stringBuilder.append("周四 ");
            }
            if (mAlarm.isFriday()) {
                stringBuilder.append("周五 ");
            }
            if (mAlarm.isSaturday()) {
                stringBuilder.append("周六 ");
            }
            if (mAlarm.isSunday()) {
                stringBuilder.append("周日 ");
            }

            String alarmRepeat = stringBuilder.toString();

            if (alarmRepeat.equals("周一 周二 周三 周四 周五 ")) {
                alarmRepeat = "周一到周五";
            }

            if (alarmRepeat.equals("周日 周一 周二 周三 周四 周五 周六 ")) {
                alarmRepeat = "每天";
            }

            if (mAlarm.isOnce()) {
                alarmRepeat = InitData.items[0];
            }

            if (mAlarm.isMonday() && mAlarm.isTuesday() && mAlarm.isWednesday() && mAlarm.isThursday()
                    && mAlarm.isFriday()) {
                alarmRepeat = InitData.items[1];
            }

            if (mAlarm.isStatutoryholiday()) {
                alarmRepeat = InitData.items[2];
            }

            if (mAlarm.isEveryday()) {
                alarmRepeat = InitData.items[3];
            }
            item3 = new SetAlarmItem(itemName3, alarmRepeat, R.drawable.arrow_right_gray);
            item4 = new SetAlarmItem(itemName4, mAlarm.getRingName(), R.drawable.arrow_right_gray);
        }

        mSetAlarmItems.add(item1);
        mSetAlarmItems.add(item2);
        mSetAlarmItems.add(item3);
        mSetAlarmItems.add(item4);
    }

    public static void ClearAlarmRepeat(Alarm alarm) {
        alarm.setEveryday(false);
        alarm.setMonday(false);
        alarm.setTuesday(false);
        alarm.setWednesday(false);
        alarm.setThursday(false);
        alarm.setFriday(false);
        alarm.setSaturday(false);
        alarm.setSunday(false);
        alarm.setStatutoryholiday(false);
        alarm.setOnce(false);
    }

    public static Alarm SaveAlarmRepeat(Alarm alarm) {
        Alarm alarmSave = alarm;
        return alarmSave;
    }
}

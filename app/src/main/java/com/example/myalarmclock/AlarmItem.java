package com.example.myalarmclock;

public class AlarmItem {

    private String alarmName;

    private String alarmtime;

    private String alarmdate;

    private String alarmRepeat;

    private Boolean open;

    public AlarmItem(String name, String time, String date, String repeat, boolean open) {
        this.alarmName = name;
        this.alarmtime = time;
        this.alarmdate = date;
        this.alarmRepeat = repeat;
        this.open = open;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmtime() {
        return alarmtime;
    }

    public void setAlarmtime(String alarmtime) {
        this.alarmtime = alarmtime;
    }

    public String getAlarmdate() {
        return alarmdate;
    }

    public void setAlarmdate(String alarmdate) {
        this.alarmdate = alarmdate;
    }

    public String getAlarmRepeat() {
        return alarmRepeat;
    }

    public void setAlarmRepeat(String alarmRepeat) {
        this.alarmRepeat = alarmRepeat;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}

package com.example.myalarmclock;

public class AlarmItem {

    private String alarmtime;

    private String alarmdate;

    private Boolean open;

    public AlarmItem(String time, String date, boolean open) {
        this.alarmtime = time;
        this.alarmdate = date;
        this.open = open;
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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}

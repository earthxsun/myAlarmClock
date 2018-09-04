package com.example.myalarmclock.Db;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Calendar;

public class Alarm extends LitePalSupport implements Serializable {

    private long id;

    private String title;

    private int year;

    private int month;

    private int day;

    private int hour;

    private int minute;

    private boolean everyday;

    private boolean once;

    private boolean monday;

    private boolean tuesday;

    private boolean wednesday;

    private boolean thursday;

    private boolean friday;

    private boolean saturday;

    private boolean sunday;

    private boolean statutoryholiday;

    private String ringUri;

    private String ringName;

    private boolean isOpen;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isEveryday() {
        return everyday;
    }

    public void setEveryday(boolean everyday) {
        this.everyday = everyday;
    }

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isStatutoryholiday() {
        return statutoryholiday;
    }

    public void setStatutoryholiday(boolean statutoryholiday) {
        this.statutoryholiday = statutoryholiday;
    }

    public String getRingUri() {
        return ringUri;
    }

    public void setRingUri(String ringUri) {
        this.ringUri = ringUri;
    }

    public String getRingName() {
        return ringName;
    }

    public void setRingName(String ringName) {
        this.ringName = ringName;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public static Alarm getAlarminstance(){
        Calendar mCalendar = Calendar.getInstance();
        Alarm alarm = new Alarm();
        alarm.setTitle("自定义");
        alarm.setRingName("自定义");
        alarm.setYear(mCalendar.get(Calendar.YEAR));
        alarm.setMonth(mCalendar.get(Calendar.MONTH) + 1);
        alarm.setDay(mCalendar.get(Calendar.DAY_OF_MONTH));
        return alarm;
    }
}

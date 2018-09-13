package com.example.myalarmclock;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myalarmclock.Db.Alarm;
import com.example.myalarmclock.Db.DbTools;

import org.litepal.LitePal;

import java.io.IOException;

public class AlarmAlertActivity extends AppCompatActivity {

    float y1 = 0;
    float y2 = 0;
    private int id;

    private MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_alert);


        TextView alarmTitle = findViewById(R.id.alarm_alert_title);
        TextView alarmTime = findViewById(R.id.alarm_alert_time);
        ImageView arrow_up = findViewById(R.id.img_up);
        TextView alertText = findViewById(R.id.alarm_alert_text);
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.arrow_anim);
        arrow_up.startAnimation(animationSet);
        alertText.startAnimation(animationSet);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        Alarm alarm = LitePal.find(Alarm.class, id);
        alarmTitle.setText(alarm.getTitle());
        alarmTime.setText(alarm.getHour() + ":" + alarm.getMinute());

        //闹钟响铃后更新闹钟状态
        DbTools.updateAlarmStatus(id);

        Uri uri = Uri.parse(alarm.getRingUri());
        try {
//            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(this,uri);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            y1 = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            y2 = event.getY();

            if (y1 - y2 > 500) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                }

                AlarmManagerTools.UpdateExistingAlarm(AlarmAlertActivity.this,id);
                finish();
            }
        }

        return super.onTouchEvent(event);
    }
}

package com.example.myalarmclock;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.myalarmclock.Db.Alarm;
import com.example.myalarmclock.Db.DbTools;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SetAlarmActivity extends AppCompatActivity {

    private List<SetAlarmItem> mSetAlarmItems = new ArrayList<>();
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private SetAlarmAdapter alarmAdapter;
    private TimePicker mTimePicker;
    private Alarm mAlarm;
    private Alarm alarmTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        final Intent intent = getIntent();
        mAlarm = (Alarm) intent.getSerializableExtra("alarm_db");
        boolean new_alarm = intent.getBooleanExtra("new",true);
        InitData.initSetAlarmItems(mAlarm,mSetAlarmItems,new_alarm);
        alarmAdapter = new SetAlarmAdapter(SetAlarmActivity.this, R.layout.setalarm_item, mSetAlarmItems);
        ListView listView = findViewById(R.id.set_alarm_listview);
        listView.setAdapter(alarmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SetAlarmItem alarmItem = mSetAlarmItems.get(position);
                //创建AlertDialog对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(SetAlarmActivity.this);
                SetAlarmTools setAlarmTools = new SetAlarmTools(SetAlarmActivity.this, builder, mSetAlarmItems, alarmAdapter, mAlarm, position);


                //根据设置项的名称来判断
                switch (alarmItem.getTitle()) {
                    case InitData.itemName1:
                        setAlarmTools.setAlarmName();//设置闹钟名称
                        break;
                    case InitData.itemName2:
                        setAlarmTools.setAlarmDate();//设置闹钟日期
                        break;
                    case InitData.itemName3:
                        alarmTemp = InitData.SaveAlarmRepeat(mAlarm);//保存闹钟设置，取消可恢复
                        InitData.ClearAlarmRepeat(mAlarm);//设置重复项前，先把默认值设为false
                        setAlarmTools.setAlarmRepeat();//设置重复项
                        break;
                    case InitData.itemName4:
                        setAlarmTools.setRingtone();//设置闹钟铃声
                        break;
                    default:
                        break;
                }
            }
        });


        Button saveButton = findViewById(R.id.save_set_alarm);
        mTimePicker = findViewById(R.id.time_picker);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Alarm alarm = LitePal.find(Alarm.class,mAlarm.getId());
                    //从数据库获取数据，如果为空就是新增闹钟，非空就是修改闹钟项
                    if (alarm == null){
                        mAlarm.setHour(mTimePicker.getHour());
                        mAlarm.setMinute(mTimePicker.getMinute());
                        mAlarm.setOpen(true);
                        mAlarm.save();
                        Intent intent1 = new Intent();
                        setResult(RESULT_OK,intent1);
                        finish();
                    } else {
                        //更新时间
                        mAlarm.setHour(mTimePicker.getHour());
                        mAlarm.setMinute(mTimePicker.getMinute());
                        mAlarm.update(mAlarm.getId());
                        //更新闹钟重复项
                        DbTools.updateAlarmRepeat(mAlarm);
                        Intent intent1 = new Intent();
                        setResult(RESULT_OK,intent1);
                        finish();
                    }
                }
            }
        });

        Button cancelButton = findViewById(R.id.cancel_set_alarm);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarm = alarmTemp;//恢复闹钟设置
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //把选中的铃声更新到界面
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Ringtone ringTitle = RingtoneManager.getRingtone(SetAlarmActivity.this, uri);
            String ringName = ringTitle.getTitle(SetAlarmActivity.this);
            mSetAlarmItems.get(3).setContent(ringName);
            mAlarm.setRingName(ringName);
            mAlarm.setRingUri(uri.toString());
            alarmAdapter.notifyDataSetChanged();
        } else {
            Log.d("mytest", "铃声获取失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //按了返回键就恢复闹钟设置
        mAlarm = alarmTemp;
    }
}

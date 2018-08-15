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

import java.util.ArrayList;
import java.util.List;

public class SetAlarmActivity extends AppCompatActivity {

    private List<SetAlarmItem> mSetAlarmItems = new ArrayList<>();
    private final String itemName1 = "闹钟标题";
    private final String itemName2 = "响铃日期";
    private final String itemName3 = "重复";
    private final String itemName4 = "铃声设置";
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private SetAlarmAdapter alarmAdapter;
    private Button saveButton;
    private TimePicker mTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        initSetAlarmItems();
        alarmAdapter = new SetAlarmAdapter(SetAlarmActivity.this, R.layout.setalarm_item, mSetAlarmItems);
        ListView listView = findViewById(R.id.set_alarm_listview);
        listView.setAdapter(alarmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SetAlarmItem alarmItem = mSetAlarmItems.get(position);
                //创建AlertDialog对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(SetAlarmActivity.this);
                SetAlarmTools setAlarmTools = new SetAlarmTools(SetAlarmActivity.this,builder, mSetAlarmItems,alarmAdapter,position);


                //根据设置项的名称来判断
                switch (alarmItem.getTitle()) {
                    case itemName1:
                        setAlarmTools.setAlarmName();
                        break;
                    case itemName2:
                        setAlarmTools.setAlarmDate();
                        break;
                    case itemName3:
                        setAlarmTools.setAlarmRepeat();
                        break;
                    case itemName4:
                        setAlarmTools.setRingtone();
                        break;
                    default:
                        break;
                }
            }
        });

        saveButton = findViewById(R.id.save_set_alarm);
        mTimePicker = findViewById(R.id.time_picker);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 23) {
                    Log.d("mytest", "SetAlarmActivity:" + mTimePicker.getHour()+":"+mTimePicker.getMinute());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //把选中的铃声更新到界面
        if (resultCode == RESULT_OK && requestCode == 1){
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Ringtone ringTitle = RingtoneManager.getRingtone(SetAlarmActivity.this,uri);
            mSetAlarmItems.get(3).setContent(ringTitle.getTitle(SetAlarmActivity.this));
            alarmAdapter.notifyDataSetChanged();

        } else {
            Log.d("mytest","获取失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    //初始化闹钟设置选项
    private void initSetAlarmItems() {
        SetAlarmItem item1 = new SetAlarmItem(itemName1, "自定义", R.drawable.arrow_right_gray);
        SetAlarmItem item2 = new SetAlarmItem(itemName2, "自定义", R.drawable.arrow_right_gray);
        SetAlarmItem item3 = new SetAlarmItem(itemName3, "自定义", R.drawable.arrow_right_gray);
        SetAlarmItem item4 = new SetAlarmItem(itemName4, "自定义", R.drawable.arrow_right_gray);

        mSetAlarmItems.add(item1);
        mSetAlarmItems.add(item2);
        mSetAlarmItems.add(item3);
        mSetAlarmItems.add(item4);
    }
}

package com.example.myalarmclock;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SetAlarmActivity extends AppCompatActivity {

    private List<SetAlarmItem> mSetAlarmItems = new ArrayList<>();
    private final String itemName1 = "闹钟标题";
    private final String itemName2 = "响铃日期";
    private final String itemName3 = "重复";
    private final String itemName4 = "铃声设置";
    private AlertDialog mDialog;
    private MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        initSetAlarmItems();
        SetAlarmAdapter alarmAdapter = new SetAlarmAdapter(SetAlarmActivity.this, R.layout.setalarm_item, mSetAlarmItems);
        ListView listView = findViewById(R.id.set_alarm_listview);
        listView.setAdapter(alarmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SetAlarmItem alarmItem = mSetAlarmItems.get(position);
                //创建AlertDialog对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(SetAlarmActivity.this);
                SetAlarmTools setAlarmTools = new SetAlarmTools(SetAlarmActivity.this,builder);


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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1){
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Log.d("mytest",""+uri);
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

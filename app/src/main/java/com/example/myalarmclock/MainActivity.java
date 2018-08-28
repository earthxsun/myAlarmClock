package com.example.myalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.myalarmclock.Db.Alarm;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AlarmItem> mAlarmItemList = new ArrayList<>();

    private AlarmAdapter mAlarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitData.initAlarmItem(mAlarmItemList);
        mAlarmAdapter = new AlarmAdapter(MainActivity.this, R.layout.alarm_list, mAlarmItemList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(mAlarmAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm alarm = Alarm.getAlarminstance();
                Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
                intent.putExtra("alarm_db", alarm);
                startActivityForResult(intent,2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAlarmItemList.clear();
        InitData.initAlarmItem(mAlarmItemList);
        mAlarmAdapter.notifyDataSetChanged();
        Log.d("mytest","szie:"+mAlarmAdapter.getCount());
    }
}

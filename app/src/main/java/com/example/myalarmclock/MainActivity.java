package com.example.myalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.myalarmclock.Db.Alarm;
import com.example.myalarmclock.Db.AlarmlistAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AlarmItem> mAlarmItemList = new ArrayList<>();

    private AlarmlistAdapter mAlarmlistAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化闹钟列表项
        InitData.initAlarmItem(mAlarmItemList);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAlarmlistAdapter = new AlarmlistAdapter(mAlarmItemList);
        recyclerView.setAdapter(mAlarmlistAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm alarm = Alarm.getAlarminstance();
                Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
                intent.putExtra("alarm_db", alarm);
                intent.putExtra("new",true);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            mAlarmItemList.clear();
            InitData.initAlarmItem(mAlarmItemList);
            mAlarmlistAdapter.notifyDataSetChanged();
        } else {
            Log.d("mytest", "返回失败");
        }
    }
}

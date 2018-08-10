package com.example.myalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AlarmItem> mAlarmItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAlarmItem();
        AlarmAdapter adapter = new AlarmAdapter(MainActivity.this,R.layout.alarm_list,mAlarmItemList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SetAlarmActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initAlarmItem() {
        for (int i = 0 ;i< 20;i++){
            if (i%2==0){
                mAlarmItemList.add(new AlarmItem("07:55","法定工作日",true));
            } else {
                mAlarmItemList.add(new AlarmItem("08:55","星期一，星期二",false));
            }
        }
    }
}

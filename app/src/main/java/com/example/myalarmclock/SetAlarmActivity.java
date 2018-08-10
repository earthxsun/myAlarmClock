package com.example.myalarmclock;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SetAlarmActivity extends AppCompatActivity {

    private List<SetAlarmItem> mSetAlarmItems = new ArrayList<>();
    private final String itemName1 = "闹钟标题";
    private final String itemName2 = "响铃日期";
    private final String itemName3 = "重复";
    private final String itemName4 = "铃声设置";
    private AlertDialog mDialog;

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
                final Window window;
                SetAlarmItem alarmItem = mSetAlarmItems.get(position);
                //创建AlertDialog对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(SetAlarmActivity.this);

                //获取AlertDialog对话框样式ID
                View input_Alarm_Name = LayoutInflater.from(SetAlarmActivity.this).inflate(R.layout.input_alarm_name, null);
                final View selectAlarmDate = LayoutInflater.from(SetAlarmActivity.this).inflate(R.layout.select_alarm_date,null);

                //根据设置项的名称来判断
                switch (alarmItem.getTitle()) {
                    case itemName1:
                        final EditText editText = input_Alarm_Name.findViewById(R.id.alarm_name);
                        builder.setView(input_Alarm_Name)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("mytest", "闹钟名称" + editText.getText());
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        mDialog = builder.create();
                        //设置对话框为圆角
                        window = mDialog.getWindow();
                        window.setBackgroundDrawableResource(R.drawable.corners_style);
                        mDialog.show();
                        break;
                    case itemName2:
                        builder.setView(selectAlarmDate)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        mDialog = builder.create();
                        window = mDialog.getWindow();
                        window.setBackgroundDrawableResource(R.drawable.corners_style);
                        mDialog.show();
                        break;
                    case itemName3:
                        final String[] items = new String[]{"单次","周一到周五","法定工作日","每天","自定义"};
                        //设置标题样式
                        TextView title = new TextView(SetAlarmActivity.this);
                        title.setText("重复");
                        title.setPadding(10,15,10,10);
                        title.setGravity(Gravity.CENTER);
                        title.setTextSize(30);
                        title.setBackgroundResource(R.drawable.input_style);

                        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDialog.dismiss();
                                switch (which){
                                    case 4:
                                        AlertDialog.Builder builder1 = customDateDialog(SetAlarmActivity.this);
                                        AlertDialog alertDialog = builder1.create();
                                        Window mDialogWindow = alertDialog.getWindow();
                                        mDialogWindow.setBackgroundDrawableResource(R.drawable.corners_style);
                                        alertDialog.show();
                                }
                            }
                        }).setCustomTitle(title);
                        mDialog = builder.create();
                        window = mDialog.getWindow();
                        window.setBackgroundDrawableResource(R.drawable.corners_style);
                        mDialog.show();
                        break;
                    case itemName4:
                        Log.d("mytest", itemName4);
                        break;
                    default:
                        break;
                }
            }
        });
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

    //创建自定义日期对话框
    private AlertDialog.Builder customDateDialog(Context context){
        final String[] items = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
        TextView textView = new TextView(context);
        textView.setText("自定义");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setBackgroundResource(R.drawable.input_style);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCustomTitle(textView);
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder;
    }
}

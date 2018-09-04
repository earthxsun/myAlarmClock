package com.example.myalarmclock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myalarmclock.Db.Alarm;

import java.util.List;

public class SetAlarmTools {

    private AppCompatActivity mActivity;
    private Context mContext;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;
    private Window window;
    private List<SetAlarmItem> mSetAlarmItems;
    private SetAlarmAdapter mSetAlarmAdapter;
    private int mPosition;
    private Alarm mAlarm;


    public SetAlarmTools(Context context, AlertDialog.Builder builder, List<SetAlarmItem> setAlarmitems,
                         SetAlarmAdapter setAlarmAdapter, Alarm alarm, int position) {
        mContext = context;
        mBuilder = builder;
        mSetAlarmItems = setAlarmitems;
        mSetAlarmAdapter = setAlarmAdapter;
        mPosition = position;
        mAlarm = alarm;
    }

    //设置闹钟名称
    public void setAlarmName() {
        //获取AlertDialog对话框
        View input_Alarm_Name = LayoutInflater.from(mContext).inflate(R.layout.input_alarm_name, null);
        final EditText editText = input_Alarm_Name.findViewById(R.id.alarm_name);
        mBuilder.setView(input_Alarm_Name)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlarm.setTitle(editText.getText().toString());
                        mSetAlarmItems.get(mPosition).setContent(editText.getText().toString());
                        mSetAlarmAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mDialog = mBuilder.create();
        //设置对话框为圆角
        window = mDialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.corners_style);
        mDialog.show();
    }

    //设置闹钟日期
    public void setAlarmDate() {
        //获取AlertDialog对话框
        View selectAlarmDate = LayoutInflater.from(mContext).inflate(R.layout.select_alarm_date, null);
        final DatePicker datePicker = selectAlarmDate.findViewById(R.id.alarm_date_select);
        mBuilder.setView(selectAlarmDate)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String alarmDate = datePicker.getYear() + "年" + (datePicker.getMonth() + 1) + "月" + datePicker.getDayOfMonth() + "日";
                        mAlarm.setYear(datePicker.getYear());
                        mAlarm.setMonth(datePicker.getMonth() + 1);
                        mAlarm.setDay(datePicker.getDayOfMonth());
                        mSetAlarmItems.get(mPosition).setContent(alarmDate);
                        mSetAlarmAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mDialog = mBuilder.create();
        window = mDialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.corners_style);
        mDialog.show();
    }

    //设置闹钟重复响铃
    public void setAlarmRepeat() {

        //设置标题样式
        TextView title = new TextView(mContext);
        title.setText("重复");
        title.setPadding(10, 15, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setBackgroundResource(R.drawable.input_style);

        mBuilder.setSingleChoiceItems(InitData.items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog.dismiss();
                switch (which) {
                    case 0:
                        mAlarm.setOnce(true);
                        mSetAlarmItems.get(mPosition).setContent(InitData.items[which]);
                        mSetAlarmAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        mAlarm.setMonday(true);
                        mAlarm.setTuesday(true);
                        mAlarm.setWednesday(true);
                        mAlarm.setThursday(true);
                        mAlarm.setFriday(true);
                        mSetAlarmItems.get(mPosition).setContent(InitData.items[which]);
                        mSetAlarmAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        mAlarm.setStatutoryholiday(true);
                        mSetAlarmItems.get(mPosition).setContent(InitData.items[which]);
                        mSetAlarmAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        mAlarm.setEveryday(true);
                        mSetAlarmItems.get(mPosition).setContent(InitData.items[which]);
                        mSetAlarmAdapter.notifyDataSetChanged();
                        break;
                    case 4:
                        AlertDialog.Builder builder1 = customDateDialog(mContext);
                        AlertDialog alertDialog = builder1.create();
                        Window mDialogWindow = alertDialog.getWindow();
                        mDialogWindow.setBackgroundDrawableResource(R.drawable.corners_style);
                        alertDialog.show();
                        break;
                    default:
                        break;
                }
            }
        }).setCustomTitle(title);
        mDialog = mBuilder.create();
        window = mDialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.corners_style);
        mDialog.show();
    }

    //创建自定义日期对话框
    private AlertDialog.Builder customDateDialog(Context context) {
        final String[] items = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        final boolean[] isSelected = {false, false, false, false, false, false, false};
        TextView textView = new TextView(context);
        textView.setText("自定义");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setBackgroundResource(R.drawable.input_style);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCustomTitle(textView);
        //设置多选
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                //根据点击选项把值付给isSelected数组
                isSelected[which] = isChecked;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();
                //根据数据isSelected来判断选项是否被选上
                for (int i = 0; i < isSelected.length; i++) {
                    if (isSelected[i]) {
                        stringBuilder.append(items[i]);
                        stringBuilder.append(" ");
                    }
                }
                String alarmRepeat = stringBuilder.toString();
                if (alarmRepeat.equals("周一 周二 周三 周四 周五 ")) {
                    alarmRepeat = "周一到周五";
                }

                if (alarmRepeat.equals("周日 周一 周二 周三 周四 周五 周六 ")) {
                    alarmRepeat = "每天";
                }
                mAlarm.setSunday(isSelected[0]);
                mAlarm.setMonday(isSelected[1]);
                mAlarm.setTuesday(isSelected[2]);
                mAlarm.setWednesday(isSelected[3]);
                mAlarm.setThursday(isSelected[4]);
                mAlarm.setFriday(isSelected[5]);
                mAlarm.setSaturday(isSelected[6]);

                mSetAlarmItems.get(mPosition).setContent(alarmRepeat);
                mSetAlarmAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder;
    }
    //调用系统铃声选择界面
    public void setRingtone() {
        mActivity = (AppCompatActivity) mContext;
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹钟铃声");
        mActivity.startActivityForResult(intent, 1);
    }
}

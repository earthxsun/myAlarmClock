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


    public SetAlarmTools(Context context, AlertDialog.Builder builder, List<SetAlarmItem> setAlarmitems,
                         SetAlarmAdapter setAlarmAdapter, int position) {
        mContext = context;
        mBuilder = builder;
        mSetAlarmItems = setAlarmitems;
        mSetAlarmAdapter = setAlarmAdapter;
        mPosition = position;
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
        final String[] items = new String[]{"单次", "周一到周五", "法定工作日", "每天", "自定义"};
        //设置标题样式
        TextView title = new TextView(mContext);
        title.setText("重复");
        title.setPadding(10, 15, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setBackgroundResource(R.drawable.input_style);

        mBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog.dismiss();
                switch (which) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        mSetAlarmItems.get(mPosition).setContent(items[which]);
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
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                isSelected[which] = isChecked;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < isSelected.length; i++) {
                    if (isSelected[i]) {
                        stringBuilder.append(items[i]);
                        stringBuilder.append(" ");
                    }
                }
                mSetAlarmItems.get(mPosition).setContent(stringBuilder.toString());
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

    public void setRingtone() {
        mActivity = (AppCompatActivity) mContext;
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹钟铃声");
        mActivity.startActivityForResult(intent, 1);
    }


}

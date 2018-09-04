package com.example.myalarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class AlarmAdapter extends ArrayAdapter<AlarmItem> {

    private int resourceId;

    private List<AlarmItem> mAlarmItemList;

    public AlarmAdapter (Context context, int textViewResourceId, List<AlarmItem> alarmItemList){
        super(context,textViewResourceId,alarmItemList);
        resourceId = textViewResourceId;
        mAlarmItemList = alarmItemList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final AlarmItem alarmItem = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.alarmName = view.findViewById(R.id.alarm_name);
            viewHolder.alarmTime = view.findViewById(R.id.alarm_time);
            viewHolder.alarmDate = view.findViewById(R.id.alarm_date);
            viewHolder.alarmRepeat = view.findViewById(R.id.alarm_repeat);
            viewHolder.alarmSwitch = view.findViewById(R.id.alarm_switch);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.alarmName.setText(alarmItem.getAlarmName());
        viewHolder.alarmTime.setText(alarmItem.getAlarmtime());
        viewHolder.alarmDate.setText(alarmItem.getAlarmdate());
        viewHolder.alarmRepeat.setText(alarmItem.getAlarmRepeat());
        viewHolder.alarmSwitch.setOnCheckedChangeListener(null);//清空监听，否则上下滑动会再次改变switch的状态
        viewHolder.alarmSwitch.setChecked(alarmItem.getOpen());
        //监听用户点击闹钟开关并写入数据库
        viewHolder.alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues values = new ContentValues();
                if (isChecked){
                    values.put("isOpen",true);
                } else {
                    values.put("isOpen",false);
                }
                Log.d("mytest","mAlarmItemList size:"+mAlarmItemList.size());
                Log.d("mytest","点击了"+position);
//                AlarmItem alarmItem1 = mAlarmItemList.get(position);
//                LitePal.update(Alarm.class,values,alarmItem1.getAlarmId());
//                //刷新闹钟列表
//                mAlarmItemList.clear();
//                InitData.initAlarmItem(mAlarmItemList);
//                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class ViewHolder {
        TextView alarmName;

        TextView  alarmTime;

        TextView alarmDate;

        TextView alarmRepeat;

        Switch alarmSwitch;
    }
}

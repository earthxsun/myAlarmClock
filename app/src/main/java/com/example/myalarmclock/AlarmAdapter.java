package com.example.myalarmclock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class AlarmAdapter extends ArrayAdapter<AlarmItem> {

    private int resourceId;

    public AlarmAdapter (Context context, int textViewResourceId, List<AlarmItem> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AlarmItem alarmItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.alarmTime = view.findViewById(R.id.alarm_time);
            viewHolder.alarmDate = view.findViewById(R.id.alarm_date);
            viewHolder.alarmSwitch = view.findViewById(R.id.alarm_switch);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.alarmTime.setText(alarmItem.getAlarmtime());
        viewHolder.alarmDate.setText(alarmItem.getAlarmdate());
        viewHolder.alarmSwitch.setChecked(alarmItem.getOpen());
        return view;
    }

    private class ViewHolder {
        TextView  alarmTime;

        TextView alarmDate;

        Switch alarmSwitch;
    }
}

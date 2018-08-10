package com.example.myalarmclock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SetAlarmAdapter extends ArrayAdapter<SetAlarmItem> {

    private int resourceId;

    public SetAlarmAdapter(@NonNull Context context, int resource, @NonNull List<SetAlarmItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SetAlarmItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView title = view.findViewById(R.id.set_alarm_title);
        TextView content = view.findViewById(R.id.set_alarm_content);
        ImageView imageId = view.findViewById(R.id.set_alarm_image);
        title.setText(item.getTitle());
        content.setText(item.getContent());
        imageId.setImageResource(item.getImageId());
        return view;
    }
}

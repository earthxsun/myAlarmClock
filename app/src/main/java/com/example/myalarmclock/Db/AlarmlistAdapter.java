package com.example.myalarmclock.Db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myalarmclock.AlarmItem;
import com.example.myalarmclock.R;
import com.example.myalarmclock.SetAlarmActivity;

import org.litepal.LitePal;

import java.util.List;

public class AlarmlistAdapter extends RecyclerView.Adapter<AlarmlistAdapter.ViewHolder> {

    private List<AlarmItem> mAlarmItemList;
    private AlarmItem mAlarmItem;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View alarmListView;
        TextView alarmDate;
        TextView alarmTime;
        TextView alarmName;
        TextView alarmRepeat;
        Switch alarmSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            alarmListView = itemView;
            alarmName = itemView.findViewById(R.id.alarm_name);
            alarmDate = itemView.findViewById(R.id.alarm_date);
            alarmTime = itemView.findViewById(R.id.alarm_time);
            alarmRepeat = itemView.findViewById(R.id.alarm_repeat);
            alarmSwitch = itemView.findViewById(R.id.alarm_switch);
        }
    }

    public AlarmlistAdapter(List<AlarmItem> alarmItemList) {
        mAlarmItemList = alarmItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        final MenuItem.OnMenuItemClickListener menuItemClickListener;
        //单击闹钟项进行编辑修改
        holder.alarmListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(parent.getContext(), SetAlarmActivity.class);
                mAlarmItem = mAlarmItemList.get(position);
                Alarm alarm = LitePal.find(Alarm.class, mAlarmItem.getAlarmId());
                intent.putExtra("alarm_db", alarm);
                intent.putExtra("new",false);
                Activity activity = (Activity) parent.getContext();
                activity.startActivityForResult(intent, 3);
            }
        });

        //设置长按闹钟项弹出删除按钮监听器
        menuItemClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int position = holder.getAdapterPosition();
                mAlarmItem = mAlarmItemList.get(position);
                LitePal.delete(Alarm.class, mAlarmItem.getAlarmId());
                mAlarmItemList.remove(position);
                notifyDataSetChanged();
                return true;
            }
        };
        //绑定弹出删除按钮监听器
        holder.alarmListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuItem delete = menu.add(Menu.NONE, 1, 0, "删除");
                delete.setOnMenuItemClickListener(menuItemClickListener);
            }
        });

        //设置闹钟开关监听
        holder.alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = holder.getAdapterPosition();
                mAlarmItem = mAlarmItemList.get(position);
                Alarm alarm = LitePal.find(Alarm.class, mAlarmItem.getAlarmId());
                //检测开关按钮是否被点击
                if (buttonView.isPressed()) {
                    ContentValues values = new ContentValues();
                    values.put("isOpen", isChecked);
                    LitePal.update(Alarm.class, values, alarm.getId());
                    mAlarmItem.setOpen(isChecked);
                    notifyDataSetChanged();
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlarmItem alarmItem = mAlarmItemList.get(position);
        holder.alarmName.setText(alarmItem.getAlarmName());
        holder.alarmDate.setText(alarmItem.getAlarmdate());
        holder.alarmTime.setText(alarmItem.getAlarmtime());
        holder.alarmRepeat.setText(alarmItem.getAlarmRepeat());
        holder.alarmSwitch.setChecked(alarmItem.getOpen());
    }

    @Override
    public int getItemCount() {
        return mAlarmItemList.size();
    }
}

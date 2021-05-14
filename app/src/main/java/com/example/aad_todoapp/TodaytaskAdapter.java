package com.example.aad_todoapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class TodaytaskAdapter extends RecyclerView.Adapter<TodaytaskAdapter.ViewHolder>{
    List<TaskEntity> tasklist=new ArrayList<>();
    onClickListener listener;
    Context context;
    int posi=0;
    TodaytaskAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.todaytasklayout,parent,false);
        //context=parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // for pagged list
        TaskEntity taskEntity=tasklist.get(position);
            holder.task_due.setText(taskEntity.getTask_due());
            holder.task_title.setText(taskEntity.getTask_desc());
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setAlarm set = new setAlarm(context.getApplicationContext());
                    Log.i("checkbox", String.valueOf(position));
                    if (holder.checkBox.isChecked()) {
                        MainActivity.completedTask(taskEntity.getId(), taskEntity.getTask_desc()
                                , taskEntity.getTask_priority(), taskEntity.getTask_due(), holder.checkBox.isChecked(), taskEntity.getDue_day()
                                , taskEntity.getDue_month(), taskEntity.getDue_year());
                        set.cancleRemainder(taskEntity.getId());
                    } else {
                        MainActivity.completedTask(taskEntity.getId(), taskEntity.getTask_desc()
                                , taskEntity.getTask_priority(), taskEntity.getTask_due(), holder.checkBox.isChecked(), taskEntity.getDue_day()
                                , taskEntity.getDue_month(), taskEntity.getDue_year());
                        Calendar current_calendar = Calendar.getInstance();

                        Calendar updated_calendar = Calendar.getInstance();
                        updated_calendar.set(Calendar.HOUR_OF_DAY, 10);
                        updated_calendar.set(Calendar.MINUTE, 10);
                        updated_calendar.set(Calendar.SECOND, 0);
                        updated_calendar.set(Calendar.MONTH, taskEntity.getDue_month());
                        updated_calendar.set(Calendar.YEAR, taskEntity.getDue_year());
                        updated_calendar.set(Calendar.DAY_OF_MONTH, taskEntity.getDue_day());
                        if (updated_calendar.after(current_calendar)) {
                            set.setTask(taskEntity.getId(), taskEntity.getTask_priority(), taskEntity.getTask_desc()
                                    , taskEntity.getTask_due(), taskEntity.getDue_day(), taskEntity.getDue_month(), taskEntity.getDue_year());
                        }
                    }
                }
            });

            switch (taskEntity.getTask_priority()) {
                case "High":
                    holder.priority_image.setImageResource(R.drawable.ic_high);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        holder.cardView.setCardBackgroundColor(context.getColor(R.color.hig));
                    }
                    break;
                case "Medium":
                    holder.priority_image.setImageResource(R.drawable.ic_medium);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        holder.cardView.setCardBackgroundColor(context.getColor(R.color.med));
                    }
                    break;
                case "Low":
                    holder.priority_image.setImageResource(R.drawable.ic_low);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        holder.cardView.setCardBackgroundColor(context.getColor(R.color.low));
                    }
                    break;
            }


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, String.valueOf(taskEntity.getId()), Toast.LENGTH_SHORT).show();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.itemClick(taskEntity);
                    }
                }
            });
            if (taskEntity.getCompleted() == 1) {
                posi = position;
//                holder.task_due.setTextColor(Color.rgb(182, 182, 182));
//                holder.task_title.setTextColor(Color.rgb(147, 147, 147));
//                holder.checkBox.setChecked(true);
//                holder.priority_image.setImageResource(R.drawable.ic_completed);
                if(holder.cardView.getVisibility()==View.VISIBLE) {
                    holder.cardView.setVisibility(View.GONE);
                }
            } else {
                holder.checkBox.setChecked(false);
                if(holder.cardView.getVisibility()==View.GONE) {
                    holder.cardView.setVisibility(View.VISIBLE);
                }
            }
       // }
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    public void showTask(List<TaskEntity> tasklist){
        // this.tasklist=tasklist;
        this.tasklist=tasklist;
        notifyDataSetChanged();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView task_title,task_due;
        ImageView priority_image;
        CheckBox checkBox;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

                task_title = itemView.findViewById(R.id.tod_title);
                task_due = itemView.findViewById(R.id.tod_due);
                checkBox = itemView.findViewById(R.id.todtask_checkbox);
                priority_image = itemView.findViewById(R.id.tod_priority);
                cardView = itemView.findViewById(R.id.todcard);


        }
    }
    public interface onClickListener{
        void itemClick(TaskEntity taskEntity);
    }
    public void onItemClicked(onClickListener listener){
        this.listener=listener;
    }
}


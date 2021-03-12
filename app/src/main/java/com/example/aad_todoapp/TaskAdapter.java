package com.example.aad_todoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    List<TaskEntity> tasklist=new ArrayList<>();
    onClickListener listener;
    Context context;
    int posi=0;
    TaskAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.taskholderlayout,parent,false);
        //context=parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskEntity taskEntity=tasklist.get(position);
        holder.task_prority.setText(taskEntity.getTask_priority());
        holder.task_due.setText(taskEntity.getTask_due());
        holder.task_desc.setText(taskEntity.getTask_desc());
        holder.cardView.setBackgroundResource(R.drawable.taskcard);
        holder.task_prority.setTextColor(Color.rgb(99,99,99));
        holder.task_due.setTextColor(Color.rgb(99,99,99));
        holder.task_desc.setTextColor(Color.rgb(0,0,0));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm set = new setAlarm(context.getApplicationContext());
                if(holder.checkBox.isChecked()){
                    MainActivity.completedTask(taskEntity.getId(), taskEntity.getTask_desc()
                            , taskEntity.getTask_priority(), taskEntity.getTask_due(),holder.checkBox.isChecked(),taskEntity.getDue_day()
                            ,taskEntity.getDue_month(),taskEntity.getDue_year());
                    set.cancleRemainder(taskEntity.getId());
                }
                else {
                    MainActivity.completedTask(taskEntity.getId(), taskEntity.getTask_desc()
                            , taskEntity.getTask_priority(), taskEntity.getTask_due(),holder.checkBox.isChecked(),taskEntity.getDue_day()
                            ,taskEntity.getDue_month(),taskEntity.getDue_year());
                    set.setTask(taskEntity.getId(),taskEntity.getTask_priority(),taskEntity.getTask_desc()
                            ,taskEntity.getTask_due(),taskEntity.getDue_day(),taskEntity.getDue_month(),taskEntity.getDue_year());
                }
            }
        });

        switch (taskEntity.getTask_priority()) {
            case "High":
                holder.priority_image.setImageResource(R.drawable.ic_high);
                break;
            case "Medium":
                holder.priority_image.setImageResource(R.drawable.ic_medium);
                break;
            case "Low":
                holder.priority_image.setImageResource(R.drawable.ic_low);
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
        if(taskEntity.getCompleted()==1) {
            posi=position;
            holder.cardView.setBackgroundResource(R.drawable.completed_task);
            holder.task_due.setTextColor(Color.rgb(182,182,182));
            holder.task_desc.setTextColor(Color.rgb(147,147,147));
            holder.task_prority.setTextColor(Color.rgb(182,182,182));
            holder.checkBox.setChecked(true);
            holder.priority_image.setImageResource(R.drawable.ic_completed);
            holder.task_prority.setText("Completed");
        }
    }

    public void showTask(List<TaskEntity> tasklist){
        this.tasklist=tasklist;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView task_desc,task_prority,task_due;
        ImageView priority_image;
        CheckBox checkBox;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_desc=itemView.findViewById(R.id.rec_taskdesc);
            task_due=itemView.findViewById(R.id.rec_taskdue);
            task_prority=itemView.findViewById(R.id.rec_taskprior);
            checkBox=itemView.findViewById(R.id.task_checkbox);
            priority_image=itemView.findViewById(R.id.rec_preimage);
            cardView=itemView.findViewById(R.id.cardview);

        }
    }
    public interface onClickListener{
        void itemClick(TaskEntity taskEntity);
    }
    public void onItemClicked(onClickListener listener){
        this.listener=listener;
    }
}

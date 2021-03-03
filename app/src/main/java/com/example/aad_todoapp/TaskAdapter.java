package com.example.aad_todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    List<TaskEntity> tasklist=new ArrayList<>();
    onClickListener listener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.taskholderlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskEntity taskEntity=tasklist.get(position);
        holder.task_prority.setText(taskEntity.getTask_priority());
        holder.task_due.setText(taskEntity.getTask_due());
        holder.task_desc.setText(taskEntity.getTask_desc());
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
            case "Completed":
                holder.priority_image.setImageResource(R.drawable.ic_completed);
                break;
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.itemClick(taskEntity);
                }
            }
        });
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
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_desc=itemView.findViewById(R.id.rec_taskdesc);
            task_due=itemView.findViewById(R.id.rec_taskdue);
            task_prority=itemView.findViewById(R.id.rec_taskprior);
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

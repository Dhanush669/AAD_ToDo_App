package com.example.aad_todoapp;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.aad_todoapp.MainActivity.task_viewModel;
import static com.example.aad_todoapp.Notification_Channel.Notification_ID;

public class TaskReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id=intent.getIntExtra("priority",R.drawable.ic_launcher_background);
        int notiid=intent.getIntExtra("noti_id",1);
        String taskdesc=intent.getStringExtra("task_desc");
        String taskdue=intent.getStringExtra("task_due");

        Notification notification=new NotificationCompat.Builder(context,Notification_ID)
                .setContentText("Task Alter")
                .setContentTitle("Task Alert Notification")
                .setSmallIcon(id)
                .build();
        NotificationManagerCompat compat=NotificationManagerCompat.from(context);
        compat.notify(notiid,notification);
        TaskEntity taskEntity=new TaskEntity(taskdesc,"Completed",taskdue,1);
        taskEntity.setId(notiid);
        task_viewModel.update(taskEntity);
    }
}

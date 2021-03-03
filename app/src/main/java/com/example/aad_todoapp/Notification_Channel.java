package com.example.aad_todoapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Notification_Channel extends Application {
    public static final String Notification_ID="NOTIFICATION_ID";

    @Override
    public void onCreate() {
        super.onCreate();

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O) {
            createNotification();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotification(){
        NotificationChannel notificationChannel=new NotificationChannel(Notification_ID,"Task Notification", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("Task Notification");
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        NotificationManager manager=getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel);
    }
}

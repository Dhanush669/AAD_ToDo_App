package com.example.aad_todoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.aad_todoapp.AddTask.day;
import static com.example.aad_todoapp.AddTask.month;
import static com.example.aad_todoapp.AddTask.year;

public class setAlarm {
    AlarmManager alarmManager;
    Context context;
    int duemonth,dueday,dueyear;
    setAlarm(Context context){
        this.context=context;
        alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    public  void setTask(int notiid,String priority,String taskdesc,String taskdue){
        int proirityid=0;
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,2);
        calendar.set(Calendar.MINUTE,24);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.YEAR,year);
        //calendar.set(Calendar.AM_PM,Calendar.PM);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        duemonth=month;
        dueday=day;
        dueyear=year;
        switch (priority) {
            case "High":
                proirityid = R.drawable.ic_high;
                break;
            case "Medium":
                proirityid = R.drawable.ic_medium;
                break;
            case "Low":
                proirityid = R.drawable.ic_low;
                break;
        }
        Intent intent=new Intent(context,TaskReceiver.class);
        intent.putExtra("priority",proirityid);
        intent.putExtra("noti_id",notiid);
        intent.putExtra("task_desc",taskdesc);
        intent.putExtra("task_due",taskdue);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,notiid,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }
    public void cancleRemainder(int id){


        Intent intent=new Intent(context,TaskReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

    }
}

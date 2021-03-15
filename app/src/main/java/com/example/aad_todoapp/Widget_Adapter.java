package com.example.aad_todoapp;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class Widget_Adapter extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("ongetViewFacory","i am here");
        return new Widget_Factory(getApplicationContext(),intent);
    }
    class Widget_Factory implements RemoteViewsFactory{
        private Task_Dao task_dao;
        private List<TaskEntity> all_task;
        private final Context context;
        private int widget_id;
        Widget_Factory(Context context,Intent intent){
            this.context=context;
            this.widget_id=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,AppWidgetManager.INVALID_APPWIDGET_ID);
            Task_Room task_room=Task_Room.getInstance(context);
            Log.i("ongetViewFacory","i am here constructor");

            task_dao=task_room.task_dao();
        }
        @Override
        public void onCreate() {
            Log.i("oncreate","on create");
            all_task=MainActivity.widget_data;


        }

        @Override
        public void onDataSetChanged() {
            all_task=MainActivity.widget_data;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return all_task.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            Log.i("ongetViewFacory","i am here get View");
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.task_widget_container);
            views.setTextViewText(R.id.widget_desc, all_task.get(i).getTask_desc());
            Log.i("getViewAt", all_task.get(i).getTask_desc());
            views.setTextViewText(R.id.widget_due, all_task.get(i).getTask_due());
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}

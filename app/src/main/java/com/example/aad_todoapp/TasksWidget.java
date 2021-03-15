package com.example.aad_todoapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class TasksWidget extends AppWidgetProvider {
    static int widit=0;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        widit=appWidgetId;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tasks_widget);
        //Adapter for widgets
        Intent intent=new Intent(context, Widget_Adapter.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetId);
        views.setRemoteAdapter(R.id.task_items,intent);
        views.setEmptyView(R.id.task_items, R.id.example_widget_empty_view);
        Intent clickintent=new Intent(context,AddTask.class);
        PendingIntent clickpendingintent=PendingIntent.getActivity(context,0,clickintent,0);
        views.setPendingIntentTemplate(R.id.task_items,clickpendingintent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
       // appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.task_items);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
package com.example.aad_todoapp;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TaskEntity.class,version = 1)
abstract public class Task_Room extends RoomDatabase {
    private static Task_Room instance;

    public abstract Task_Dao task_dao();

    public static synchronized Task_Room getInstance(Context context){
        if(instance==null){
            instance=Room.databaseBuilder(context,Task_Room.class,"Task_Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

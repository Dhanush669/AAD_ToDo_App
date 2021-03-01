package com.example.aad_todoapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Task_Dao {
    @Insert
    public void Insert(TaskEntity taskEntity);

    @Update
    public void Update(TaskEntity taskEntity);

    @Delete
    public void Delete(TaskEntity taskEntity);

    @Query("DELETE FROM TaskEntity")
    public void DeleteAllTask();

    @Query("SELECT * FROM TaskEntity")
    LiveData<List<TaskEntity>> GetAllData();
}
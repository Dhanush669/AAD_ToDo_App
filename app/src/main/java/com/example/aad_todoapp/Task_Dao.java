package com.example.aad_todoapp;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
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

    @Query("SELECT * FROM TaskEntity ORDER BY Completed")
    LiveData<List<TaskEntity>> GetAllTask();

    //pagination query
    @Query("SELECT * FROM TaskEntity ORDER BY completed")
    DataSource.Factory<Integer,TaskEntity> pagination();

    @Query("SELECT * FROM TaskEntity WHERE due_day>:due AND due_day<:due+7 ORDER BY completed")
    LiveData<List<TaskEntity>> getThisWeekTask(int due);
}

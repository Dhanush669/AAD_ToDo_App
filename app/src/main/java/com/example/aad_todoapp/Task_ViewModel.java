package com.example.aad_todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

public class Task_ViewModel extends AndroidViewModel {
    Task_Repo task_repo;
    LiveData<List<TaskEntity>> allTask;
    //pagination tasks
    LiveData<PagedList<TaskEntity>> pagitask;
    LiveData<List<TaskEntity>> thisweektask;
    public Task_ViewModel(@NonNull Application application) {
        super(application);
        task_repo=new Task_Repo(application);
        allTask=task_repo.getAllTask();
        pagitask=task_repo.init();
    }

    LiveData<List<TaskEntity>> getThisweektask(int due){
        return thisweektask=task_repo.thisweektasklist(due);
    }

    public void add(TaskEntity taskEntity){
        task_repo.addTask(taskEntity);
    }
    public void update(TaskEntity taskEntity){
        task_repo.updateTask(taskEntity);
    }
    public void delete(TaskEntity taskEntity){
        task_repo.deleteTask(taskEntity);
    }
    public void deleteAll(){
        task_repo.deleteAllTask();
    }
    LiveData<List<TaskEntity>> getAllTask(){
        return allTask;
    }
    LiveData<PagedList<TaskEntity>> getPagitask(){
        return pagitask;
    }
}

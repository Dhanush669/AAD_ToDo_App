package com.example.aad_todoapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

public class Task_Repo {
    Task_Dao task_dao;
    //normal alltask
    LiveData<List<TaskEntity>> allTask;
    LiveData<List<TaskEntity>> thisweaktask;

    //pagination alltask
    LiveData<PagedList<TaskEntity>> pagialltask;

    Task_Repo(Application application){
       Task_Room task_room=Task_Room.getInstance(application);
        task_dao=task_room.task_dao();
        allTask=task_dao.GetAllTask();
    }

    public LiveData<List<TaskEntity>> thisweektasklist(int due){
        thisweaktask=task_dao.getThisWeekTask(due);
        return thisweaktask;
    }

    public void addTask(TaskEntity taskEntity){
        new Insert_AsyncTask(task_dao).execute(taskEntity);
    }
    public void updateTask(TaskEntity taskEntity){
        new Update_AsyncTask(task_dao).execute(taskEntity);
    }
    public void deleteTask(TaskEntity taskEntity){
        new Delete_AsyncTask(task_dao).execute(taskEntity);
    }
    public void deleteAllTask(){
        new DeleteAll_AsyncTask(task_dao).execute();
    }
    private static class Insert_AsyncTask extends AsyncTask<TaskEntity,Void,Void>{
        Task_Dao task_dao;
        Insert_AsyncTask(Task_Dao task_dao){
            this.task_dao=task_dao;
        }
        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            task_dao.Insert(taskEntities[0]);
            return null;
        }
    }
    private static class Update_AsyncTask extends AsyncTask<TaskEntity,Void,Void>{
        Task_Dao task_dao;
        Update_AsyncTask(Task_Dao task_dao){
            this.task_dao=task_dao;
        }
        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            task_dao.Update(taskEntities[0]);
            return null;
        }
    }
    private static class Delete_AsyncTask extends AsyncTask<TaskEntity,Void,Void>{
        Task_Dao task_dao;
        Delete_AsyncTask(Task_Dao task_dao){
            this.task_dao=task_dao;
        }
        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            task_dao.Delete(taskEntities[0]);
            return null;
        }
    }
    private static class DeleteAll_AsyncTask extends AsyncTask<Void,Void,Void>{
        Task_Dao task_dao;
        DeleteAll_AsyncTask(Task_Dao task_dao){
            this.task_dao=task_dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            task_dao.DeleteAllTask();
            return null;
        }
    }
    LiveData<List<TaskEntity>> getAllTask(){
        return allTask;
    }

    public LiveData<PagedList<TaskEntity>> init(){
        PagedList.Config config=(new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(40)
                .setPageSize(40).build();
        pagialltask= new LivePagedListBuilder<>(task_dao.pagination(),config).build();
        return pagialltask;
    }
}

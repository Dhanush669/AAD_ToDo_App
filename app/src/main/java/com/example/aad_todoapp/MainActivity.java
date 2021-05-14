package com.example.aad_todoapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aad_todoapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    static Task_ViewModel task_viewModel;
    List<TaskEntity> tasklist;
    TaskAdapter taskAdapter;
    TodaytaskAdapter todaytaskAdapter;
    static List<TaskEntity> widget_data;
    List<TaskEntity> todaylist;
    List<Integer> ids;
    int size=0;
    static int duemonth=0,dueyear=0,dueday=0;
    static int taskid=0;
    setAlarm set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //to load data from where it left


        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        tasklist=new ArrayList<>();
        widget_data=new ArrayList<>();
        todaytaskAdapter=new TodaytaskAdapter(this);
        taskAdapter=new TaskAdapter(MainActivity.this);
        todaylist=new ArrayList<>();
        set=new setAlarm(getApplicationContext());
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.todaytaskrec.setLayoutManager(horizontalLayoutManagaer);


        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setHasFixedSize(false);
        taskAdapter.onItemClicked(new TaskAdapter.onClickListener() {
            @Override
            public void itemClick(TaskEntity taskEntity) {
                Intent intent=new Intent(MainActivity.this,AddTask.class);
                intent.putExtra("up_task_desc",taskEntity.getTask_desc());
                intent.putExtra("up_task_due",taskEntity.getTask_due());
                intent.putExtra("up_task_priority",taskEntity.getTask_priority());
                intent.putExtra("up_task_id",taskEntity.getId());
                intent.putExtra("up_due_day",taskEntity.getDue_day());
                intent.putExtra("up_due_month",taskEntity.getDue_month());
                intent.putExtra("up_due_year",taskEntity.getDue_year());
                startActivityForResult(intent,2);
            }
        });
        task_viewModel= new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(this.getApplication())).get(Task_ViewModel.class);

        //normal all task
//        task_viewModel.getAllTask().observe(this, new Observer<List<TaskEntity>>() {
//            @Override
//            public void onChanged(List<TaskEntity> taskEntities) {
//                taskAdapter.showTask(taskEntities);
//                widget_data=taskEntities;
//                AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(MainActivity.this);
//                Log.i("main act", String.valueOf(TasksWidget.widit)+"31");
//                Context context = getApplicationContext();
//                ComponentName name = new ComponentName(context, TasksWidget.class);
//                int [] widget_ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
//                appWidgetManager.notifyAppWidgetViewDataChanged(widget_ids,R.id.task_items);
//                ids=new ArrayList<>();
//                for (TaskEntity taskEntity:taskEntities){
//                    ids.add(taskEntity.getId());
//                }
//                size=taskEntities.size();
//                if(size!=0){
//                   size-=1;
////                    setTask(ids.get(size),taskEntities.get(size).getTask_priority(),taskEntities.get(size).getTask_desc(),
////                            taskEntities.get(size).getTask_due());
//                    taskid=ids.get(size);
//
//                }
//                Log.i("onchanged",String.valueOf(taskid));
//
//            }
//        });


        //pagination all task 2:06

//        task_viewModel.getPagitask().observe(this, new Observer<List<TaskEntity>>() {
//            @Override
//            public void onChanged(List<TaskEntity> taskEntities) {
//                taskAdapter.showTask(taskEntities);
//                widget_data=taskEntities;
//                AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(MainActivity.this);
//                Log.i("main act", String.valueOf(TasksWidget.widit)+"31");
//                Context context = getApplicationContext();
//                ComponentName name = new ComponentName(context, TasksWidget.class);
//                int [] widget_ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
//                appWidgetManager.notifyAppWidgetViewDataChanged(widget_ids,R.id.task_items);
//                ids=new ArrayList<>();
//                for (TaskEntity taskEntity:taskEntities){
//                    ids.add(taskEntity.getId());
//                }
//                size=taskEntities.size();
//                if(size!=0){
//                    Log.i("size",String.valueOf(ids.size()));
//                    size-=1;
////                    setTask(ids.get(size),taskEntities.get(size).getTask_priority(),taskEntities.get(size).getTask_desc(),
////                            taskEntities.get(size).getTask_due());
//                    taskid=ids.get(size);
//
//                }
//                Log.i("onchanged",String.valueOf(taskid));
//
//            }
//        });
//        for(int i=1;i<201;i++){
//            task_viewModel.add(new TaskEntity(String.valueOf(i),"High","12/12/12",0,12,12,2012));
//        }

        //check pagination
        task_viewModel.pagitask.observe(this, new Observer<PagedList<TaskEntity>>() {
            @Override
            public void onChanged(PagedList<TaskEntity> taskEntities) {
              // taskAdapter.showTask(taskEntities);

                taskAdapter.submitList(taskEntities);
                get_weeK_list();
            }
        });

        binding.recyclerview.setAdapter(taskAdapter);
        binding.todaytaskrec.setAdapter(todaytaskAdapter);

        binding.addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddTask.class);
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            String taskdesc=data.getStringExtra("task_Desc");
            String taskprior=data.getStringExtra("task_Proir");
            String taskdue=data.getStringExtra("task_Due");
            int dueday=0,duemonth=0,dueyear=0;
            dueday=data.getIntExtra("due_day",0);
            duemonth=data.getIntExtra("due_month",0);
            dueyear=data.getIntExtra("due_year",0);
            TaskEntity taskEntity=new TaskEntity(taskdesc,taskprior,taskdue,0,dueday,duemonth
            ,dueyear);
            task_viewModel.add(taskEntity);
            set.setTask(taskid+1,taskEntity.getTask_priority(),taskEntity.getTask_desc(),
                    taskEntity.getTask_due(),dueday,duemonth,dueyear);
        }
        else if(requestCode==2 && resultCode==RESULT_OK){

            String taskdesc=data.getStringExtra("task_Desc");
            String taskprior=data.getStringExtra("task_Proir");
            String taskdue=data.getStringExtra("task_Due");
            int dueday=0,duemonth=0,dueyear=0;
            dueday=data.getIntExtra("due_day",0);
            duemonth=data.getIntExtra("due_month",0);
            dueyear=data.getIntExtra("due_year",0);
            int id=data.getIntExtra("id",-1);
            setAlarm s=new setAlarm(getApplicationContext());
            s.cancleRemainder(id);
            if(id==-1){
                Toast.makeText(this, "Task can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            TaskEntity taskEntity=new TaskEntity(taskdesc,taskprior,taskdue,0,dueday,duemonth
            ,dueyear);
            taskEntity.setId(id);
            task_viewModel.update(taskEntity);
            Calendar current_calendar=Calendar.getInstance();
            Calendar updated_calendar=Calendar.getInstance();
            updated_calendar.set(Calendar.HOUR_OF_DAY,10);
            updated_calendar.set(Calendar.MINUTE,10);
            updated_calendar.set(Calendar.SECOND,0);
            updated_calendar.set(Calendar.MONTH,duemonth);
            updated_calendar.set(Calendar.YEAR,dueyear);
            updated_calendar.set(Calendar.DAY_OF_MONTH,dueday);
            Log.i("check",String.valueOf(dueday));
            if(updated_calendar.after(current_calendar)){
                set.setTask(taskEntity.getId(),taskEntity.getTask_priority(),taskEntity.getTask_desc(),
                                taskEntity.getTask_due(),dueday,duemonth,dueyear);
            }
            //  *** this one is doubt!! ***
//            if(dueday>current_day){
//                if(duemonth>=current_month){
//                    if(dueyear>=current_year){
//                        set.setTask(taskEntity.getId(),taskEntity.getTask_priority(),taskEntity.getTask_desc(),
//                                taskEntity.getTask_due(),dueday,duemonth,dueyear);
//                    }
//                }
//                else if(dueyear>current_year){
//                    set.setTask(taskEntity.getId(),taskEntity.getTask_priority(),taskEntity.getTask_desc(),
//                            taskEntity.getTask_due(),dueday,duemonth,dueyear);
//                }
//            }
//            else if(duemonth>current_month){
//                if(dueyear>=current_year){
//                    set.setTask(taskEntity.getId(),taskEntity.getTask_priority(),taskEntity.getTask_desc(),
//                            taskEntity.getTask_due(),dueday,duemonth,dueyear);
//                }
//            }
//            else if(dueyear>current_year){
//                set.setTask(taskEntity.getId(),taskEntity.getTask_priority(),taskEntity.getTask_desc(),
//                        taskEntity.getTask_due(),dueday,duemonth,dueyear);
//            }
        }
    }
    public static void completedTask(int update_id,String task_desc,String task_priority,String task_due,boolean ischecked
    ,int comp_dueday,int comp_duemonth,int comp_dueyear){
        TaskEntity taskEntity;
        if(ischecked) {
            taskEntity = new TaskEntity(task_desc, task_priority, task_due, 1,comp_dueday,comp_duemonth
            ,comp_dueyear);
            taskEntity.setId(update_id);

        }
        else {
            taskEntity = new TaskEntity(task_desc, task_priority, task_due, 0,comp_dueday,comp_duemonth
            ,comp_dueyear);
            taskEntity.setId(update_id);
        }
        task_viewModel.update(taskEntity);
    }
    public void get_weeK_list(){
        Calendar tillnextweak = Calendar.getInstance();
        task_viewModel.getThisweektask(tillnextweak.get(Calendar.DAY_OF_MONTH)).observe(this, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntities) {
                todaytaskAdapter.showTask(taskEntities);
                Log.i("call show", String.valueOf(taskEntities.size()));

            }
        });
    }

}
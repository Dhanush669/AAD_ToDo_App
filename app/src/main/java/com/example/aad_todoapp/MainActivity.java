package com.example.aad_todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.aad_todoapp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.aad_todoapp.AddTask.day;
import static com.example.aad_todoapp.AddTask.month;
import static com.example.aad_todoapp.AddTask.year;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    static Task_ViewModel task_viewModel;
    List<TaskEntity> tasklist;
    int notiid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        tasklist=new ArrayList<>();
        TaskAdapter taskAdapter=new TaskAdapter();
        binding.recyclerview.setAdapter(taskAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter.onItemClicked(new TaskAdapter.onClickListener() {
            @Override
            public void itemClick(TaskEntity taskEntity) {
                Intent intent=new Intent(MainActivity.this,AddTask.class);
                intent.putExtra("up_task_desc",taskEntity.getTask_desc());
                intent.putExtra("up_task_due",taskEntity.getTask_due());
                intent.putExtra("up_task_priority",taskEntity.getTask_priority());
                intent.putExtra("up_task_id",taskEntity.getId());
                startActivityForResult(intent,2);
            }
        });
        task_viewModel= new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(this.getApplication())).get(Task_ViewModel.class);
        task_viewModel.getAllTask().observe(this, new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntities) {
                taskAdapter.showTask(taskEntities);
            }
        });
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
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            String taskdesc=data.getStringExtra("task_Desc");
            String taskprior=data.getStringExtra("task_Proir");
            String taskdue=data.getStringExtra("task_Due");
            TaskEntity taskEntity=new TaskEntity(taskdesc,taskprior,taskdue,0);
            task_viewModel.add(taskEntity);
            setTask(taskEntity.getId(),taskEntity.getTask_priority(),taskEntity.getTask_desc(),
                    taskEntity.getTask_due());

        }
        else if(requestCode==2 && resultCode==RESULT_OK){
            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
            String taskdesc=data.getStringExtra("task_Desc");
            Toast.makeText(this, "2"+taskdesc, Toast.LENGTH_SHORT).show();
            String taskprior=data.getStringExtra("task_Proir");
            String taskdue=data.getStringExtra("task_Due");
            int id=data.getIntExtra("id",-1);
            if(id==-1){
                Toast.makeText(this, "Task can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            TaskEntity taskEntity=new TaskEntity(taskdesc,taskprior,taskdue,0);
            taskEntity.setId(id);
            task_viewModel.update(taskEntity);
            setTask(taskEntity.getId(),taskEntity.getTask_priority(),taskEntity.getTask_desc(),
                    taskEntity.getTask_due());
        }
    }
    public  void setTask(int notiid,String priority,String taskdesc,String taskdue){
        int proirityid=0;
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,50);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.AM_PM,Calendar.AM);
        calendar.set(Calendar.DAY_OF_MONTH,day);
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
        Intent intent=new Intent(this,TaskReceiver.class);
        intent.putExtra("priority",proirityid);
        intent.putExtra("noti_id",notiid);
        intent.putExtra("task_desc",taskdesc);
        intent.putExtra("task_due",taskdue);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,notiid,intent,0);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }
    public static void completedTask(int update_id,String task_desc,String task_priority,String task_due){
        TaskEntity taskEntity=new TaskEntity(task_desc,task_priority,task_due,1);
        taskEntity.setId(update_id);
        task_viewModel.update(taskEntity);
    }
}
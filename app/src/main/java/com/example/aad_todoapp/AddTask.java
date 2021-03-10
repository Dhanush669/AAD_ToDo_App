package com.example.aad_todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aad_todoapp.databinding.ActivityAddTaskBinding;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {
    ActivityAddTaskBinding binding;
    String priority;
    int dp=0,id;
    static int notiid,year,month,day;
    public static final String Save_Value="PRIORITY";
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddTaskBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.taskproioity,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.taskPriority.setAdapter(adapter);
        Intent intent=getIntent();
        if(dp==0){
            binding.radioGroup.check(R.id.no_due);
        }
        if(intent.getStringExtra("up_task_desc")!=null){
            setTitle("Update Task");
            id=intent.getIntExtra("up_task_id",-1);
            binding.taskDesc.setText(intent.getStringExtra("up_task_desc"));
            binding.selectedDate.setText(intent.getStringExtra("up_task_due"));
            switch(intent.getStringExtra("up_task_priority")){
                case "High":
                    binding.taskPriority.setSelection(0);
                    break;
                case "Medium":
                    binding.taskPriority.setSelection(1);
                    break;
                case "Low":
                    binding.taskPriority.setSelection(2);
                    break;

            }
            binding.radioGroup.check(R.id.due_date);
            binding.addBtn.setText("update Task");
        }
        else{
            setTitle("Add Task");
            binding.addBtn.setText("Add Task");
        }
        if(savedInstanceState!=null){
            if(savedInstanceState.getString(Save_Value)!=null){
               binding.selectedDate.setText(savedInstanceState.getString(Save_Value));
            }
            if(savedInstanceState.getInt("Dp_Value")!=0){
                dp=1;
            }
        }
        datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date=i2+" / "+(i1+1)+" / "+i;
                day=i2;
                month=i1;
                year=i;
                binding.selectedDate.setText(date);
            }
        },Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONDAY),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));


        binding.taskPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priority=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {
               switch (i){
                   case R.id.no_due:
                       dp=0;
                       break;
                   case R.id.due_date:
                      if(dp==0) {
                           datePickerDialog.show();
                       }
                        break;
               }
           }
       });
       binding.addBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(binding.taskDesc.getText().toString().equals("") || binding.selectedDate.getText().toString().equals("")){
                   Toast.makeText(AddTask.this, "Task Description or Task Due Missing!", Toast.LENGTH_SHORT).show();
               }
               else{
                   Intent intent=new Intent();
                   //Toast.makeText(AddTask.this,
                        //   binding.taskDesc.getText().toString(), Toast.LENGTH_SHORT).show();
                   intent.putExtra("task_Desc",binding.taskDesc.getText().toString());
                   intent.putExtra("task_Proir",priority);
                   intent.putExtra("task_Due",binding.selectedDate.getText().toString());
                   if(id!=-1){
                       intent.putExtra("id",id);
                       notiid=id;
                   }
                   setResult(RESULT_OK,intent);
                   finish();

               }
           }
       });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
       if(!binding.selectedDate.getText().equals("")){
           outState.putString(Save_Value,binding.selectedDate.getText().toString());
           outState.putInt("Dp_Value",1);
       }
    }

}
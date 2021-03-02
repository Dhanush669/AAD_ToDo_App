package com.example.aad_todoapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String task_desc;
    private String task_priority;
    private String task_due;

    public TaskEntity(String task_desc, String task_priority, String task_due) {
        this.task_desc = task_desc;
        this.task_priority = task_priority;
        this.task_due = task_due;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public String getTask_priority() {
        return task_priority;
    }

    public String getTask_due() {
        return task_due;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package com.example.aad_todoapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int completed;
    private String task_desc;
    private String task_priority;
    private String task_due;
    private int due_day;
    private int due_month;
    private int due_year;

    public TaskEntity(String task_desc, String task_priority, String task_due,int completed,int due_day
    ,int due_month,int due_year) {
        this.task_desc = task_desc;
        this.task_priority = task_priority;
        this.task_due = task_due;
        this.completed=completed;
        this.due_day=due_day;
        this.due_month=due_month;
        this.due_year=due_year;
    }
    public int getDue_day() {
        return due_day;
    }

    public void setDue_day(int due_day) {
        this.due_day = due_day;
    }

    public int getDue_month() {
        return due_month;
    }

    public void setDue_month(int due_month) {
        this.due_month = due_month;
    }

    public int getDue_year() {
        return due_year;
    }

    public void setDue_year(int due_year) {
        this.due_year = due_year;
    }

    public int getCompleted() {
        return completed;
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

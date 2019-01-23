package com.example.android.new_tasks_list.model;

import java.util.Date;

public class Task {
    /**
     * descriptive name for the task
     */
    private String name;

    /**
     * the due date of the task;
     */
    private Date dueDate;

    /**
     * the detailed description of the task.
     */
    private String description;

    /**
     * indicates the current state of the task(In progress, completed or archived)
     */
    private String state;


    public Task (String name, Date dueDate, String description) {
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

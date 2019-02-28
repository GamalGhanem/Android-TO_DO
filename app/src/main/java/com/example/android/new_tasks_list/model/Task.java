package com.example.android.new_tasks_list.model;

public class Task {

    /**
     * ID given for the task when first created
     */
    private int id;

    /**
     * descriptive name for the task
     */
    private String name;

    /**
     * the due date of the task;
     */
    private String dueDate;

    /**
     * the detailed description of the task.
     */
    private String description;

    /**
     * indicates the current state of the task(In progress, completed or archived)
     */
    private String state;

    public final static String INPROGRESS_STATE = "In Progress";
    public final static String COMPLETED_STATE = "Completed";
    public final static String ARCHIVED_STATE = "Archived";

    public Task (String name, String dueDate, String description) {
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
        state = INPROGRESS_STATE;
    }

    public Task (int id, String name, String dueDate, String description) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
        state = INPROGRESS_STATE;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

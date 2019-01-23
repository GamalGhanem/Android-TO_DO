package com.example.android.new_tasks_list.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.new_tasks_list.model.Task;

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = TaskDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "tasks_organiser.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating the string that contains the SQL statement to create the tasks table
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + "("
                + TaskContract.TaskEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskContract.TaskEntry.TASK_COLUMN_NAME + "TEXT NOT NULL, "
                + TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE + "TEXT NOT NULL, "
                + TaskContract.TaskEntry.TASK_COLUMN_DESCRIPTION + "TEXT, "
                + TaskContract.TaskEntry.TASK_COLUMN_STATE + "TEXT NOT NULL);";

        //executing the create table statement
        db.execSQL(CREATE_TASKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

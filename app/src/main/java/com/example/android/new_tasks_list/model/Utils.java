package com.example.android.new_tasks_list.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.new_tasks_list.data.TaskContract;
import com.example.android.new_tasks_list.data.TaskDbHelper;

public class Utils {

    public Cursor getTasks(TaskDbHelper dbHelper, String categ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //desired attributes
        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.TASK_COLUMN_NAME,
                TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE,
                TaskContract.TaskEntry.TASK_COLUMN_DESCRIPTION
        };

        //selection condition
        String selection = TaskContract.TaskEntry.TASK_COLUMN_STATE + "=?";

        //selection args: value for every ? in the selection
        String[] selectionArgs = {categ};

        String sortOrder = TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE + " ASC";

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

}

package com.example.android.new_tasks_list;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.new_tasks_list.data.TaskContract;

public class TaskAdapter extends CursorAdapter {
    public TaskAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView taskNameView = (TextView)view.findViewById(R.id.task_name);
        TextView taskDateView = (TextView)view.findViewById(R.id.task_date);

        String taskName = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.TASK_COLUMN_NAME));
        String taskDate = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE));

        taskNameView.setText(taskName);
        taskDateView.setText(taskDate);
    }
}

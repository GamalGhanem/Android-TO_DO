package com.example.android.new_tasks_list;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.new_tasks_list.data.TaskContract;
import com.example.android.new_tasks_list.data.TaskDbHelper;
import com.example.android.new_tasks_list.model.Task;

import java.util.ArrayList;

public class InProgressFragment extends Fragment {

    private TaskDbHelper dbHelper;
    private static String state = Task.INPROGRESS;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        listView = (ListView)rootView.findViewById(R.id.task_list);
        dbHelper = new TaskDbHelper(getActivity());
        Cursor cursor = getTasks();
        displayTasks(cursor);
        return rootView;

    }

    /**
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new TaskDbHelper(getActivity());
        Cursor cursor = getTasks();
        displayTasks(cursor);
    }
    */

    public Cursor getTasks() {

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
        String[] selectionArgs = {Task.INPROGRESS};

        String sortOrder = TaskContract.TaskEntry.TASK_COLUMN_NAME + " DESC";

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }


    public void displayTasks(Cursor cursor) {
        /**
        ArrayList<Task> tasksList = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_NAME));
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry._ID)));
                String date = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE));
                String description = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_DESCRIPTION));
                tasksList.add(new Task(id, name, date, description));
            }
        } finally {
            cursor.close();
        }
         **/

        TaskAdapter adapter = new TaskAdapter(getActivity(), cursor);
        listView.setAdapter(adapter);
    }

}

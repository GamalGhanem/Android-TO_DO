package com.example.android.new_tasks_list;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.new_tasks_list.data.TaskContract;
import com.example.android.new_tasks_list.data.TaskDbHelper;
import com.example.android.new_tasks_list.model.Task;
import com.example.android.new_tasks_list.model.Utils;

public class ArchivedFragment extends Fragment {

    private TaskDbHelper dbHelper;
    private static String state = Task.INPROGRESS_STATE;
    ListView listView;
    TaskAdapter adapter;
    Utils utilsObj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        listView = (ListView)rootView.findViewById(R.id.task_list);
        dbHelper = new TaskDbHelper(getActivity());
        utilsObj = new Utils();
        final Cursor cursor = utilsObj.getTasks(dbHelper, Task.ARCHIVED_STATE);
        displayTasks(cursor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor mCusor = (Cursor)adapter.getItem(position);

                String taskName = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_NAME));
                String taskDate = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE));
                String taskDesc = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_DESCRIPTION));
                String taskId = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry._ID));

                Intent mIntent = new Intent(getActivity(), EditActivity.class);

                mIntent.putExtra(TaskContract.TaskEntry.TASK_COLUMN_NAME, taskName);
                mIntent.putExtra(TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE, taskDate);
                mIntent.putExtra(TaskContract.TaskEntry.TASK_COLUMN_DESCRIPTION, taskDesc);
                mIntent.putExtra(TaskContract.TaskEntry._ID, taskId);

                startActivity(mIntent);
            }
        });
        return rootView;
    }


    public void displayTasks(Cursor cursor) {
        adapter = new TaskAdapter(getActivity(), cursor);
        listView.setAdapter(adapter);
    }
}

package com.example.android.new_tasks_list;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.new_tasks_list.data.TaskContract;
import com.example.android.new_tasks_list.data.TaskDbHelper;
import com.example.android.new_tasks_list.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    private EditText editText;
    private TaskDbHelper dbHelper;
    private Task mTask;
    private EditText nameEditText;
    private EditText dateEditText;
    private EditText descEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Details");
        toolbar.setNavigationIcon(R.drawable.back_button_image);

        dbHelper = new TaskDbHelper(this);

        editText = (EditText)findViewById(R.id.task_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        fillForm();

        
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent newIntent = new Intent(EditActivity.this, MainActivity.class);
        switch (item.getItemId()) {
            case R.id.action_save:
                //update the task Info.
                saveUpdateTask();
                startActivity(newIntent);
                return true;
            case R.id.action_archive:
                //add the task to archive
                addToArchive();
                startActivity(newIntent);
                return true;
            case R.id.action_delete:
                deleteTask();
                startActivity(newIntent);
                return true;
            case R.id.action_done:
                addToCompleted();
                startActivity(newIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillForm() {
        //getting the attributes of the desired task
        Intent mIntent = getIntent();
        String taskName = mIntent.getStringExtra(TaskContract.TaskEntry.TASK_COLUMN_NAME);
        String taskDate = mIntent.getStringExtra(TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE);
        String taskDesc = mIntent.getStringExtra(TaskContract.TaskEntry.TASK_COLUMN_DESCRIPTION);
        String taskId = mIntent.getStringExtra(TaskContract.TaskEntry._ID);

        mTask = new Task(Integer.parseInt(taskId), taskName, taskDate, taskDesc);

        nameEditText = (EditText)findViewById(R.id.task_name);
        dateEditText = (EditText)findViewById(R.id.task_date);
        descEditText = (EditText)findViewById(R.id.task_description);

        dateEditText.setText(taskDate);
        nameEditText.setText(taskName);
        descEditText.setText(taskDesc);
    }

    private void saveUpdateTask() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put(TaskContract.TaskEntry.TASK_COLUMN_NAME, String.valueOf(nameEditText.getText()));
        vals.put(TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE, String.valueOf(dateEditText.getText()));
        vals.put(TaskContract.TaskEntry.TASK_COLUMN_DESCRIPTION, String.valueOf(descEditText.getText()));

        //selection condition
        String selection = TaskContract.TaskEntry._ID + "=?";

        //selection args: value for every ? in the selection
        String[] selectionArgs = {String.valueOf(mTask.getId())};
        int cnt = db.update(TaskContract.TaskEntry.TABLE_NAME, vals, selection, selectionArgs);
    }

    private void addToArchive() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TaskContract.TaskEntry.TASK_COLUMN_STATE, Task.ARCHIVED_STATE);

        String selection = TaskContract.TaskEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(mTask.getId())};
        int cnt = db.update(TaskContract.TaskEntry.TABLE_NAME, vals, String.valueOf(selection), selectionArgs);

        Toast.makeText(this, "Task Was Successfully Archived", Toast.LENGTH_SHORT).show();
    }

    private void deleteTask() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = TaskContract.TaskEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(mTask.getId())};

        int cnt = db.delete(TaskContract.TaskEntry.TABLE_NAME, selection, selectionArgs);

        Toast.makeText(this, "Task Was Successfully Deleted", Toast.LENGTH_SHORT).show();
    }

    private void addToCompleted() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(TaskContract.TaskEntry.TASK_COLUMN_STATE, Task.COMPLETED_STATE);

        String selection = TaskContract.TaskEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(mTask.getId())};

        int cnt = db.update(TaskContract.TaskEntry.TABLE_NAME, vals, selection, selectionArgs);
        Toast.makeText(this, "Task Was Successfully Marked As Done", Toast.LENGTH_SHORT).show();

    }

}

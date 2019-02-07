package com.example.android.new_tasks_list;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class AddActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    private EditText editText;
    private TaskDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_button_image);
        setSupportActionBar(toolbar);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                new DatePickerDialog(AddActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        dbHelper = new TaskDbHelper(this);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                addTask();
                Log.v("insert", "before.........................................................");
                Intent newIntent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(newIntent);
                Log.v("insert", "after.........................................................");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addTask() {


        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        EditText nameEditText = (EditText)findViewById(R.id.task_name);
        String taskName = String.valueOf(nameEditText.getText());

        EditText dateEditText = (EditText)findViewById(R.id.task_date);
        String taskDate = String.valueOf(dateEditText.getText());

        EditText descEditText = (EditText)findViewById(R.id.task_description);
        String taskDesc = String.valueOf(descEditText.getText());

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.TASK_COLUMN_NAME, taskName);
        values.put(TaskContract.TaskEntry.TASK_COLUMN_DUE_DATE, taskDate);
        values.put(TaskContract.TaskEntry.TASK_COLUMN_DESCRIPTION, taskDesc);
        values.put(TaskContract.TaskEntry.TASK_COLUMN_STATE, Task.INPROGRESS);


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);

        Toast.makeText(this, "A new task was added with id " + newRowId, Toast.LENGTH_SHORT).show();
    }
}

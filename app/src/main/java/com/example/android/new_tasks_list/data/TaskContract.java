package com.example.android.new_tasks_list.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class TaskContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.new_tasks_list";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TASKS = "tasks";

    public static abstract class TaskEntry implements BaseColumns {
        // uri path of the pets table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TASKS);

        //table name constant
        public  static final String TABLE_NAME = "tasks";

        //columns headers values
        public static final String _ID = BaseColumns._ID;
        public static final String TASK_COLUMN_NAME = "name";
        public static final String TASK_COLUMN_DUE_DATE = "dueDate";
        public static final String TASK_COLUMN_DESCRIPTION = "description";
        public static final String TASK_COLUMN_STATE = "state";

        //possible values of state
        public static String STATE_IN_PROGRESS = "inProgress";
        public static String STATE_COMPLETED = "completed";
        public static String STATE_ARCHIVED = "archived";

    }

}

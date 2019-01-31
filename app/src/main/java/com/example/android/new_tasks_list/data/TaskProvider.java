package com.example.android.new_tasks_list.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.new_tasks_list.model.Task;

public class TaskProvider extends ContentProvider {

    private static final int TASKS = 100;
    private static final int TASK_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        sUriMatcher.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_ID);
    }

    /** Tag for the log messages */
    public static final String LOG_TAG = TaskProvider.class.getSimpleName();

    /**
     * Initialize the provider and the database helper object.
     */
    private TaskDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new TaskDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor = null;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case TASKS:
                // For the TASKS code, query the Tasks table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the tasks table.
                // TODO: Perform database query on tasks table
                cursor = database.query(TaskContract.TaskEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case TASK_ID:
                // For the TASK_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.Tasks/Tasks/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = TaskContract.TaskEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the Tasks table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(TaskContract.TaskEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TASKS:
                return insertTask(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertTask(Uri uri, ContentValues values) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        // Insert the new row, returning the primary key value of the new row
        long newRowId = database.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        return Uri.withAppendedPath(uri, Long.toString(newRowId));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        int deletedRows = database.delete(TaskContract.TaskEntry.TABLE_NAME, selection, selectionArgs);
        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

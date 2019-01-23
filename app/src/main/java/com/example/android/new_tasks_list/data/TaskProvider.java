package com.example.android.new_tasks_list.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.new_tasks_list.model.Task;

public class TaskProvider extends ContentProvider {

    private static final int TASKS = 100;
    private static final int TASK_ID = 101;
    private static final UriMatcher sUriMATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMATCHER.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        sUriMATCHER.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_ID);
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
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

package btp400.assignment2.todolist.logger;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class DatabaseLogger {
    private static final String TAG = "DatabaseLogger";


    public static void onCreate(String tableName) {
        Log.w(TAG, "Created table: " + tableName);
    }

    public static void onUpgrade(String tableName, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading table: " + tableName + " from version " + oldVersion + " to " + newVersion);
    }

    public static void onOpen(String tableName) {
        Log.i(TAG, "Opened table: " + tableName);
    }

    public static void onInsert(String tableName, String values) {
        Log.i(TAG, "Inserted into table: " + tableName + " values: " + values);
    }

    public static void onGetAllTasks(String tableName) {
        Log.i(TAG, "Getting all tasks from table: " + tableName);
    }
}

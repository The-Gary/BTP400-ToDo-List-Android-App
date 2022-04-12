package btp400.assignment2.todolist.logger;

import android.util.Log;

public class UILogger {
    private static final String TAG = "TouchHelperLogger";

    public static void onSwipeLeft() {
        Log.i(TAG, "task swiped left" );
    }
    public static void onSwipeRight() {
        Log.i(TAG, "task swiped right" );
    }
    public static void onClick(String buttonName, String msg) {
        Log.i(TAG, buttonName + msg );
    }
    public static void onAlertBoxOpen() {
        Log.i(TAG, "alert box opened" );
    }
    public static void onDialogBoxOpen() {
        Log.i(TAG, "dialog box opened" );
    }
    public static void onDialogBoxClose() {
        Log.i(TAG, "dialog box closed" );
    }
}

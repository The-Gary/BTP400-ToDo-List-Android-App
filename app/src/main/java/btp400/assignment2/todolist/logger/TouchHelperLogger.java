package btp400.assignment2.todolist.logger;

import android.util.Log;

public class TouchHelperLogger {
    private static final String TAG = "TouchHelperLogger";

    public static void onSwipeLeft() {
        Log.w(TAG, "task swiped left" );
    }
    public static void onSwipeRight() {
        Log.w(TAG, "task swiped right" );
    }
    public static void ColorIndicator(String color){
        Log.w(TAG, "Color after swipe: " + color);
    }
}

package btp400.assignment2.todolist.logger;

import android.util.Log;

public class AdapterLogger {
    private final static String Tag = "AdapterLogger";

    public static void onSetTodoList() {
        Log.i(Tag, "onSetTodoList set ");
    }

    public static void onDeleteItem(int position) {
        Log.i(Tag, "onDeleteItem delete item at position " + position);
    }

    public static void onEditItem(int position) {
        Log.i(Tag, "onEditItem edit item at position " + position);
    }

    public static void onStatusChange(int position) {
        Log.i(Tag, "onStatusChange change status of item at position " + position);
    }

    public static void onBindViewHolder() {
        Log.i(Tag, "onBindViewHolder successfully bound to ViewHolder");
    }
}

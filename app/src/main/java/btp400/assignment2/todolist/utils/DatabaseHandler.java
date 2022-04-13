package btp400.assignment2.todolist.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import btp400.assignment2.todolist.logger.DatabaseLogger;
import btp400.assignment2.todolist.model.ToDoModel;

public class DatabaseHandler extends SQLiteOpenHelper {

    /**
     * This variable defines the version of the data base
     */
    private static final int VERSION = 1;
    /**
     * This variable is the name of the data base
     */
    private static final String Name = "TodoListDatabase";
    /**
     * This is the name of the table
     */
    private static final String TODO_TABLE = "todo";
    /**
     * These variables are the name of the columns
     */
    private static final String ID = "id";

    private static final String TASK = "task";

    private static final String STATUS = "status";
    /**
     * We use this variable to create the database
     */
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";
    /**
     * This is a reference of the database we are creating
     */
    private SQLiteDatabase db;

    /**
     *This constructor use pass the context to the super class
     * @param context is the context of the database
     */
    public DatabaseHandler(Context context) {
        super(context, Name, null, VERSION);
    }

    /**
     * This method creates new table
     * @param db is the database we have created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
        DatabaseLogger.onCreate(Name);
    }

    /**
     * This method will drop the old table and create new one based on the newVersion
     * @param db is the data base
     * @param oldVersion is the old version of data
     * @param newVersion is the new version of the data
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
        DatabaseLogger.onUpgrade(Name, oldVersion, newVersion);
    }

    /**
     * This method will be called in other methods to be able to modify database
     */
    public void openDatabase() {
        db = this.getWritableDatabase();
        DatabaseLogger.onOpen(Name);
    }

    /**
     * This method allows us to insert data into database
     * @param task is the task that we want to add into our database
     */
    public void insertTask(ToDoModel task) {
        ContentValues values = new ContentValues();
        values.put(TASK, task.getTask());
        values.put(STATUS, 0);
        db.insert(TODO_TABLE, null, values);
        DatabaseLogger.onInsert(Name, task.getTask());
    }

    /**
     * This method will get all the tasks from database and store it in the array list and returns to it
     * @return List<ToDoModel>
     */
    public List<ToDoModel> getAllTasks() {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setTask(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
                        taskList.add(task);
                    }
                    while (cur.moveToNext());
                }
            }
            DatabaseLogger.onGetAllTasks(Name);
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    /**
     * This method will update the status of the task. (checked, Unchecked)
     * @param id is the id of the task which we want to update the status of
     * @param status is the the new status that we want to assign to the specific task
     */
    public void updateStatus(int id, int status) {
        ContentValues values = new ContentValues();
        values.put(STATUS, status);
        db.update(TODO_TABLE, values, ID + "= ?", new String[]{String.valueOf(id)});
        DatabaseLogger.onUpdateStatus(Name, values.getAsString(STATUS));
    }

    /**
     * This method will update the task in case the user wants to modify it
     * @param id is the id of the task which we want to update
     * @param task is the modified description
     */

    public void updateTask(int id, String task) {
        ContentValues values = new ContentValues();
        values.put(TASK, task);
        db.update(TODO_TABLE, values, ID + "= ?", new String[]{String.valueOf(id)});
        DatabaseLogger.onUpdateTask(Name, task);
    }

    /**
     * This method will delete the task if the user want to remove it from the list
     * @param id is the id of the task which we want to delete
     */
    public void deleteTask(int id) {
        db.delete(TODO_TABLE, ID + "= ?", new String[]{String.valueOf(id)});
        DatabaseLogger.onDeleteTask(Name, id);
    }
}

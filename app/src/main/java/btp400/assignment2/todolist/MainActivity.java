package btp400.assignment2.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import btp400.assignment2.todolist.adapter.ToDoAdapter;
import btp400.assignment2.todolist.logger.UILogger;
import btp400.assignment2.todolist.model.ToDoModel;
import btp400.assignment2.todolist.utils.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private List<ToDoModel> tasks = new ArrayList<>(0);
    private ToDoAdapter tasksAdapter;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        RecyclerView tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            UILogger.onClick("FAB button", "Add new task button clicked");
            UILogger.onDialogBoxOpen();
        });

        tasks = db.getAllTasks();
        Collections.reverse(tasks);
        tasksAdapter.setToDoList(tasks);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        UILogger.onDialogBoxClose();
        tasks = db.getAllTasks();
        Collections.reverse(tasks);
        tasksAdapter.setToDoList(tasks);
        tasksAdapter.notifyItemChanged(tasksAdapter.getItemCount());
    }
}
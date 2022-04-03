package btp400.assignment2.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import btp400.assignment2.todolist.adapter.ToDoAdapter;
import btp400.assignment2.todolist.model.ToDoModel;
import btp400.assignment2.todolist.utils.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private List<ToDoModel> tasks = new ArrayList<>(0);
    private ToDoAdapter tasksAdapter;
    private RecyclerView tasksRecyclerView;
    private FloatingActionButton fab;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        tasks = db.getAllTasks();
        Collections.reverse(tasks);
        tasksAdapter.setToDoList(tasks);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        tasks = db.getAllTasks();
        Collections.reverse(tasks);
        tasksAdapter.setToDoList(tasks);
        tasksAdapter.notifyDataSetChanged();

    }
}
package btp400.assignment2.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import btp400.assignment2.todolist.adapter.ToDoAdapter;
import btp400.assignment2.todolist.model.ToDoModel;

public class MainActivity extends AppCompatActivity {

    private List<ToDoModel> tasks = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        RecyclerView tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ToDoAdapter tasksAdapter = new ToDoAdapter(this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ToDoModel task = new ToDoModel(1, 0, "This is a task");
        ToDoModel task2 = new ToDoModel(2, 0, "This is another task");
        tasks.add(task);
        tasks.add(task);
        tasks.add(task);
        tasks.add(task);
        tasks.add(task2);
        tasks.add(task2);
        tasks.add(task2);
        tasks.add(task2);
        tasksAdapter.setToDoList(tasks);
    }
}
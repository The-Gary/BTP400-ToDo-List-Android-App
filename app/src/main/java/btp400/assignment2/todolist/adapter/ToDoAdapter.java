package btp400.assignment2.todolist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import btp400.assignment2.todolist.AddNewTask;
import btp400.assignment2.todolist.MainActivity;
import btp400.assignment2.todolist.R;
import btp400.assignment2.todolist.model.ToDoModel;
import btp400.assignment2.todolist.utils.DatabaseHandler;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    private List<ToDoModel> toDoList;
    private MainActivity activity;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ToDoViewHolder(itemView);
    }

    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        db.openDatabase();
        ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    db.updateStatus(item.getId(), 1);
                }
                else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public int getItemCount() {
        return toDoList.size();
    }

    private boolean toBoolean(int number) {
        return number == 1;
    }

    public void setToDoList(List<ToDoModel> toDoList) {
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDoModel item = toDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.todo_checkbox);
        }
    }


}

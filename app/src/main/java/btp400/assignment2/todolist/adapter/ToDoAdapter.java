package btp400.assignment2.todolist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import btp400.assignment2.todolist.AddNewTask;
import btp400.assignment2.todolist.MainActivity;
import btp400.assignment2.todolist.R;
import btp400.assignment2.todolist.logger.AdapterLogger;
import btp400.assignment2.todolist.model.ToDoModel;
import btp400.assignment2.todolist.utils.DatabaseHandler;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    private List<ToDoModel> toDoList;
    private final MainActivity activity;
    private final DatabaseHandler db;
    /**custom constructor*/
    public ToDoAdapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }
    /**
     * this method handles the view holder when it gets created*/
    @NonNull
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        AdapterLogger.onBindViewHolder();
        return new ToDoViewHolder(itemView);
    }
    /**this method receives a holder and a position and using these two will set  text and check status of the task
     * @param position
     * @param holder */
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        db.openDatabase();
        ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                db.updateStatus(item.getId(), 1);
            } else {
                db.updateStatus(item.getId(), 0);
            }
            AdapterLogger.onStatusChange(position);
        });
    }
    /**
     * this method returns the size of the tasks list*/
    public int getItemCount() {
        return toDoList.size();
    }

    private boolean toBoolean(int number) {
        return number == 1;
    }
/**
 * this method receives a List and sets the current list to the new list
 * @param toDoList */
    public void setToDoList(List<ToDoModel> toDoList) {
        this.toDoList = toDoList;
        AdapterLogger.onSetTodoList();
        notifyDataSetChanged();
    }

    public Context getContext() {
        return activity;
    }
    /**
     * this method gets the position of the task and removes it from the list and database
     * @param position*/
    public void deleteItem(int position) {
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        AdapterLogger.onDeleteItem(position);
        notifyItemRemoved(position);
    }
/**
 * this method gets the position of the task and updates it on the list and database
 * @param position */
    public void editItem(int position) {
        ToDoModel item = toDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
        AdapterLogger.onEditItem(position);
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.todo_checkbox);
        }
    }


}

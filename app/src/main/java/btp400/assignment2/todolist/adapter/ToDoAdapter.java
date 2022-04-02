package btp400.assignment2.todolist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import btp400.assignment2.todolist.MainActivity;
import btp400.assignment2.todolist.R;
import btp400.assignment2.todolist.model.ToDoModel;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    private List<ToDoModel> toDoList;
    private MainActivity activity;

    public ToDoAdapter(MainActivity activity) {
        this.activity = activity;
    }

    @NonNull
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ToDoViewHolder(itemView);
    }

    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
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

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.todo_checkbox);
        }
    }


}

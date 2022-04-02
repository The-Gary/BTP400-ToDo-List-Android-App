package btp400.assignment2.todolist.model;

public class ToDoModel {
    private int id = 0, status = 0;
    private String task = "";

    public ToDoModel(int id, int status, String task) {
        this.id = id;
        this.status = status;
        this.task = task;
    }

    public ToDoModel() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}

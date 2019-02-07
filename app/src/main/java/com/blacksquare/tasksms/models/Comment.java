package com.blacksquare.tasksms.models;

public class Comment {
    private String ID, TaskID, Title;

    public Comment() {
    }

    public Comment(String toString) {
        Title = toString;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}

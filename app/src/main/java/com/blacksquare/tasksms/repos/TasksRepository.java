package com.blacksquare.tasksms.repos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.blacksquare.tasksms.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksRepository {
    List<Task> TasksList;
    LiveData<List<Task>> TasksListLiveData;

    int lastIDIndex = 3;

    public TasksRepository() {
        TasksList = new ArrayList<>();
        TasksListLiveData = new MutableLiveData<>();
        setDefaultTasksList();
    }

    public LiveData<List<Task>> getTasksListLiveData(String username) {
        ((MutableLiveData<List<Task>>) TasksListLiveData).setValue(TasksList);
        return TasksListLiveData;
    }

    public LiveData<List<Task>> getTasksListLiveData() {
        ((MutableLiveData<List<Task>>) TasksListLiveData).setValue(TasksList);
        return TasksListLiveData;
    }


    private void setDefaultTasksList() {
        TasksList.add(new Task("1", "Task 1", "31 Jan 2019", 2));
        TasksList.add(new Task("2", "Task 2", "1 Feb 2019", 0));
        TasksList.add(new Task("3", "Task 3", "2 Feb 2019", 1));
    }

    public void deleteTask(Task task) {
        for (int i = 0; i < TasksList.size(); i++) {
            if (TasksList.get(i).equals(task)) {
                TasksList.remove(i);
                break;
            }
        }
    }

    public void addTask(String username, Task task) {
        lastIDIndex++;
        task.setID(String.valueOf(lastIDIndex));
        TasksList.add(task);
        ((MutableLiveData<List<Task>>) TasksListLiveData).setValue(TasksList);
    }
}

package com.blacksquare.tasksms.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.blacksquare.tasksms.models.Task;
import com.blacksquare.tasksms.repos.TasksRepository;

import java.util.ArrayList;
import java.util.List;

public class TasksViewModel extends AndroidViewModel {
    LiveData<List<Task>> listLiveData = new MutableLiveData<>();
    TasksRepository tasksRepository;
    List<Task> TasksList, FilteredTasksList;

    public TasksViewModel(@NonNull Application application) {
        super(application);
        TasksList = new ArrayList<>();
        FilteredTasksList = new ArrayList<>();
        tasksRepository = new TasksRepository();
    }

    public LiveData<List<Task>> getListLiveData(String username) {
        listLiveData = tasksRepository.getTasksListLiveData(username);
        return listLiveData;
    }

    public LiveData<List<Task>> getListLiveData() {
        return listLiveData;
    }

    public void deleteTask(Task task) {
        tasksRepository.deleteTask(task);
    }

    public void addTaskToUser(String username, Task task) {
        tasksRepository.addTask(username, task);
    }

    public void filterTasksByDone() {
        TasksList = tasksRepository.getTasksListLiveData().getValue();
        FilteredTasksList.clear();

        for (int i = 0; i < TasksList.size(); i++)
            if (!TasksList.get(i).isDone())
                FilteredTasksList.add(TasksList.get(i));
        ((MutableLiveData<List<Task>>) listLiveData).setValue(FilteredTasksList);
    }

    public void showAllTasks() {
        ((MutableLiveData<List<Task>>) listLiveData).setValue(TasksList);
    }

    public void updateTask(Task currentTask) {
        TasksList = tasksRepository.getTasksListLiveData().getValue();
        for (int i = 0; i < TasksList.size(); i++)
            if (currentTask.getID().equals(TasksList.get(i).getID())) {
                TasksList.get(i).setTitle(currentTask.getTitle());
                TasksList.get(i).setDate(currentTask.getDate());
                TasksList.get(i).setPriority(currentTask.getPriority());
                break;
            }
        ((MutableLiveData<List<Task>>) listLiveData).setValue(TasksList);
    }
}

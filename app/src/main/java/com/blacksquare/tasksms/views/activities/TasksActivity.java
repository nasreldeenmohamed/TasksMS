package com.blacksquare.tasksms.views.activities;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blacksquare.tasksms.R;
import com.blacksquare.tasksms.databinding.ActivityTasksBinding;
import com.blacksquare.tasksms.models.Task;
import com.blacksquare.tasksms.viewModels.TasksViewModel;
import com.blacksquare.tasksms.views.adapters.TasksAdapter;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {
    ActivityTasksBinding binding;
    TasksViewModel tasksViewModel;

    //    TasksAdapterOld tasksAdapter;
    TasksAdapter tasksAdapter;
    String Username = "";
    List<Task> tasks = new ArrayList<>();

    boolean isFiltered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasks);

        Username = getIntent().getStringExtra("username");

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.toolbar.setTitle(Username + "'s Tasks");
            binding.toolbar.setTitleTextColor(getResources().getColor(R.color.gray_text));
        }


        tasksViewModel = ViewModelProviders.of(this).get(TasksViewModel.class);
        tasksAdapter = new TasksAdapter(TasksActivity.this, tasks);

        binding.recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewTasks.setAdapter(tasksAdapter);

        tasksViewModel.getListLiveData(Username).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
//                Toast.makeText(TasksActivity.this, "list is updated!", Toast.LENGTH_SHORT).show();
//                tasksAdapter.submitList(tasks);
                tasksAdapter = new TasksAdapter(TasksActivity.this, tasks);
                binding.recyclerViewTasks.setAdapter(tasksAdapter);
            }
        });

        tasksAdapter.setOnItemClickListener(new TasksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(TasksActivity.this, TaskDetailsActivity.class);
                intent.putExtra("task", task);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                tasksViewModel.deleteTask(tasksViewModel.getListLiveData().getValue().get(viewHolder.getAdapterPosition()));
                Toast.makeText(TasksActivity.this, "Task Deleted!", Toast.LENGTH_SHORT).show();
                tasksAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(binding.recyclerViewTasks);

        binding.buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AddTaskDialog addTaskDialog = new AddTaskDialog(TasksActivity.this, tasksViewModel);
//                addTaskDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                addTaskDialog.setCancelable(true);
//                addTaskDialog.show();
                showAddTaskDialog();
            }
        });

    }

    private void showAddTaskDialog() {
        final Dialog dialog = new Dialog(TasksActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        DialogAddTaskBinding binding = DataBindingUtil.setContentView(dialog, R.layout.dialog_add_task);
        dialog.setContentView(R.layout.dialog_add_task);

        final EditText title_et = dialog.findViewById(R.id.editTextTitle);
        final EditText date_et = dialog.findViewById(R.id.editTextDate);
        final MultiStateToggleButton toggleButton = dialog.findViewById(R.id.mstb_multi_id3);
        Button done = dialog.findViewById(R.id.buttonDialogAddTask);

        toggleButton.setColorRes(R.color.orange, R.color.white);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task(title_et.getText().toString(), date_et.getText().toString(),
                        toggleButton.getValue());
                tasksViewModel.addTaskToUser(Username, task);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tasks_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            if (!isFiltered) {
                tasksViewModel.filterTasksByDone();
                isFiltered = true;
            } else {
                tasksViewModel.showAllTasks();
                isFiltered = false;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

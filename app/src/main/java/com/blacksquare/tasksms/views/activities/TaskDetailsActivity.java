package com.blacksquare.tasksms.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blacksquare.tasksms.R;
import com.blacksquare.tasksms.databinding.ActivityTaskDetailsBinding;
import com.blacksquare.tasksms.models.Comment;
import com.blacksquare.tasksms.models.Task;
import com.blacksquare.tasksms.viewModels.TasksViewModel;
import com.blacksquare.tasksms.views.adapters.CommentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailsActivity extends AppCompatActivity {

    ActivityTaskDetailsBinding binding;
    TasksViewModel tasksViewModel;

    CommentsAdapter commentsAdapter;
    List<Comment> commentList;

    Task CurrentTask;
    boolean isDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_details);
        tasksViewModel = ViewModelProviders.of(this).get(TasksViewModel.class);

        CurrentTask = getIntent().getParcelableExtra("task");
        commentList = new ArrayList<>();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.toolbar.setTitle(CurrentTask.getTitle());
            binding.toolbar.setTitleTextColor(getResources().getColor(R.color.gray_text));
        }

        binding.textViewDate.setText(CurrentTask.getDate());
        binding.mstbMultiId.setValue(CurrentTask.getPriority());
        binding.mstbMultiId.setColorRes(R.color.orange, R.color.white);

        if (CurrentTask.isDone()) {
            isDone = true;
            binding.imageDone.setBackgroundResource(R.drawable.circle_done);
            binding.imageDone.setImageResource(R.drawable.ic_done);
        }

        binding.imageDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CurrentTask.isDone()) {
                    CurrentTask.setDone(false);
                    binding.imageDone.setBackgroundResource(R.drawable.circle);
                    tasksViewModel.updateTask(CurrentTask);
                } else {
                    CurrentTask.setDone(true);
                    binding.imageDone.setBackgroundResource(R.drawable.circle_done);
                    binding.imageDone.setImageResource(R.drawable.ic_done);
                    tasksViewModel.updateTask(CurrentTask);
                }
            }
        });

        commentsAdapter = new CommentsAdapter(this, commentList);
        binding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(TaskDetailsActivity.this));
        binding.recyclerViewComments.setAdapter(commentsAdapter);

        binding.buttonPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.editTextComment.getText().toString().equals("")) {
                    commentList.add(new Comment(binding.editTextComment.getText().toString()));
                    commentsAdapter.notifyDataSetChanged();
                    binding.textViewComments.setVisibility(View.GONE);
                    binding.editTextComment.setText("");
                } else {
                    binding.editTextComment.setError("no comment to add!");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

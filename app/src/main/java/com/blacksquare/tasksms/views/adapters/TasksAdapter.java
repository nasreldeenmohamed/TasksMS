package com.blacksquare.tasksms.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blacksquare.tasksms.R;
import com.blacksquare.tasksms.models.Task;
import com.blacksquare.tasksms.views.activities.TaskDetailsActivity;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {

    private Context context;
    private List<Task> TasksList;

    public TasksAdapter(Context con, List<Task> list) {
        context = con;
        this.TasksList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Task currentTask = TasksList.get(position);
        holder.date_tv.setText(currentTask.getDate());
        holder.title_tv.setText(currentTask.getTitle());

        if (currentTask.isDone()) {
            holder.isDone_img.setBackgroundResource(R.drawable.circle_done);
            holder.isDone_img.setImageResource(R.drawable.ic_done);
        }

        holder.isDone_img.setTag(position);
        holder.isDone_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TasksList.get((int) view.getTag()).isDone()) {
                    TasksList.get(((int) view.getTag())).setDone(false);
                    holder.isDone_img.setBackgroundResource(R.drawable.circle);
                } else {
                    TasksList.get(((int) view.getTag())).setDone(true);
                    holder.isDone_img.setBackgroundResource(R.drawable.circle_done);
                    holder.isDone_img.setImageResource(R.drawable.ic_done);
                }
            }
        });

        holder.toggleButton.setValue(currentTask.getPriority());
        holder.toggleButton.setColorRes(R.color.orange, R.color.white);

//        holder.toggleButton.setTag(currentTask.getID());
//        holder.toggleButton.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
//            @Override
//            public void onValueChanged(int value) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return TasksList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title_tv, date_tv;
        private CircleImageView isDone_img;
        private MultiStateToggleButton toggleButton;


        private MyViewHolder(View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.textTitle);
            date_tv = itemView.findViewById(R.id.textDate);
            isDone_img = itemView.findViewById(R.id.imageDone);
            toggleButton = itemView.findViewById(R.id.mstb_multi_id2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
//                    listener.onItemClick(TasksList.get(position));
                    Intent intent = new Intent(context, TaskDetailsActivity.class);
                    intent.putExtra("task", TasksList.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(TasksAdapter.OnItemClickListener listner) {
        OnItemClickListener listener = listner;
    }


}

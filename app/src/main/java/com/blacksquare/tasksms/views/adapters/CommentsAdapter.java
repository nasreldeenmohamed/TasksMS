package com.blacksquare.tasksms.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blacksquare.tasksms.R;
import com.blacksquare.tasksms.models.Comment;
import com.blacksquare.tasksms.models.Task;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    Context context;
    private List<Comment> CommentsList;

    public CommentsAdapter(Context con, List<Comment> list) {
        context = con;
        this.CommentsList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Comment comment = CommentsList.get(position);

        holder.title_tv.setText(comment.getTitle());

    }

    @Override
    public int getItemCount() {
        return CommentsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title_tv;


        public MyViewHolder(View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.textTitle);

        }
    }

}

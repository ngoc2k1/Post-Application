package com.bichngoc.jsonplaceholderapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bichngoc.jsonplaceholderapplication.data.model.CommentModel;
import com.bichngoc.jsonplaceholderapplication.databinding.ItemCommentBinding;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<CommentModel> list;
    private Context context;

    public CommentAdapter(List<CommentModel> list, Context context) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding itemBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentAdapter.CommentViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        CommentModel comment = list.get(position);
        holder.tvBodyComment.setText(comment.getBody() + "");
        holder.tvEmailComment.setText(comment.getEmail() + "");
        holder.tvNameComment.setText(comment.getName());
        holder.tvIdComment.setText(comment.getId() + "");
        holder.tvPostIdComment.setText(comment.getPostId() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView tvEmailComment;
        TextView tvIdComment;
        TextView tvNameComment;
        TextView tvPostIdComment;
        TextView tvBodyComment;

        public CommentViewHolder(ItemCommentBinding itemView) {
            super(itemView.getRoot());
            tvBodyComment = itemView.tvBodyComment;
            tvIdComment = itemView.tvIdComment;
            tvEmailComment = itemView.tvEmailComment;
            tvNameComment = itemView.tvNameComment;
            tvPostIdComment = itemView.tvPostIdComment;
        }
    }
}

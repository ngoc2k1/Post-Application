package com.bichngoc.jsonplaceholderapplication.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bichngoc.jsonplaceholderapplication.R;
import com.bichngoc.jsonplaceholderapplication.data.model.PostModel;
import com.bichngoc.jsonplaceholderapplication.databinding.ItemPostBinding;
import com.bichngoc.jsonplaceholderapplication.ui.listener.PostListener;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<PostModel> list;
    private Context context;
    private PostListener listener;

    public PostAdapter(List<PostModel> list, Context context, PostListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding itemBinding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PostModel post = list.get(position);
        holder.userid.setText(post.getUserId() + "");
        holder.id.setText(post.getId() + "");
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.clickListener(post.getId());
                }
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.clickEditListener(position);
                        }
                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.clickDeleteListener(position);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageButton delete;
        ImageButton edit;
        TextView userid;
        TextView id;
        TextView title;
        TextView body;

        public PostViewHolder(ItemPostBinding itemView) {
            super(itemView.getRoot());
            edit = itemView.imagebuttonEdit;
            delete = itemView.imagebuttonDelete;
            userid = itemView.tvUserid;
            id = itemView.tvId;
            title = itemView.tvTitle;
            body = itemView.tvBody;

        }
    }
}

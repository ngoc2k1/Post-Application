package com.bichngoc.jsonplaceholderapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bichngoc.jsonplaceholderapplication.data.model.CommentModel;
import com.bichngoc.jsonplaceholderapplication.data.model.PostModel;
import com.bichngoc.jsonplaceholderapplication.data.source.ApiClient;
import com.bichngoc.jsonplaceholderapplication.data.source.JsonPlaceHolderApi;
import com.bichngoc.jsonplaceholderapplication.databinding.ActivityEachPostBinding;
import com.bichngoc.jsonplaceholderapplication.databinding.ActivityMainBinding;
import com.bichngoc.jsonplaceholderapplication.ui.adapter.CommentAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EachPostActivity extends AppCompatActivity {

    JsonPlaceHolderApi apiInterface;
    ActivityEachPostBinding binding;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEachPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiInterface = ApiClient.getClientJsonPlaceHolder().create(JsonPlaceHolderApi.class);
        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra("id", 0);
            getPostById(id);
            getListCommentByPostId(id);
        }
    }

    /**
     * @param  id la id cua post
     *
     * */
    private void getPostById(int id) {
        Call<PostModel> call = apiInterface.fetchPost(id);
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()) {
                    PostModel post = response.body();
                    if (post != null) {
                        binding.tvId.setText(post.getId() + "");
                        binding.tvUserid.setText(post.getUserId() + "");
                        binding.tvTitle.setText(post.getTitle());
                        binding.tvBody.setText(post.getBody());
                    }

                } else {
                    Log.e("data", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {

            }
        });
    }

    private void getListCommentByPostId(int postId) {
        Call<List<CommentModel>> callComments = apiInterface.fetchPostComment(postId);
        callComments.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if (response.isSuccessful()) {
                    List<CommentModel> commentList = response.body();
                    if (commentList.size() > 0) {
                        commentAdapter = new CommentAdapter(commentList, EachPostActivity.this);
                        binding.rvComments.setAdapter(commentAdapter);
                    }
                } else {
                    // 301-500 v,v
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {

            }
        });

    }
}
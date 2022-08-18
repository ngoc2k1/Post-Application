package com.bichngoc.jsonplaceholderapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bichngoc.jsonplaceholderapplication.R;
import com.bichngoc.jsonplaceholderapplication.data.model.PostModel;
import com.bichngoc.jsonplaceholderapplication.data.source.ApiClient;
import com.bichngoc.jsonplaceholderapplication.data.source.JsonPlaceHolderApi;
import com.bichngoc.jsonplaceholderapplication.data.source.MockApi;
import com.bichngoc.jsonplaceholderapplication.databinding.ActivityMainBinding;
import com.bichngoc.jsonplaceholderapplication.ui.adapter.PostAdapter;
import com.bichngoc.jsonplaceholderapplication.ui.listener.PostListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    MockApi mockApi;
    JsonPlaceHolderApi apiJsonPlaceHolder;
    PostAdapter postAdapter;
    List<PostModel> postList;
    ActivityMainBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = this;
        binding.tvMainHead.setText(context.getResources().getString(R.string.app_name));
        mockApi = ApiClient.getClientMockApi().create(MockApi.class);

        // khởi tạo retrofit với 1 interface -> sd interface để gọi phương thức kết nối với url api còn lại
        apiJsonPlaceHolder = ApiClient.getClientJsonPlaceHolder().create(JsonPlaceHolderApi.class);
        getListPost();
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {//check trùng Id, k nhập đủ
                String userId = String.valueOf(binding.edtUserid.getText());
                String id = String.valueOf(binding.edtId.getText());
                String body = String.valueOf(binding.edtBody.getText());
                String title = String.valueOf(binding.edtTitle.getText());
                try {
                    int userIdInt = Integer.parseInt(userId);
                    int idInt = Integer.parseInt(id);
                    PostModel postModel = new PostModel(userIdInt, idInt, title, body);
                    postList.add(0, postModel);
                    postAdapter.notifyDataSetChanged();
                    binding.rvPosts.setAdapter(postAdapter);
                    binding.rvPosts.smoothScrollToPosition(0);

                } catch (Exception ignored) {
                    Toast.makeText(MainActivity.this, "Please enter number for userId and id", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

/*        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {//check trùng Id, k nhập đủ
                getListToDo("16");
            }
        });
    }

    private void getListToDo(String idDelete) {
        Call<ToDo> callToDo = mockApi.deleteToDo(idDelete);
        callToDo.enqueue(new Callback<ToDo>() {
            @Override
            public void onResponse(Call<ToDo> call, Response<ToDo> response) {
                if (response.isSuccessful()) {
                    ToDo todo = response.body();
                    if (todo instanceof ToDo && todo.getId().equals(idDelete)) {
                        Toast.makeText(MainActivity.this, "success delete", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "false delete", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "404 delete todo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ToDo> call, Throwable t) {
            }
        });
    }*/

    private void updatePost(int pos) {
        PostModel postModel = postList.get(pos);
        binding.edtBody.setText(postModel.getBody());
        binding.edtId.setText(postModel.getId() + "");
        binding.edtTitle.setText(postModel.getTitle());
        binding.edtUserid.setText(postModel.getUserId() + "");
    }

    private List<PostModel> getListPost() {
        Call<List<PostModel>> callPost = apiJsonPlaceHolder.getPostFromServer();
        callPost.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.isSuccessful()) {
                    postList = response.body();

                    postAdapter = new PostAdapter(postList, MainActivity.this, new PostListener() {
                        @Override
                        public void clickListener(int position) {
                            Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, EachPostActivity.class);
                            intent.putExtra("id", position);
                            startActivity(intent);
                        }

                        @Override
                        public void clickDeleteListener(int position) {
                            int id = postList.get(position).getId();
                            deletePostByClickItemView(id);
                        }

                        @Override
                        public void clickEditListener(int position) {
                            updatePost(position);
                        }
                    });
                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
                    binding.rvPosts.setLayoutManager(manager);
                    binding.rvPosts.setAdapter(postAdapter);
                } else {
                    Log.e("data", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Log.e("data", t.toString());
            }
        });
        return postList;
    }

    private void deletePostByClickItemView(int id) {
        Call<PostModel> callPost = apiJsonPlaceHolder.deletePost(id);
        callPost.enqueue(new Callback<PostModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()) {
                    PostModel post = response.body();
                    postList.remove(post);
                    postAdapter.notifyDataSetChanged();
                    binding.rvPosts.setAdapter(postAdapter);

//                    for (int position = 0; position < postList.size() - 1; position++) {
//                        PostModel post = postList.get(position);
//                        if (post.getId() == id) {
//                            postList.remove(post);
//                            postAdapter.notifyDataSetChanged();
//                            binding.rvPosts.setAdapter(postAdapter);
//                            break;
//                        }
//                    }

                } else {
                    Log.e("data", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Log.e("data", t.toString());
            }
        });
    }


    private void sendPost() {//create a post, lấy ra json có thêm post này không "delete/put"
        PostModel post = new PostModel(10, 101, "Ngọc", "Ngọc channel");
        Call<PostModel> call = apiJsonPlaceHolder.sendPost(post);
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                Toast.makeText(MainActivity.this, "Call api success by method POST", Toast.LENGTH_SHORT).show();
                PostModel result = response.body();
                if (result != null) {
                    binding.tvMainHead.setText(result.toString());
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {

            }
        });
    }
}
// delete,put,header

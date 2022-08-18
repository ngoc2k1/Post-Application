package com.bichngoc.jsonplaceholderapplication.data.source;

import com.bichngoc.jsonplaceholderapplication.data.model.CommentModel;
import com.bichngoc.jsonplaceholderapplication.data.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

//link api có base_url: https://jsonplaceholder.typicode.com/
public interface JsonPlaceHolderApi {
    /**
     * method call api, truyền vào List Object mà api trả về
     * method get ra list post /posts
     *  @return tra ve 1 List<CommentModel>
     */
    @GET("posts")
    Call<List<PostModel>> getPostFromServer();

    //method get ra 1 post khi truyền vào id (primary_key)    /posts/1
    @GET("posts/{id}")
    Call<PostModel> fetchPost(@Path("id") int id);

    //method get: lấy 1 list comment khi truyền vào postId   /posts/1/comments
    @GET("posts/{id}/comments")
    Call<List<CommentModel>> fetchPostComment(@Path("id") int id);

    //method get: lấy 1 list comment có postId = 1 trong tất cả các comment  /comments?postId=1
    @GET("comments")
    Call<List<CommentModel>> getListComment(@Query("postId") int postId);

    //method post lên server /posts
    // "https://google.com/post"
    @POST("posts")
    Call<PostModel> sendPost(@Body PostModel post);

    @DELETE("posts/{id}")// /posts/1
    Call<PostModel> deletePost(@Path("id") int id);

    @PUT("posts/{id}")// /posts/1
    Call<PostModel> updatePost(@Path("id") int id, @Body PostModel post);

     @GET("/")// method đặc biệt
     Call<PostModel> sendPost2();
}





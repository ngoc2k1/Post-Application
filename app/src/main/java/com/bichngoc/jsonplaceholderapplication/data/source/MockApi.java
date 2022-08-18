package com.bichngoc.jsonplaceholderapplication.data.source;

import com.bichngoc.jsonplaceholderapplication.data.model.ToDoModel;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface MockApi {
    @DELETE("/post/{id}")
    Call<ToDoModel> deleteToDo(@Path("id") String id);
}

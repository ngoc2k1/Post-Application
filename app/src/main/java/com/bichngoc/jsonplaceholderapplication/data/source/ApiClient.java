package com.bichngoc.jsonplaceholderapplication.data.source;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //init retrofit
    private static final String BASE_URL_PLACEHOLDER = "https://jsonplaceholder.typicode.com/";
    private static final String BASE_URL_TODO = "https://62fb5344abd610251c05db6e.mockapi.io/api/v1/";

    private static Retrofit retrofit = null;

    public static Retrofit getClientJsonPlaceHolder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_PLACEHOLDER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static Retrofit getClientMockApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_TODO)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}

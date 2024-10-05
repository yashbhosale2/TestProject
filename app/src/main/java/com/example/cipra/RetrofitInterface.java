package com.example.cipra;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("takehome/signin")
    Call<Void> login(@Query("email")String email,
                     @Query("password")String password);
}

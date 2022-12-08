package com.example.apihumans;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("Products")
    Call<DataModal> createPost(@Body DataModal dataModal);

}

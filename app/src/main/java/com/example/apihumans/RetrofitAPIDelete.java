package com.example.apihumans;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface RetrofitAPIDelete {
    @DELETE("Products/")
    Call<DataModal> deleteData(@Query("id")int Id);
}

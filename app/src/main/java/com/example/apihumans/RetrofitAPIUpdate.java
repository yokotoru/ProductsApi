package com.example.apihumans;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPIUpdate {
    @PUT("Products/")
    Call<DataModal> updateData(@Query("id")int Id, @Body DataModal dataModal);

}

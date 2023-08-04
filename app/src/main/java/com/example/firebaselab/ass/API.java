package com.example.firebaselab.ass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("planetary/apod")
    Call<NasaResponse> getNasa(@Query("api_key") String apiKey, @Query("date") String date);
}

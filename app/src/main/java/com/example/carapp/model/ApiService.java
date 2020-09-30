package com.example.carapp.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("availablecars")
    Call<List<Car>> getCars();
}

package com.example.carapp.network;

import com.example.carapp.model.Car;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("availablecars")
    Call<List<Car>> getCars();
}

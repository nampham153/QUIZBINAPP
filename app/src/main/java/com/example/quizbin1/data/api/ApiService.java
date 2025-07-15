package com.example.quizbin1.data.api;

import com.example.quizbin1.data.model.dto.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/User/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/User/register")
    Call<LoginResponse> register(@Body RegisterRequest request);
}

package com.example.quizbin1.data.api;

import com.example.quizbin1.data.model.dto.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.GET;

public interface ApiService {

    @POST("api/User/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/User/register")
    Call<LoginResponse> register(@Body RegisterRequest request);

    @GET("api/Subjects/user/{userId}")
    Call<List<SubjectDTO>> getSubjectsByUser(@Path("userId") String userId);
    @GET("api/Subject")
    Call<List<SubjectDTO>> getAllSubjects();
    @GET("api/Semester")
    Call<List<SemesterDTO>> getAllSemesters();

}

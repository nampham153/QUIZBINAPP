package com.example.quizbin1.data.model.dto;

import com.example.quizbin1.data.api.ApiService;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubjectRepository {

    private ApiService apiService;

    public SubjectRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://your_api_base_url/") // Thay bằng URL thật của API bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public Call<List<SubjectDTO>> getSubjectsByUser(UUID userId) {
        return apiService.getSubjectsByUser(userId.toString());
    }
}

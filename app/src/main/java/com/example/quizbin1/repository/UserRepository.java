    package com.example.quizbin1.repository;

    import com.example.quizbin1.data.api.ApiClient;
    import com.example.quizbin1.data.api.ApiService;
    import com.example.quizbin1.data.model.dto.*;

    import retrofit2.Call;

    public class UserRepository {
        private final ApiService apiService;

        public UserRepository() {
            apiService = ApiClient.getClient().create(ApiService.class);
        }

        public Call<LoginResponse> login(LoginRequest request) {
            return apiService.login(request);
        }

        public Call<LoginResponse> register(RegisterRequest request) {
            return apiService.register(request);
        }
    }


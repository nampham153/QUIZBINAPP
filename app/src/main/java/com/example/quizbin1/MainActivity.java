package com.example.quizbin1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.User;
import com.example.quizbin1.data.model.dto.LoginRequest;
import com.example.quizbin1.data.model.dto.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ApiService apiService;
    private static final String TAG = "API_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiClient.getClient().create(ApiService.class);

        // Test kết nối
        testApiConnection();
    }

    private void testApiConnection() {
        Log.d(TAG, "Bắt đầu test API connection...");

        // Tạo dữ liệu login mẫu
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("nam1");  // Thay bằng dữ liệu phù hợp
        loginRequest.setPasswordHash("123456");

        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "✅ KẾT NỐI THÀNH CÔNG!");
                    Log.d(TAG, "Response Code: " + response.code());
                    Log.d(TAG, "Data: " + response.body().toString());

                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Kết nối API thành công!", Toast.LENGTH_LONG).show();
                    });
                } else {
                    Log.e(TAG, "❌ LỖI RESPONSE: " + response.code());
                    Log.e(TAG, "Error Body: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "❌ KẾT NỐI THẤT BẠI: " + t.getMessage());
                Log.e(TAG, "Error: ", t);

                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}
package com.example.quizbin1.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizbin1.R;
import com.example.quizbin1.data.model.dto.LoginRequest;
import com.example.quizbin1.data.model.dto.LoginResponse;
import com.example.quizbin1.repository.UserRepository;
import com.example.quizbin1.ui.home.HomeActivity;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin, btnToRegister;
    UserRepository userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);
        userRepo = new UserRepository();

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginRequest request = new LoginRequest(username, password);
            userRepo.login(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        // ✅ Lấy userId dạng chuỗi UUID và truyền qua Intent
                        String userId = response.body().getUserId().toString();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish(); // Không quay lại LoginActivity
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
                }
            });
        });
        btnToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}

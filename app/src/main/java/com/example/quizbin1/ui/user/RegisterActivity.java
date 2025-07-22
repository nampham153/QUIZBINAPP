package com.example.quizbin1.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizbin1.R;
import com.example.quizbin1.data.model.dto.*;
import com.example.quizbin1.repository.UserRepository;
import com.example.quizbin1.ui.user.LoginActivity;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    EditText editTextRegisterConfirmPassword;
    Button btnRegister;
    TextView textViewLogin;
    UserRepository userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        editTextRegisterConfirmPassword = findViewById(R.id.editTextRegisterConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        textViewLogin = findViewById(R.id.textViewLogin);
        userRepo = new UserRepository();
        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btnRegister.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String confirmPassword = editTextRegisterConfirmPassword.getText().toString().trim();

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            UUID subjectId = UUID.fromString("28481261-dd77-4108-8817-4812cc951e93");
            RegisterRequest request = new RegisterRequest(username, password, subjectId);

            userRepo.register(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}

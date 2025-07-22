package com.example.quizbin1.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.quizbin1.MainActivity;
import com.example.quizbin1.R;
import com.example.quizbin1.ui.user.LoginActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchDarkMode;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_DARK_MODE = "dark_mode";

    private String userIdString;
    private String role, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Lấy dữ liệu từ Intent
        username = getIntent().getStringExtra("username");
        userIdString = getIntent().getStringExtra("userId");
        role = getIntent().getStringExtra("role");

        // Ánh xạ View
        TextView tvEditProfile = findViewById(R.id.tvEditProfile);
        TextView tvChangePassword = findViewById(R.id.tvChangePassword);
        TextView tvTerms = findViewById(R.id.tvTerms);
        TextView tvLogout = findViewById(R.id.tvLogout);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        Button btnBackToHome = findViewById(R.id.btnBackToHome); // ✅ Nút quay về

        // Dark mode switch setup
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false);
        switchDarkMode.setChecked(isDarkMode);
        updateDarkMode(isDarkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
            updateDarkMode(isChecked);
        });

        // Sự kiện nút chức năng
        tvEditProfile.setOnClickListener(v -> {
            if (userIdString != null) {
                Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                intent.putExtra("userId", userIdString);
                intent.putExtra("role", role);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Không tìm thấy userId", Toast.LENGTH_SHORT).show();
            }
        });

        tvChangePassword.setOnClickListener(v -> {
            if (userIdString != null && role != null) {
                Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            }
        });

        tvTerms.setOnClickListener(v -> {
            Intent intent = new Intent(this, TermsActivity.class);
            startActivity(intent);
        });

        tvLogout.setOnClickListener(v -> logout());

        // ✅ Quay lại MainActivity → tab Home
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.putExtra("userId", userIdString);
            intent.putExtra("role", role);
            intent.putExtra("username", username);
            intent.putExtra("navigateTo", "home"); // tab cần hiển thị
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // kết thúc SettingsActivity
        });
    }

    private void updateDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void logout() {
        Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

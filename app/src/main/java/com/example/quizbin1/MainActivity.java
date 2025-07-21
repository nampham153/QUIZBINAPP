package com.example.quizbin1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.quizbin1.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private String userId = "sample-user-id";
    private String role = "student";
    private String username = "username123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            role = getIntent().getStringExtra("role");
            username = getIntent().getStringExtra("username");
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment.newInstance(userId, role, username))
                    .commit();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int id = item.getItemId();
            if (id == R.id.nav_home) {
                selectedFragment = HomeFragment.newInstance(userId, role, username);
            } else if (id == R.id.nav_create) {
                // Tạo fragment CreateFragment tương tự
                selectedFragment = new com.example.quizbin1.ui.home.CreateFragment();
            } else if (id == R.id.nav_library) {
                selectedFragment = new com.example.quizbin1.ui.home.LibraryFragment();
            } else if (id == R.id.nav_premium) {
                selectedFragment = new com.example.quizbin1.ui.home.PremiumFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });
    }
}

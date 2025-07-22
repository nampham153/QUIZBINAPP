package com.example.quizbin1;

import android.os.Bundle;
import com.example.quizbin1.ui.subject.SubjectListFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.quizbin1.ui.home.HomeFragment;
import com.example.quizbin1.ui.home.LibraryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private String userId;
    private String role;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy dữ liệu intent
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            role = getIntent().getStringExtra("role");
            username = getIntent().getStringExtra("username");
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        // Listener cho BottomNavigationView
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                selectedFragment = HomeFragment.newInstance(userId, role, username);
            } else if (id == R.id.nav_create) {
                selectedFragment = new com.example.quizbin1.ui.subject.SubjectListFragment();
            } else if (id == R.id.nav_library) {
                selectedFragment = LibraryFragment.newInstance(userId, role, username); // ✅ SỬA ĐÚNG Ở ĐÂY
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

        if (savedInstanceState == null) {
            String navigateTo = getIntent().getStringExtra("navigateTo");

            int defaultTabId = R.id.nav_home;

            if (navigateTo != null) {
                switch (navigateTo) {
                    case "home":
                        defaultTabId = R.id.nav_home;
                        break;
                    case "create":
                        defaultTabId = R.id.nav_create;
                        break;
                    case "library":
                        defaultTabId = R.id.nav_library;
                        break;
                    case "premium":
                        defaultTabId = R.id.nav_premium;
                        break;
                }
            }
            bottomNav.setSelectedItemId(defaultTabId);
        }
    }
}

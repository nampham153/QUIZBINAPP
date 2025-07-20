package com.example.quizbin1.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.SubjectDTO;
import com.example.quizbin1.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private List<SubjectDTO> subjectList = new ArrayList<>();
    private ApiService apiService;
    private String userIdString;
    private String role;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageView imgAvatar = findViewById(R.id.imgAvatar);
        username = getIntent().getStringExtra("username");
        imgAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            intent.putExtra("userId", userIdString);
            intent.putExtra("role", role);
            intent.putExtra("username", username);
            startActivity(intent);
        });
        userIdString = getIntent().getStringExtra("userId");
        role = getIntent().getStringExtra("role");

        if (userIdString == null || role == null) {
            Toast.makeText(this, "Không tìm thấy userId hoặc role!", Toast.LENGTH_SHORT).show();
            return;
        }

        recyclerView = findViewById(R.id.recyclerSubjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SubjectAdapter(this, subjectList);
        recyclerView.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        loadSubjects(role);
    }

    private void loadSubjects(String role) {
        Call<List<SubjectDTO>> call = apiService.getAllSubjects();
        call.enqueue(new Callback<List<SubjectDTO>>() {
            @Override
            public void onResponse(Call<List<SubjectDTO>> call, Response<List<SubjectDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SubjectDTO> allSubjects = response.body();

                    subjectList.clear();

                    if ("student".equalsIgnoreCase(role)) {
                        // Sinh viên xem tất cả môn học
                        subjectList.addAll(allSubjects);
                    } else if ("teacher".equalsIgnoreCase(role)) {
                        // Giáo viên chỉ xem môn học do họ tạo
                        UUID userId = UUID.fromString(userIdString);
                        for (SubjectDTO subject : allSubjects) {
                            if (subject.getUserId() != null && subject.getUserId().equals(userId)) {
                                subjectList.add(subject);
                            }
                        }
                    } else {
                        // Role khác hoặc không xác định, bạn có thể tùy chỉnh
                        subjectList.addAll(allSubjects);
                    }

                    adapter.notifyDataSetChanged();

                    if (subjectList.isEmpty()) {
                        Toast.makeText(HomeActivity.this, "Không có học phần nào!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Lỗi khi lấy danh sách học phần", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SubjectDTO>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi kết nối máy chủ: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });
    }
}

package com.example.quizbin1.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.SubjectDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private List<SubjectDTO> subjectList = new ArrayList<>(); // ✅ Khởi tạo list ở đây
    private ApiService apiService;
    private String userIdString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ✅ Lấy userId từ Intent
        userIdString = getIntent().getStringExtra("userId");
        if (userIdString == null) {
            Toast.makeText(this, "Không tìm thấy userId!", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Ánh xạ RecyclerView và cài LayoutManager
        recyclerView = findViewById(R.id.recyclerSubjects); // ID trong XML phải đúng
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Khởi tạo adapter với danh sách rỗng và gắn vào RecyclerView
        adapter = new SubjectAdapter(this, subjectList);
        recyclerView.setAdapter(adapter);

        // ✅ Khởi tạo Retrofit API service
        apiService = ApiClient.getClient().create(ApiService.class);

        // ✅ Gọi API để tải danh sách môn học
        loadSubjects();
    }

    private void loadSubjects() {
        Call<List<SubjectDTO>> call = apiService.getAllSubjects();
        call.enqueue(new Callback<List<SubjectDTO>>() {
            @Override
            public void onResponse(Call<List<SubjectDTO>> call, Response<List<SubjectDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UUID userId = UUID.fromString(userIdString);
                    List<SubjectDTO> allSubjects = response.body();

                    subjectList.clear(); // Xoá danh sách cũ

                    // ✅ Lọc các học phần theo userId
                    for (SubjectDTO subject : allSubjects) {
                        if (subject.getUserId() != null && subject.getUserId().equals(userId)) {
                            subjectList.add(subject);
                        }
                    }

                    adapter.notifyDataSetChanged(); // Cập nhật giao diện

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

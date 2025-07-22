package com.example.quizbin1.ui.semester;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbin1.MainActivity;
import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.SemesterDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SemesterActivity extends AppCompatActivity {

    private RecyclerView rvSemesters;
    private SemesterAdapter adapter;
    private ApiService apiService;
    private String subjectId;

    private String userId, role, username;

    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        Toast.makeText(this, "Mở SemesterActivity", Toast.LENGTH_SHORT).show();

        // Lấy dữ liệu từ Intent
        subjectId = getIntent().getStringExtra("subjectId");
        userId = getIntent().getStringExtra("userId");
        role = getIntent().getStringExtra("role");
        username = getIntent().getStringExtra("username");

        Log.d("SemesterActivity", "subjectId nhận được: " + subjectId);

        rvSemesters = findViewById(R.id.rvSemesters);
        rvSemesters.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SemesterAdapter(this);
        rvSemesters.setAdapter(adapter);

        if (subjectId != null) {
            apiService = ApiClient.getClient().create(ApiService.class);
            loadSemesters(subjectId);
        } else {
            Toast.makeText(this, "Không tìm thấy subjectId", Toast.LENGTH_SHORT).show();
        }

        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(v -> {
            Toast.makeText(this, "Quay lại trang chủ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SemesterActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("role", role);
            intent.putExtra("username", username);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }




    private void loadSemesters(String subjectId) {
        apiService.getAllSemesters().enqueue(new Callback<List<SemesterDTO>>() {
            @Override
            public void onResponse(Call<List<SemesterDTO>> call, Response<List<SemesterDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SemesterDTO> filtered = new ArrayList<>();
                    Log.d("DEBUG", "Tổng số học kỳ từ API: " + response.body().size());

                    for (SemesterDTO semester : response.body()) {
                        Log.d("DEBUG", "semester.getSemesterName(): " + semester.getSemesterName());
                        Log.d("DEBUG", "semester.getSubjectId(): " + semester.getSubjectId());
                        Log.d("DEBUG", "intent subjectId: " + subjectId);

                        if (semester.getSubjectId() != null &&
                                String.valueOf(semester.getSubjectId()).equals(subjectId)) {
                            filtered.add(semester);
                        }
                    }

                    Log.d("SemesterActivity", "Số học kỳ sau lọc: " + filtered.size());

                    if (filtered.isEmpty()) {
                        Toast.makeText(SemesterActivity.this, "Không có học kỳ nào phù hợp với subjectId", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setSemesterList(filtered);
                    }
                } else {
                    Toast.makeText(SemesterActivity.this, "Không có dữ liệu học kỳ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SemesterDTO>> call, Throwable t) {
                Toast.makeText(SemesterActivity.this, "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

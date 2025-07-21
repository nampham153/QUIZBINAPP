package com.example.quizbin1.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.SubjectDTO;
import com.example.quizbin1.ui.semester.SemesterActivity;
import com.example.quizbin1.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private List<SubjectDTO> subjectList = new ArrayList<>();
    private ApiService apiService;

    private String userIdString;
    private String role;
    private String username;

    public HomeFragment() {
        // Required empty public constructor
    }

    // Tạo factory method để truyền dữ liệu vào fragment
    public static HomeFragment newInstance(String userId, String role, String username) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("role", role);
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerSubjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SubjectAdapter();
        adapter.setSubjectList(subjectList);
        recyclerView.setAdapter(adapter);

        ImageView imgAvatar = view.findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            intent.putExtra("userId", userIdString);
            intent.putExtra("role", role);
            intent.putExtra("username", username);
            startActivity(intent);
        });
        adapter.setOnItemClickListener(subject -> {
            // Ví dụ chuyển sang Activity Semester, truyền subjectId
            Intent intent = new Intent(getContext(), SemesterActivity.class);
            intent.putExtra("subjectId", subject.getSubjectId().toString());
            startActivity(intent);
        });

        if (getArguments() != null) {
            userIdString = getArguments().getString("userId");
            role = getArguments().getString("role");
            username = getArguments().getString("username");
        }

        if (userIdString == null || role == null) {
            Toast.makeText(getContext(), "Không tìm thấy userId hoặc role!", Toast.LENGTH_SHORT).show();
        } else {
            apiService = ApiClient.getClient().create(ApiService.class);
            loadSubjects(role);
        }

        return view;
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
                        subjectList.addAll(allSubjects);
                    } else if ("teacher".equalsIgnoreCase(role)) {
                        UUID userId = UUID.fromString(userIdString);
                        for (SubjectDTO subject : allSubjects) {
                            if (subject.getUserId() != null && subject.getUserId().equals(userId)) {
                                subjectList.add(subject);
                            }
                        }
                    } else {
                        subjectList.addAll(allSubjects);
                    }

                    adapter.notifyDataSetChanged();

                    if (subjectList.isEmpty()) {
                        Toast.makeText(getContext(), "Không có học phần nào!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi khi lấy danh sách học phần", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SubjectDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối máy chủ: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });
    }
}

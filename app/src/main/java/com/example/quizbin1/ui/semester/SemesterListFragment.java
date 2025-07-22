package com.example.quizbin1.ui.semester;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.SemesterDTO;
import com.example.quizbin1.ui.question.QuestionListFragment;
import com.example.quizbin1.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SemesterListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SemesterAdapter adapter;
    private List<SemesterDTO> semesterList = new ArrayList<>();
    private ApiService apiService;
    private ImageButton btnAddSemester;
    private String subjectId;
    private String role;

    public SemesterListFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_semester_list, container, false);

        recyclerView = view.findViewById(R.id.semesterRecyclerView);
        btnAddSemester = view.findViewById(R.id.btnAddSemester);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        role = SharedPrefManager.getInstance(getContext()).getRole();

        adapter = new SemesterAdapter(getContext(), role, semesterId -> {
            if ("teacher".equalsIgnoreCase(role)) {
                QuestionListFragment questionFragment = new QuestionListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("semesterId", semesterId.toString());
                questionFragment.setArguments(bundle);

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, questionFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setAdapter(adapter);
        apiService = ApiClient.getApiService();

        subjectId = getArguments() != null ? getArguments().getString("subjectId") : null;

        if (subjectId == null || subjectId.isEmpty()) {
            Toast.makeText(getContext(), "Không tìm thấy SubjectId", Toast.LENGTH_SHORT).show();
        } else {
            try {
                UUID subjectUUID = UUID.fromString(subjectId);
                loadSemesters(subjectUUID);
            } catch (IllegalArgumentException e) {
                Toast.makeText(getContext(), "SubjectId không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }

        if ("teacher".equalsIgnoreCase(role)) {
            btnAddSemester.setVisibility(View.VISIBLE);
            btnAddSemester.setOnClickListener(v -> {
                CreateSemesterFragment createFragment = new CreateSemesterFragment();
                Bundle bundle = new Bundle();
                bundle.putString("subjectId", subjectId);
                createFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, createFragment)
                        .addToBackStack(null)
                        .commit();
            });
            ImageButton btnBack = view.findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        } else {
            btnAddSemester.setVisibility(View.GONE);
        }

        return view;
    }

    private void loadSemesters(UUID subjectUUID) {
        Call<List<SemesterDTO>> call = apiService.getAllSemesterBySubject(subjectUUID);
        call.enqueue(new Callback<List<SemesterDTO>>() {
            @Override
            public void onResponse(Call<List<SemesterDTO>> call, Response<List<SemesterDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    semesterList.clear();
                    semesterList.addAll(response.body());
                    adapter.setSemesterList(semesterList);
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải học kỳ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SemesterDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

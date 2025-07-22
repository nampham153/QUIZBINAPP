package com.example.quizbin1.ui.subject;

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
import com.example.quizbin1.data.model.dto.SubjectDTO;
import com.example.quizbin1.ui.home.SubjectAdapter;
import com.example.quizbin1.ui.semester.SemesterListFragment;
import com.example.quizbin1.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private List<SubjectDTO> subjectList = new ArrayList<>();
    private ApiService apiService;
    private ImageButton btnAddSubject;

    private String userIdStr;

    public SubjectListFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_list, container, false);

        recyclerView = view.findViewById(R.id.subjectRecyclerView);
        btnAddSubject = view.findViewById(R.id.btnAddSubject);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo adapter với list trống, gán callback click item
        adapter = new SubjectAdapter();
        adapter.setSubjectList(subjectList);

        adapter.setOnItemClickListener(subject -> {
            //chuyển sang SemesterListFragment kèm subjectId
            SemesterListFragment semesterListFragment = new SemesterListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("subjectId", subject.getSubjectId().toString());
            semesterListFragment.setArguments(bundle);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, semesterListFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(adapter);

        // lấy userId từ SharedPrefManager
        userIdStr = SharedPrefManager.getInstance(getContext()).getUserId();

        if (userIdStr == null || userIdStr.isEmpty()) {
            Toast.makeText(getContext(), "User chưa đăng nhập hoặc userId không hợp lệ", Toast.LENGTH_SHORT).show();
        } else {
            loadSubjectsByTeacher(userIdStr);
        }

        btnAddSubject.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new CreateFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void loadSubjectsByTeacher(String teacherUserId) {
        apiService = ApiClient.getApiService();

        try {
            UUID.fromString(teacherUserId);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "UserId không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<SubjectDTO>> call = apiService.getAllSubjectByTeacher(teacherUserId);
        call.enqueue(new Callback<List<SubjectDTO>>() {
            @Override
            public void onResponse(Call<List<SubjectDTO>> call, Response<List<SubjectDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    subjectList.clear();
                    subjectList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    if (subjectList.isEmpty()) {
                        Toast.makeText(getContext(), "Bạn chưa có học phần nào!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải học phần", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SubjectDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.quizbin1.ui.subject;

import android.os.Bundle;
import android.view.*;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import com.example.quizbin1.R;
import com.example.quizbin1.data.model.dto.SubjectDTO;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.ui.home.SubjectAdapter;
import com.example.quizbin1.utils.SharedPrefManager;

import java.util.*;

import retrofit2.*;

public class SubjectFragment extends Fragment {

    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;
    private List<SubjectDTO> subjectList = new ArrayList<>();
    private ImageButton btnAddSubject;

    private String userId;
    private String role;

    public SubjectFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subject, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.subjectRecyclerView);
        btnAddSubject = view.findViewById(R.id.btnAddSubject);

        subjectAdapter = new SubjectAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(subjectAdapter);

        userId = SharedPrefManager.getInstance(getContext()).getUserId();
        role = SharedPrefManager.getInstance(getContext()).getRole();

        if (!"teacher".equals(role)) {
            btnAddSubject.setVisibility(View.GONE);
        }


        fetchSubjects();
    }

    private void fetchSubjects() {
        ApiService apiService = ApiClient.getApiService();
        apiService.getAllSubjectByTeacher(userId).enqueue(new Callback<List<SubjectDTO>>() {
            @Override
            public void onResponse(Call<List<SubjectDTO>> call, Response<List<SubjectDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    subjectList.clear();
                    subjectList.addAll(response.body());
                    subjectAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SubjectDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

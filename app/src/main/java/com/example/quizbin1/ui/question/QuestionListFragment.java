package com.example.quizbin1.ui.question;

import android.os.Bundle;
import android.view.*;
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
import com.example.quizbin1.data.model.dto.QuestionDTO;
import com.example.quizbin1.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionListFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private List<QuestionDTO> questionList = new ArrayList<>();
    private ApiService apiService;
    private String semesterId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_list, container, false);

        recyclerView = view.findViewById(R.id.questionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new QuestionAdapter(getContext());
        recyclerView.setAdapter(adapter);

        apiService = ApiClient.getApiService();
        ImageButton btnAddQuestion = view.findViewById(R.id.btnAddQuestion);

        // kiểm tra role cho phép tạo
        // truyền semesterId sang tạo câu hỏi
        String role = SharedPrefManager.getInstance(getContext()).getRole();
        if ("teacher".equalsIgnoreCase(role)) {
            btnAddQuestion.setVisibility(View.VISIBLE);
            btnAddQuestion.setOnClickListener(v -> {
                CreateQuestionFragment createQuestionFragment = new CreateQuestionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("semesterId", semesterId);
                createQuestionFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, createQuestionFragment)
                        .addToBackStack(null)
                        .commit();
            });
            ImageButton btnBack = view.findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        } else {
            btnAddQuestion.setVisibility(View.GONE);
        }
        // lấy semesterId truyền từ ngoài vào
        semesterId = getArguments() != null ? getArguments().getString("semesterId") : null;

        if (semesterId == null || semesterId.isEmpty()) {
            Toast.makeText(getContext(), "Không tìm thấy SemesterId", Toast.LENGTH_SHORT).show();
        } else {
            try {
                UUID semesterUUID = UUID.fromString(semesterId);
                loadQuestions(semesterUUID);
            } catch (IllegalArgumentException e) {
                Toast.makeText(getContext(), "SemesterId không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }

    private void loadQuestions(UUID semesterUUID) {
        Call<List<QuestionDTO>> call = apiService.getQuestionsBySemesterId(semesterUUID);
        call.enqueue(new Callback<List<QuestionDTO>>() {
            @Override
            public void onResponse(Call<List<QuestionDTO>> call, Response<List<QuestionDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questionList.clear();
                    questionList.addAll(response.body());
                    adapter.setQuestionList(questionList);

                    if (questionList.isEmpty()) {
                        Toast.makeText(getContext(), "Chưa có câu hỏi nào cho học kỳ này", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi khi tải câu hỏi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<QuestionDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

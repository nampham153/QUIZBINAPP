package com.example.quizbin1.ui.semester;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.AddSemesterRequestDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSemesterFragment extends Fragment {

    private EditText edtSemesterName;
    private Button btnCreateSemester;
    private ApiService apiService;

    // Nếu bạn có SubjectId truyền vào thì dùng biến này
    private String subjectIdStr;

    public CreateSemesterFragment() {}

    public static CreateSemesterFragment newInstance(String subjectId) {
        CreateSemesterFragment fragment = new CreateSemesterFragment();
        Bundle args = new Bundle();
        args.putString("subjectId", subjectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_semester, container, false);

        edtSemesterName = view.findViewById(R.id.edtSemesterName);
        btnCreateSemester = view.findViewById(R.id.btnCreateSemester);

        apiService = ApiClient.getApiService();

        if (getArguments() != null) {
            subjectIdStr = getArguments().getString("subjectId");
        }

        btnCreateSemester.setOnClickListener(v -> createSemester());

        return view;
    }

    private void createSemester() {
        String semesterName = edtSemesterName.getText().toString().trim();

        if (TextUtils.isEmpty(semesterName)) {
            Toast.makeText(getContext(), "Please enter semester name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (subjectIdStr == null || subjectIdStr.isEmpty()) {
            Toast.makeText(getContext(), "Subject ID not found", Toast.LENGTH_SHORT).show();
            return;
        }

        AddSemesterRequestDTO request = new AddSemesterRequestDTO();
        request.setSemesterName(semesterName);
        request.setSubjectId(java.util.UUID.fromString(subjectIdStr));

        Call<Void> call = apiService.addSemester(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Semester created successfully!", Toast.LENGTH_SHORT).show();
                    // Quay về màn trước (SubjectListFragment hoặc SemesterListFragment)
                    if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                        getParentFragmentManager().popBackStack();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to create semester", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

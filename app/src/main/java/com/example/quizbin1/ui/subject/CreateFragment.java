package com.example.quizbin1.ui.subject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.AddSubjectRequestDTO;
import com.example.quizbin1.utils.SharedPrefManager;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFragment extends Fragment {

    private EditText edtSubjectName, edtSubjectDetail, edtUrlImage, edtVideoUrl;
    private Button btnCreate;
    private ApiService apiService;

    public CreateFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        edtSubjectName = view.findViewById(R.id.edtSubjectName);
        edtSubjectDetail = view.findViewById(R.id.edtSubjectDetail);
        edtUrlImage = view.findViewById(R.id.edtUrlImage);
        edtVideoUrl = view.findViewById(R.id.edtVideoUrl);
        btnCreate = view.findViewById(R.id.btnCreateSubject);

        apiService = ApiClient.getApiService();

        btnCreate.setOnClickListener(v -> createSubject());

        return view;
    }

    private void createSubject() {
        String name = edtSubjectName.getText().toString().trim();
        String detail = edtSubjectDetail.getText().toString().trim();
        String imageUrl = edtUrlImage.getText().toString().trim();
        String videoUrl = edtVideoUrl.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(detail)
                || TextUtils.isEmpty(imageUrl) || TextUtils.isEmpty(videoUrl)) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy userId từ SharedPrefManager (đã lưu UUID string)
        String userIdStr = SharedPrefManager.getInstance(getContext()).getUserId();
        UUID userId;
        try {
            userId = UUID.fromString(userIdStr);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Invalid user ID", Toast.LENGTH_SHORT).show();
            return;
        }

        AddSubjectRequestDTO request = new AddSubjectRequestDTO(
                name, detail, imageUrl, videoUrl, userId
        );

        Call<Void> call = apiService.createSubject(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Subject created successfully!", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new SubjectListFragment())
                            .commit();

                } else {
                    Toast.makeText(getContext(), "Failed to create subject", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

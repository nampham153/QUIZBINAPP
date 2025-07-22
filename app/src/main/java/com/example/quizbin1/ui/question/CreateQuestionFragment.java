package com.example.quizbin1.ui.question;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.AddOptionRequestDTO;
import com.example.quizbin1.data.model.dto.AddQuestionRequestDTO;
import com.example.quizbin1.data.model.dto.OptionDTO;
import com.example.quizbin1.data.model.dto.QuestionDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateQuestionFragment extends Fragment {

    private EditText etQuestionContent, etOption1, etOption2, etOption3, etOption4;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Button btnCreateQuestion;

    private ApiService apiService;
    private String semesterId;
    private int selectedOptionIndex = -1; // Lưu trữ đáp án được chọn

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo views
        etQuestionContent = view.findViewById(R.id.etQuestionContent);
        etOption1 = view.findViewById(R.id.etOption1);
        etOption2 = view.findViewById(R.id.etOption2);
        etOption3 = view.findViewById(R.id.etOption3);
        etOption4 = view.findViewById(R.id.etOption4);

        rbOption1 = view.findViewById(R.id.rbOption1);
        rbOption2 = view.findViewById(R.id.rbOption2);
        rbOption3 = view.findViewById(R.id.rbOption3);
        rbOption4 = view.findViewById(R.id.rbOption4);

        btnCreateQuestion = view.findViewById(R.id.btnCreateQuestion);

        apiService = ApiClient.getClient().create(ApiService.class);

        if (getArguments() != null) {
            semesterId = getArguments().getString("semesterId");
        }

        // Thiết lập listener cho các radio button
        setupRadioButtonListeners();

        btnCreateQuestion.setOnClickListener(v -> createQuestionWithOptions());
    }

    private void setupRadioButtonListeners() {
        rbOption1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedOptionIndex = 0;
                clearOtherRadioButtons(rbOption1);
            }
        });

        rbOption2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedOptionIndex = 1;
                clearOtherRadioButtons(rbOption2);
            }
        });

        rbOption3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedOptionIndex = 2;
                clearOtherRadioButtons(rbOption3);
            }
        });

        rbOption4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedOptionIndex = 3;
                clearOtherRadioButtons(rbOption4);
            }
        });
    }

    private void clearOtherRadioButtons(RadioButton except) {
        if (except != rbOption1) rbOption1.setChecked(false);
        if (except != rbOption2) rbOption2.setChecked(false);
        if (except != rbOption3) rbOption3.setChecked(false);
        if (except != rbOption4) rbOption4.setChecked(false);
    }

    private void createQuestionWithOptions() {
        String questionContent = etQuestionContent.getText().toString().trim();
        String opt1 = etOption1.getText().toString().trim();
        String opt2 = etOption2.getText().toString().trim();
        String opt3 = etOption3.getText().toString().trim();
        String opt4 = etOption4.getText().toString().trim();

        // Kiểm tra input
        if (questionContent.isEmpty() || opt1.isEmpty() || opt2.isEmpty() || opt3.isEmpty() || opt4.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ câu hỏi và đáp án", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedOptionIndex == -1) {
            Toast.makeText(getContext(), "Vui lòng chọn đáp án đúng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo danh sách options
        List<OptionDTO> options = new ArrayList<>();
        options.add(new OptionDTO(null, null, opt1, selectedOptionIndex == 0));
        options.add(new OptionDTO(null, null, opt2, selectedOptionIndex == 1));
        options.add(new OptionDTO(null, null, opt3, selectedOptionIndex == 2));
        options.add(new OptionDTO(null, null, opt4, selectedOptionIndex == 3));

        createQuestionWithOptionsApi(semesterId, questionContent, options);
    }

    private void createQuestionWithOptionsApi(String semesterId, String questionContent, List<OptionDTO> optionList) {
        AddQuestionRequestDTO questionRequest = new AddQuestionRequestDTO(semesterId, questionContent);

        apiService.createQuestion(questionRequest).enqueue(new Callback<QuestionDTO>() {
            @Override
            public void onResponse(Call<QuestionDTO> call, Response<QuestionDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UUID createdQuestionIdUuid = response.body().getQuestionId();
                    String createdQuestionId = createdQuestionIdUuid.toString();
                    createOptionSequentially(createdQuestionId, optionList, 0);                     // Gửi từng option lần lượt
                } else {
                    Toast.makeText(getContext(), "Lỗi tạo câu hỏi: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuestionDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createOptionSequentially(String questionId, List<OptionDTO> options, int index) {
        if (index >= options.size()) {
            Toast.makeText(getContext(), "Tạo câu hỏi thành công!", Toast.LENGTH_SHORT).show();
            resetForm();
            return;
        }

        OptionDTO option = options.get(index);
        AddOptionRequestDTO optionRequest = new AddOptionRequestDTO(questionId, option.getContent(), option.isCorrect());

        apiService.createOption(optionRequest).enqueue(new Callback<OptionDTO>() {
            @Override
            public void onResponse(Call<OptionDTO> call, Response<OptionDTO> response) {
                if (response.isSuccessful()) {
                    createOptionSequentially(questionId, options, index + 1);  // để tiếp tục với option tiếp theo
                } else {
                    Log.e("API", "Lỗi tạo option: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OptionDTO> call, Throwable t) {
                Log.e("API", "Lỗi kết nối option: " + t.getMessage());
            }
        });
    }


    private void resetForm() {
        etQuestionContent.setText("");
        etOption1.setText("");
        etOption2.setText("");
        etOption3.setText("");
        etOption4.setText("");

        rbOption1.setChecked(false);
        rbOption2.setChecked(false);
        rbOption3.setChecked(false);
        rbOption4.setChecked(false);

        selectedOptionIndex = -1;
    }
}

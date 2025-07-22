package com.example.quizbin1.ui.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizbin1.R;
import com.example.quizbin1.data.api.ApiClient;
import com.example.quizbin1.data.api.ApiService;
import com.example.quizbin1.data.model.dto.OptionDTO;
import com.example.quizbin1.data.model.dto.QuestionDTO;
import com.example.quizbin1.ui.result.ResultActivity;
import com.example.quizbin1.utils.GlobalDataHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizDoingActivity extends AppCompatActivity {

    private TextView tvQuestionNumber, tvQuestionContent;
    private RadioGroup radioGroupOptions;
    private Button btnPrevious, btnNext, btnSubmit;

    private List<QuestionDTO> questionList = new ArrayList<>();
    private int currentIndex = 0;
    private final List<UUID> selectedOptionIds = new ArrayList<>();
    private final Map<UUID, List<OptionDTO>> optionMap = new HashMap<>();

    private ApiService apiService;
    private String semesterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_doing);

        semesterId = getIntent().getStringExtra("semesterId");

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestionContent = findViewById(R.id.tvQuestionContent);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);

        apiService = ApiClient.getClient().create(ApiService.class);

        loadQuestions();

        btnPrevious.setOnClickListener(v -> {
            saveSelectedOption();
            if (currentIndex > 0) {
                currentIndex--;
                displayQuestion(currentIndex);
            }
        });

        btnNext.setOnClickListener(v -> {
            saveSelectedOption();
            if (currentIndex < questionList.size() - 1) {
                currentIndex++;
                displayQuestion(currentIndex);
            }
        });

        btnSubmit.setOnClickListener(v -> {
            saveSelectedOption();
            int correct = 0;
            for (int i = 0; i < questionList.size(); i++) {
                UUID selectedId = selectedOptionIds.get(i);
                List<OptionDTO> options = optionMap.get(questionList.get(i).getQuestionId());
                if (selectedId == null || options == null) continue;

                for (OptionDTO option : options) {
                    if (option.getOptionId().equals(selectedId) && option.isCorrect()) {
                        correct++;
                        break;
                    }
                }
            }

            int total = questionList.size();
            int wrong = total - correct;
            int score = correct * 10;

            // Truyền dữ liệu sang ResultActivity
            GlobalDataHolder.questionList = questionList;
            GlobalDataHolder.optionMap = optionMap;
            GlobalDataHolder.selectedOptionIds = selectedOptionIds;

            Intent intent = new Intent(QuizDoingActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("correct", correct);
            intent.putExtra("wrong", wrong);
            startActivity(intent);
            finish();
        });
    }

    private void loadQuestions() {
        apiService.getQuestionsBySemesterId(UUID.fromString(semesterId)).enqueue(new Callback<List<QuestionDTO>>() {
            @Override
            public void onResponse(Call<List<QuestionDTO>> call, Response<List<QuestionDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questionList = response.body();
                    selectedOptionIds.clear();
                    for (QuestionDTO q : questionList) {
                        selectedOptionIds.add(null);
                        loadOptions(q.getQuestionId());
                    }
                } else {
                    Toast.makeText(QuizDoingActivity.this, "Không có câu hỏi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<QuestionDTO>> call, Throwable t) {
                Toast.makeText(QuizDoingActivity.this, "Lỗi tải câu hỏi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadOptions(UUID questionId) {
        apiService.getOptionsByQuestionId(questionId).enqueue(new Callback<List<OptionDTO>>() {
            @Override
            public void onResponse(Call<List<OptionDTO>> call, Response<List<OptionDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    optionMap.put(questionId, response.body());
                    if (optionMap.size() == questionList.size()) {
                        displayQuestion(currentIndex);
                    }
                } else {
                    Toast.makeText(QuizDoingActivity.this, "Không tải được đáp án", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OptionDTO>> call, Throwable t) {
                Toast.makeText(QuizDoingActivity.this, "Lỗi tải đáp án: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayQuestion(int index) {
        if (index < 0 || index >= questionList.size()) return;

        QuestionDTO question = questionList.get(index);
        tvQuestionNumber.setText("Câu " + (index + 1));
        tvQuestionContent.setText(question.getContent());

        List<OptionDTO> options = optionMap.get(question.getQuestionId());
        if (options == null) return;

        radioGroupOptions.removeAllViews();
        for (OptionDTO option : options) {
            RadioButton rb = new RadioButton(this);
            rb.setText(option.getContent());
            rb.setId(View.generateViewId());
            rb.setTag(option.getOptionId());
            radioGroupOptions.addView(rb);

            UUID selected = selectedOptionIds.get(index);
            if (selected != null && selected.equals(option.getOptionId())) {
                rb.setChecked(true);
            }
        }
    }

    private void saveSelectedOption() {
        int selectedViewId = radioGroupOptions.getCheckedRadioButtonId();
        if (selectedViewId != -1) {
            RadioButton selectedButton = findViewById(selectedViewId);
            UUID selectedOptionId = (UUID) selectedButton.getTag();
            if (currentIndex < selectedOptionIds.size()) {
                selectedOptionIds.set(currentIndex, selectedOptionId);
            }
        }
    }
}

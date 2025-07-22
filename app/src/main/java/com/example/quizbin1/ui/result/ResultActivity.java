package com.example.quizbin1.ui.result;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizbin1.R;
import com.example.quizbin1.data.model.dto.OptionDTO;
import com.example.quizbin1.data.model.dto.QuestionDTO;
import com.example.quizbin1.utils.GlobalDataHolder;

import java.util.List;
import java.util.UUID;

public class ResultActivity extends AppCompatActivity {

    private LinearLayout resultContainer;
    private TextView tvScore, tvCorrect, tvWrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvScore = findViewById(R.id.tvScore);
        tvCorrect = findViewById(R.id.tvCorrect);
        tvWrong = findViewById(R.id.tvWrong);
        resultContainer = findViewById(R.id.resultContainer);

        int score = getIntent().getIntExtra("score", 0);
        int correct = getIntent().getIntExtra("correct", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);

        tvScore.setText("Điểm: " + score);
        tvCorrect.setText("Số câu đúng: " + correct);
        tvWrong.setText("Số câu sai: " + wrong);

        List<QuestionDTO> questions = GlobalDataHolder.questionList;
        List<UUID> selectedOptionIds = GlobalDataHolder.selectedOptionIds;

        for (int i = 0; i < questions.size(); i++) {
            QuestionDTO question = questions.get(i);
            UUID selectedId = selectedOptionIds.get(i);
            List<OptionDTO> options = GlobalDataHolder.optionMap.get(question.getQuestionId());

            // Hiển thị nội dung câu hỏi
            TextView tvQuestion = new TextView(this);
            tvQuestion.setText("Câu " + (i + 1) + ": " + question.getContent());
            tvQuestion.setTextSize(16);
            tvQuestion.setTextColor(Color.BLACK);
            tvQuestion.setPadding(0, 16, 0, 8);
            resultContainer.addView(tvQuestion);

            for (OptionDTO option : options) {
                TextView tvOption = new TextView(this);
                tvOption.setText("• " + option.getContent());

                // Xử lý tô màu theo logic:
                if (option.getOptionId().equals(selectedId)) {
                    if (option.isCorrect()) {
                        tvOption.setTextColor(Color.GREEN); // chọn đúng
                    } else {
                        tvOption.setTextColor(Color.RED); // chọn sai
                    }
                } else if (option.isCorrect()) {
                    tvOption.setTextColor(Color.GREEN); // đáp án đúng (kể cả không chọn)
                } else {
                    tvOption.setTextColor(Color.BLACK); // các đáp án còn lại
                }

                tvOption.setPadding(30, 4, 0, 4);
                resultContainer.addView(tvOption);
            }
        }
    }
}

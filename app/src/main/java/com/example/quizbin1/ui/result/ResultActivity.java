package com.example.quizbin1.ui.result;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizbin1.R;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvScore = findViewById(R.id.tvScore);
        TextView tvCorrect = findViewById(R.id.tvCorrect);
        TextView tvWrong = findViewById(R.id.tvWrong);

        int score = getIntent().getIntExtra("score", 0);
        int correct = getIntent().getIntExtra("correct", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);

        tvScore.setText("Điểm: " + score);
        tvCorrect.setText("Số câu đúng: " + correct);
        tvWrong.setText("Số câu sai: " + wrong);
    }
}

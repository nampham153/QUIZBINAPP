package com.example.quizbin1.ui.settings;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizbin1.R;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        TextView tvTermsContent = findViewById(R.id.tvTermsContent);
        tvTermsContent.setText(getString(R.string.terms_text));
    }
}

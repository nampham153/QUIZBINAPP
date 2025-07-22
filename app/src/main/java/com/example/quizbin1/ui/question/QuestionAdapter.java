package com.example.quizbin1.ui.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizbin1.R;
import com.example.quizbin1.data.model.dto.QuestionDTO;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private Context context;
    private List<QuestionDTO> questionList = new ArrayList<>();

    public QuestionAdapter(Context context) {
        this.context = context;
    }

    public void setQuestionList(List<QuestionDTO> questions) {
        this.questionList = questions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionDTO question = questionList.get(position);
        holder.tvQuestionContent.setText(question.getContent());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionContent;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionContent = itemView.findViewById(R.id.tvQuestionContent);
        }
    }
}


package com.example.quizbin1.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbin1.R;
import com.example.quizbin1.data.model.dto.SubjectDTO;
import com.example.quizbin1.ui.semester.SemesterActivity;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<SubjectDTO> subjects = new ArrayList<>();
    private Context context;

    public void setSubjectList(List<SubjectDTO> subjectList) {
        this.subjects = subjectList;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nếu chưa gán context từ ngoài thì lấy luôn từ parent
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        SubjectDTO subject = subjects.get(position);
        holder.txtName.setText(subject.getSubjectName());

        // Xử lý khi click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SemesterActivity.class);
            intent.putExtra("subjectId", subject.getSubjectId().toString());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtSubjectName);
        }
    }
}

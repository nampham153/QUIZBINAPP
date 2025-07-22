package com.example.quizbin1.ui.semester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbin1.R;
import com.example.quizbin1.data.model.dto.SemesterDTO;

import java.util.ArrayList;
import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder> {

    private final Context context;
    private final String role;
    private final OnSemesterClickListener listener;
    private List<SemesterDTO> semesterList = new ArrayList<>();

    public interface OnSemesterClickListener {
        void onSemesterClick(String semesterId);
    }
    public SemesterAdapter(Context context, String role, OnSemesterClickListener listener) {
        this.context = context;
        this.role = role;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_semester, parent, false);
        return new SemesterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        SemesterDTO semester = semesterList.get(position);
        holder.tvSemesterName.setText(semester.getSemesterName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null && semester.getSemesterId() != null) {
                listener.onSemesterClick(semester.getSemesterId().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return semesterList.size();
    }

    public void setSemesterList(List<SemesterDTO> list) {
        this.semesterList = list;
        notifyDataSetChanged();
    }

    static class SemesterViewHolder extends RecyclerView.ViewHolder {
        TextView tvSemesterName;

        public SemesterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSemesterName = itemView.findViewById(R.id.tvSemesterName);
        }
    }
}

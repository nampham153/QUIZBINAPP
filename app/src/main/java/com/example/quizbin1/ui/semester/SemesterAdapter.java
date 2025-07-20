package com.example.quizbin1.ui.semester;

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

    private List<SemesterDTO> semesterList = new ArrayList<>();

    public void setSemesterList(List<SemesterDTO> semesterList) {
        this.semesterList = semesterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_semester, parent, false);
        return new SemesterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        SemesterDTO semester = semesterList.get(position);
        holder.tvSemesterName.setText(semester.getSemesterName());
    }

    @Override
    public int getItemCount() {
        return semesterList.size();
    }

    static class SemesterViewHolder extends RecyclerView.ViewHolder {
        TextView tvSemesterName;

        public SemesterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSemesterName = itemView.findViewById(R.id.tvSemesterName);
        }
    }
}

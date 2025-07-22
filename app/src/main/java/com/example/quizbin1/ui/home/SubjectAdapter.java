package com.example.quizbin1.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbin1.R;
import com.example.quizbin1.data.model.dto.SubjectDTO;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<SubjectDTO> subjects = new ArrayList<>();
    private OnItemClickListener listener;

    public void setSubjectList(List<SubjectDTO> subjectList) {
        this.subjects = subjectList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(SubjectDTO subject);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        SubjectDTO subject = subjects.get(position);
        holder.txtName.setText(subject.getSubjectName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(subject);
            }
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

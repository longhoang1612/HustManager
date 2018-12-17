package com.hoanglong.hustmanager.screen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.data.Subject;

import java.util.List;

import butterknife.BindView;

public class FailSubjectAdapter extends RecyclerView.Adapter<FailSubjectAdapter.SubjectViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Subject> mSubjects;
    private OnClickSubjectListener mOnClickSubjectListener;

    public FailSubjectAdapter(List<Subject> subjects, OnClickSubjectListener onClickSubjectListener) {
        mSubjects = subjects;
        mOnClickSubjectListener = onClickSubjectListener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_fail_subject, viewGroup, false);
        return new SubjectViewHolder(view, mOnClickSubjectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder subjectViewHolder, int i) {
        Subject subject = mSubjects.get(i);
        subjectViewHolder.bindData(subject);
    }

    public interface OnClickSubjectListener {
        void onClickSubject(Subject subject);
    }

    @Override
    public int getItemCount() {
        return mSubjects != null ? mSubjects.size() : 0;
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnClickSubjectListener mOnClickSubjectListener;
        private Subject mSubject;
        @BindView(R.id.text_name_subject)
        TextView mTextNameSubject;
        @BindView(R.id.text_ma_hoc_phan)
        TextView mTextMaHocPhan;
        @BindView(R.id.text_so_tin_chi)
        TextView mTextNumberTinchi;

        SubjectViewHolder(@NonNull View itemView, OnClickSubjectListener onClickSubjectListener) {
            super(itemView);
            mOnClickSubjectListener = onClickSubjectListener;
        }

        void bindData(Subject subject) {
            if (subject == null) return;
            mSubject = subject;
            mTextMaHocPhan.setText(subject.getSubjectCode());
            mTextNameSubject.setText(subject.getSubjectName());
            mTextNumberTinchi.setText(subject.getSubjectSoTinChi());
        }

        @Override
        public void onClick(View v) {
            mOnClickSubjectListener.onClickSubject(mSubject);
        }
    }
}

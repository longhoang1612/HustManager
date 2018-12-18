package com.hoanglong.hustmanager.screen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.database.Subject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.TargetViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Subject> mSubjects;
    private OnClickSubjectListener mOnClickSubjectListener;

    public interface OnClickSubjectListener {
        void onClickSubject(Subject subject);
    }

    public TargetAdapter(List<Subject> subjects, OnClickSubjectListener onClickSubjectListener) {
        mSubjects = subjects;
        mOnClickSubjectListener = onClickSubjectListener;
    }


    @NonNull
    @Override
    public TargetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_target, viewGroup, false);
        return new TargetViewHolder(view, mOnClickSubjectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetViewHolder targetViewHolder, int i) {
        Subject subject = mSubjects.get(i);
        targetViewHolder.bindData(subject);
    }

    @Override
    public int getItemCount() {
        return mSubjects != null ? mSubjects.size() : 0;
    }

    static class TargetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private OnClickSubjectListener mOnClickSubjectListener;
        private Subject mSubject;
        @BindView(R.id.text_ma_hoc_phan)
        TextView mTextMaHP;
        @BindView(R.id.text_name_subject)
        TextView mTextNameSubject;
        @BindView(R.id.text_point_subject)
        TextView mTextPointSubject;
        @BindView(R.id.text_number_credits)
        TextView mTextNumberCredits;

        TargetViewHolder(@NonNull View itemView, OnClickSubjectListener onClickSubjectListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mOnClickSubjectListener = onClickSubjectListener;
            itemView.setOnClickListener(this);
        }

        void bindData(Subject subject) {
            if(subject == null){
                return;
            }
            mSubject = subject;
            mTextMaHP.setText(subject.getSubjectCode());
            mTextNameSubject.setText(subject.getSubjectName());
            mTextPointSubject.setText(subject.getPointSubject());
            mTextNumberCredits.setText(String.valueOf(subject.getSubjectSoTinChi()));
        }

        @Override
        public void onClick(View v) {
            mOnClickSubjectListener.onClickSubject(mSubject);
        }
    }
}

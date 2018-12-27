package com.hoanglong.hustmanager.screen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.database.Class;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<Class> mClasses;
    private LayoutInflater mLayoutInflater;
    private OnClickClassListener mOnClickClassListener;


    public ClassAdapter(List<Class> classes, OnClickClassListener onClickClassListener) {
        mClasses = classes;
        mOnClickClassListener = onClickClassListener;
    }

    public interface OnClickClassListener {
        void onClickClass(Class mClass);
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_class, viewGroup, false);
        return new ClassViewHolder(view, mOnClickClassListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder classViewHolder, int i) {
        Class cl = mClasses.get(i);
        classViewHolder.bindData(cl);
    }

    @Override
    public int getItemCount() {
        return mClasses != null ? mClasses.size() : 0;
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_name_subject)
        TextView mTextSubject;
        @BindView(R.id.text_maHP)
        TextView mTextMaHP;
        @BindView(R.id.text_hoc_ky)
        TextView mTextHocKy;
        @BindView(R.id.text_so_tin_chi)
        TextView mTextSoTC;
        private OnClickClassListener mOnClickClassListener;
        private Class mClass;

        ClassViewHolder(@NonNull View itemView, OnClickClassListener onClickClassListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnClickClassListener = onClickClassListener;
            itemView.setOnClickListener(this);
        }

        void bindData(Class cl) {
            if (cl == null) {
                return;
            }
            mClass = cl;
            mTextSubject.setText(cl.getTenHP());
            mTextMaHP.setText(cl.getMaLH());
            mTextHocKy.setText(String.valueOf(cl.getHocky()));
            mTextSoTC.setText(String.valueOf(cl.getSoTC()));
        }

        @Override
        public void onClick(View view) {
            mOnClickClassListener.onClickClass(mClass);
        }
    }
}

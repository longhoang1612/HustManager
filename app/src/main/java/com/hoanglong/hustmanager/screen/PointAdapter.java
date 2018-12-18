package com.hoanglong.hustmanager.screen;

import android.graphics.Typeface;
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

public class PointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Subject> mSubjects;
    private OnClickPointListener mOnClickPointListener;

    public PointAdapter(List<Subject> subjects, OnClickPointListener onClickPointListener) {
        mSubjects = subjects;
        mOnClickPointListener = onClickPointListener;
    }

    public interface OnClickPointListener {
        void OnClickPoint(Subject subject);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        switch (viewType) {
            case 0:
                View view = mLayoutInflater.inflate(R.layout.item_point_header, parent, false);
                return new HeaderHolder(view);
            case 1:
                View viewHeader = mLayoutInflater.inflate(R.layout.item_point, parent, false);
                return new MyViewHolder(viewHeader, mOnClickPointListener);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.setData(mSubjects.get(position));
                break;
            case 1:
                ((MyViewHolder) holder).setData(mSubjects.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSubjects.get(position).isHeader()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mSubjects == null ? 0 : mSubjects.size();
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_ma_hoc_phan)
        TextView mTextMaHP;
        @BindView(R.id.text_hoc_ky)
        TextView mTextHocKy;
        @BindView(R.id.text_ten_hoc_phan)
        TextView mTextTenHP;
        @BindView(R.id.text_tin_chi)
        TextView mTextTinChi;
        @BindView(R.id.text_diem_qua_trinh)
        TextView mTextQuaTrinh;
        @BindView(R.id.text_diem_thi)
        TextView mTextDiemThi;
        @BindView(R.id.text_diem_chu)
        TextView mTextDiemChu;
        @BindView(R.id.text_he_so)
        TextView mTextHeSo;

        HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Subject data) {
            mTextMaHP.setText("Mã học phần");
            mTextHocKy.setText("Học kỳ");
            mTextTenHP.setText("Tên học phần");
            mTextTinChi.setText("TC");
            mTextQuaTrinh.setText("Điểm quá trình");
            mTextDiemThi.setText("Điểm thi");
            mTextDiemChu.setText("Điểm chữ");
            mTextHeSo.setText("Hệ số");

            mTextMaHP.setTypeface(Typeface.DEFAULT_BOLD);
            mTextHocKy.setTypeface(Typeface.DEFAULT_BOLD);
            mTextTenHP.setTypeface(Typeface.DEFAULT_BOLD);
            mTextTinChi.setTypeface(Typeface.DEFAULT_BOLD);
            mTextQuaTrinh.setTypeface(Typeface.DEFAULT_BOLD);
            mTextDiemThi.setTypeface(Typeface.DEFAULT_BOLD);
            mTextDiemChu.setTypeface(Typeface.DEFAULT_BOLD);
            mTextHeSo.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_ma_hoc_phan)
        TextView mTextMaHP;
        @BindView(R.id.text_hoc_ky)
        TextView mTextHocKy;
        @BindView(R.id.text_ten_hoc_phan)
        TextView mTextTenHP;
        @BindView(R.id.text_tin_chi)
        TextView mTextTinChi;
        @BindView(R.id.text_diem_qua_trinh)
        TextView mTextQuaTrinh;
        @BindView(R.id.text_diem_thi)
        TextView mTextDiemThi;
        @BindView(R.id.text_diem_chu)
        TextView mTextDiemChu;
        @BindView(R.id.text_he_so)
        TextView mTextHeSo;
        private Subject mSubject;
        private OnClickPointListener mOnClickPointListener;

        MyViewHolder(View itemView, OnClickPointListener onClickPointListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnClickPointListener = onClickPointListener;
            itemView.setOnClickListener(this);
        }

        public void setData(Subject subject) {
            if (subject == null) {
                return;
            }
            mSubject = subject;
        }

        @Override
        public void onClick(View v) {
            mOnClickPointListener.OnClickPoint(mSubject);
        }
    }


}
package com.hoanglong.hustmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoanglong.hustmanager.database.Student;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> mStudents;
    private LayoutInflater mLayoutInflater;
    private OnStudentClickListener mOnStudentClickListener;


    public StudentAdapter(List<Student> students, OnStudentClickListener onStudentClickListener) {
        mStudents = students;
        mOnStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_student, viewGroup, false);
        return new StudentViewHolder(view, mOnStudentClickListener);
    }

    interface OnStudentClickListener {
        void onStudentClick(Student student);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        Student student = mStudents.get(i);
        studentViewHolder.bindData(student);
    }

    @Override
    public int getItemCount() {
        return mStudents != null ? mStudents.size() : 0;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnStudentClickListener mOnStudentClickListener;
        private Student mStudent;
        @BindView(R.id.text_name)
        TextView mTextName;
        @BindView(R.id.text_mssv)
        TextView mTextMSSV;
        @BindView(R.id.text_class)
        TextView mTextClass;
        @BindView(R.id.text_diem_ck)
        TextView mTextDiemCK;
        @BindView(R.id.text_diem_qt)
        TextView mTextDiemQT;

        StudentViewHolder(@NonNull View itemView, OnStudentClickListener onStudentClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOnStudentClickListener = onStudentClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnStudentClickListener.onStudentClick(mStudent);
        }

        void bindData(Student student) {
            if (student == null) {
                return;
            }
            mStudent = student;
            mTextName.setText(String.format("Họ và tên: %s", student.getNameStudent()));
            mTextMSSV.setText(String.format("MSSV: %s", student.getMaSV()));
            mTextClass.setText(String.format("Lớp: %s", student.getMaLopHoc()));
            mTextDiemCK.setText(String.format("Điểm quá trình: %s", String.valueOf(student.getDiemQT())));
            mTextDiemQT.setText(String.format("Điểm chuyên cần: %s", String.valueOf(student.getDiemCK())));

        }
    }
}

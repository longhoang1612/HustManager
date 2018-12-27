package com.hoanglong.hustmanager.screen.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.database.Class;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.Student;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hoanglong.hustmanager.DetailClassActivity.BUNDLE_CLASS;

public class AddStudentFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = AddStudentFragment.class.getName();

    @BindView(R.id.edit_ma_sv)
    EditText mEditMaSV;
    @BindView(R.id.edit_diem_ck)
    EditText mEditDiemCK;
    @BindView(R.id.edit_diem_qt)
    EditText mEditDiemQT;
    @BindView(R.id.edit_ho_ten)
    EditText mEditHoTen;
    @BindView(R.id.edit_lop_sv)
    EditText mEditLopSV;
    @BindView(R.id.text_save_new_subject)
    TextView mTextSave;
    @BindView(R.id.text_cancel_new_subject)
    TextView mTextCancle;
    private DatabaseHelper db;
    private OnChangeListener mOnChangeListener;
    private Class mClass;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnChangeListener = (OnChangeListener) context;
    }

    public static AddStudentFragment newInstance(Class aClass) {

        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_CLASS, aClass);
        AddStudentFragment fragment = new AddStudentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mClass = getArguments().getParcelable(BUNDLE_CLASS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_add_student, container, false);
    }

    public interface OnChangeListener {
        void onChangeListener(long id);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        db = new DatabaseHelper(getContext());

        mTextCancle.setOnClickListener(this);
        mTextSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_save_new_subject:
                checkValid();
                break;
            case R.id.text_cancel_new_subject:
                getDialog().dismiss();
                break;
        }
    }

    private void checkValid() {
        if (mEditMaSV.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống mã học phần", Toast.LENGTH_SHORT).show();
        } else if (
                mEditHoTen.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống tên học phần", Toast.LENGTH_SHORT).show();
        } else if (mEditLopSV.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống số tín chỉ học phần", Toast.LENGTH_SHORT).show();
        } else {
            saveStudent();
            Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
        }
    }

    private void saveStudent() {

        String name = mEditHoTen.getText().toString();
        String mssv = mEditMaSV.getText().toString();
        String lopsv = mEditLopSV.getText().toString();
        String hocphan = mClass.getMaLH();
        Float diemQT = Float.parseFloat(mEditDiemQT.getText().toString());
        Float diemCK = Float.parseFloat(mEditDiemCK.getText().toString());

        Student student = new Student();
        student.setNameStudent(name);
        student.setMaSV(mssv);
        student.setMaLopHoc(lopsv);
        student.setMaLopMonHoc(hocphan);
        student.setDiemCK(diemCK);
        student.setDiemQT(diemQT);

        long id = db.insertStudent(student);
        mOnChangeListener.onChangeListener(id);
    }
}

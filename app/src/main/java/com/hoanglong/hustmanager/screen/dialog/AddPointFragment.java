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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.Subject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPointFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG = AddPointFragment.class.getName();

    @BindView(R.id.text_save_new_subject)
    TextView mTextSaveSubject;
    @BindView(R.id.text_cancel_new_subject)
    TextView mTextCancelNewSubject;
    @BindView(R.id.radio_point_subject)
    RadioGroup mRadioPointSubject;
    @BindView(R.id.edit_subject_code)
    EditText mEditSubjectCode;
    @BindView(R.id.edit_subject_name)
    EditText mEditSubjectName;
    @BindView(R.id.edit_subject_number)
    EditText mEditSubjectNumber;
    private DatabaseHelper db;
    //private OnChangeListener mOnChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_add_point, container, false);
    }

    public interface OnChangeListener{
        void onChangeListener(long id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
 //       mOnChangeListener = (OnChangeListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        db = new DatabaseHelper(getContext());

        mTextSaveSubject.setOnClickListener(this);
        mTextCancelNewSubject.setOnClickListener(this);
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
        if (mEditSubjectCode.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống mã học phần", Toast.LENGTH_SHORT).show();
        } else if (
                mEditSubjectName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống tên học phần", Toast.LENGTH_SHORT).show();
        } else if (mEditSubjectNumber.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống số tín chỉ học phần", Toast.LENGTH_SHORT).show();
        } else {
            saveNewSubject();
            Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
        }
    }

    private void saveNewSubject() {
        String code = mEditSubjectCode.getText().toString();
        String name = mEditSubjectName.getText().toString();
        String number = mEditSubjectNumber.getText().toString();
        String point = null;

        switch (mRadioPointSubject.getCheckedRadioButtonId()) {
            case R.id.radio_a_a_plus:
                point = "A";
                break;
            case R.id.radio_b_plus:
                point = "B+";
                break;
            case R.id.radio_b:
                point = "B+";
                break;
            case R.id.radio_c_plus:
                point = "C+";
                break;
            case R.id.radio_c:
                point = "C";
                break;
            case R.id.radio_d_plus:
                point = "D+";
                break;
            case R.id.radio_d:
                point = "D";
                break;
            case R.id.radio_f:
                point = "F";
                break;
        }

        Subject subject = new Subject(code, name, Integer.parseInt(number), point);
        createSubject(subject);
    }

    private void createSubject(Subject subject) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertSubject(subject);

        //mOnChangeListener.onChangeListener(id);

        // get the newly inserted note from db
    }
}

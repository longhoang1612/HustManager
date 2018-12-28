package com.hoanglong.hustmanager.screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.Subject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CaiThienFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = CaiThienFragment.class.getName();
    private static final String BUNDLE_SUBJECT = "BUNDLE_SUBJECT";
    private Subject mSubject;
    @BindView(R.id.edit_diem_qt)
    EditText mEditDiemQT;
    @BindView(R.id.edit_diem_ck)
    EditText mEditDiemCK;
    @BindView(R.id.radio_point_subject)
    RadioGroup mRadioCaiThien;
    @BindView(R.id.text_save_new_subject)
    TextView mTextSave;
    @BindView(R.id.text_cancel_new_subject)
    TextView mTextCancel;
    @BindView(R.id.text_ma_hoc_phan)
    TextView mTextMaHP;
    @BindView(R.id.image_back)
    ImageView mImageBack;
    private DatabaseHelper db;

    public static CaiThienFragment newInstance(Subject subject) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_SUBJECT, subject);
        CaiThienFragment fragment = new CaiThienFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) return;
        mSubject = getArguments().getParcelable(BUNDLE_SUBJECT);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cai_thien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        db = new DatabaseHelper(getContext());
        initData();
        initListener();
    }

    private void initData() {
        if(mSubject==null) return;
        mTextMaHP.setText(mSubject.getSubjectCode());
        mEditDiemCK.setText(String.valueOf(mSubject.getDiemThi()));
        mEditDiemQT.setText(String.valueOf(mSubject.getDiemQT()));
    }

    private void initListener() {
        mTextSave.setOnClickListener(this);
        mTextCancel.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.text_save_new_subject:
                checkValid();
                break;
            case R.id.text_cancel_new_subject:
                getDialog().dismiss();
                break;
        }
    }

    private void checkValid() {
        if(mEditDiemQT.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Bạn không được để trống điểm quá trình", Toast.LENGTH_SHORT).show();
        }else if(mEditDiemCK.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Bạn không được để trống điểm thi", Toast.LENGTH_SHORT).show();
        }else{
            updatePoint();
        }
    }

    private void updatePoint() {
        long id = db.updatePoint(mSubject);
    }
}

package com.hoanglong.hustmanager.screen.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.TeacherActivity;
import com.hoanglong.hustmanager.base.BaseFragment;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.Teacher;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignInFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.et_name)
    EditText mEditFullName;
    @BindView(R.id.et_password)
    EditText mEditPassword;
    @BindView(R.id.et_re_password)
    EditText mEditRePassword;
    @BindView(R.id.radio_group)
    RadioGroup mRadioPhanQuyen;
    @BindView(R.id.button_sign_in)
    Button mButtonSignIn;
    private ProgressDialog dialogProgress;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mDatabaseHelper = new DatabaseHelper(getContext());
        mButtonSignIn.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                showProgress();
                check();
                break;
        }
    }

    private void check() {
        if (mEditFullName.getText().toString().isEmpty()
                || mEditRePassword.getText().toString().isEmpty() || mEditPassword.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống thông tin", Toast.LENGTH_SHORT).show();
        } else if (!mEditPassword.getText().toString().equals(mEditRePassword.getText().toString())) {
            Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }
        else {
            createUser();
        }
    }

    private void createUser() {
        String fullname = mEditFullName.getText().toString();
        String password = mEditPassword.getText().toString();
        String phanquyen = null;
        switch (mRadioPhanQuyen.getCheckedRadioButtonId()) {
            case R.id.radio_gv:
                phanquyen = "Giáo viên";
                break;
            case R.id.radio_sv:
                phanquyen = "Sinh viên";
                break;
        }

        if(phanquyen.equals("Giáo viên")){
            Teacher teacher = new Teacher(fullname,password);
            mDatabaseHelper.insertGV(teacher);
            hideProgress();
            openTeachActivity();
        }else if(phanquyen.equals("Sinh viên")){

        }
    }

    private void openTeachActivity() {
        Intent intent = new Intent(getActivity(),TeacherActivity.class);
        startActivity(intent);
    }

    public void showProgress() {
        if (dialogProgress != null) {
            return;
        }
        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage(getString(R.string.wating_sign_in));
        dialogProgress.show();
    }

    public void hideProgress() {
        if (dialogProgress == null)
            return;
        dialogProgress.dismiss();
        dialogProgress = null;
    }

}

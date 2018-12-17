package com.hoanglong.hustmanager.screen.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignInFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.et_name)
    EditText mEditFullName;
    @BindView(R.id.edit_mssv)
    EditText mEditMSSV;
    @BindView(R.id.et_password)
    EditText mEditPassword;
    @BindView(R.id.radio_group)
    RadioGroup mRadioSex;
    @BindView(R.id.button_sign_in)
    Button mButtonSignIn;
    private ProgressDialog dialogProgress;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
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
        if (mEditMSSV.getText().toString().isEmpty()
                || mEditFullName.getText().toString().isEmpty() || mEditPassword.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống thông tin", Toast.LENGTH_SHORT).show();
        } else {
            createUser();
        }
    }

    private void createUser() {
        String mssv = mEditMSSV.getText().toString();
        String password = mEditPassword.getText().toString();
        String sex = null;
        switch (mRadioSex.getCheckedRadioButtonId()) {
            case R.id.radioButton_male:
                sex = "Nam";
                break;
            case R.id.radioButton_female:
                sex = "Nữ";
                break;
        }
        String fullName = mEditFullName.getText().toString();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = df.format(c);
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

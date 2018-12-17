package com.hoanglong.hustmanager.screen.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.base.BaseFragment;
import com.hoanglong.hustmanager.screen.MainActivity;
import com.hoanglong.hustmanager.utils.Constants;
import com.hoanglong.hustmanager.utils.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginFragment extends BaseFragment {

    @BindView(R.id.edit_mssv)
    EditText mEditMSSV;
    @BindView(R.id.et_password)
    EditText mEditPassword;
    @BindView(R.id.button_login)
    Button mButtonLogin;
    private ProgressDialog dialogProgress;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        if (!mEditMSSV.getText().toString().isEmpty()
                || !mEditPassword.getText().toString().isEmpty()) {
            showProgress();
            loginUser();
        } else {
            Toast.makeText(getContext(), getString(R.string.Cannot_empty_value), Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        String email = mEditMSSV.getText().toString();
        String password = mEditPassword.getText().toString();

        SharedPrefs.getInstance().put(Constants.SPR_LOGIN, true);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        hideProgress();
    }

    private void showProgressSuccess() {
        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage(getString(R.string.login_success));
        dialogProgress.show();
    }

    public void showProgress() {
        if (dialogProgress != null) {
            return;
        }
        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage(getString(R.string.wating_login));
        dialogProgress.show();
    }

    public void hideProgress() {
        if (dialogProgress == null)
            return;
        dialogProgress.dismiss();
        dialogProgress = null;
    }
}

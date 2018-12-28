package com.hoanglong.hustmanager.screen.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.screen.TeacherActivity;
import com.hoanglong.hustmanager.base.BaseFragment;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.User;
import com.hoanglong.hustmanager.screen.MainActivity;
import com.hoanglong.hustmanager.utils.Constants;
import com.hoanglong.hustmanager.utils.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginFragment extends BaseFragment {

    @BindView(R.id.edit_email)
    EditText mEditEmail;
    @BindView(R.id.et_password)
    EditText mEditPassword;
    @BindView(R.id.button_login)
    Button mButtonLogin;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.text_input_email)
    TextInputLayout mTextInputLayoutEmail;
    @BindView(R.id.text_input_password)
    TextInputLayout mTextInputLayoutPassword;
    private ProgressDialog dialogProgress;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mDatabaseHelper = new DatabaseHelper(getContext());
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    showProgress();
                    //Get values from EditText fields
                    String Email = mEditEmail.getText().toString();
                    String Password = mEditPassword.getText().toString();

                    //Authenticate user
                    User currentUser = mDatabaseHelper.Authenticate(new User(null, null, Email, Password));

                    //Check Authentication is successful or not
                    if (currentUser != null) {
                        Snackbar.make(v, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                        String phanquyen = null;
                        switch (mRadioGroup.getCheckedRadioButtonId()) {
                            case R.id.radio_gv:
                                phanquyen = "Giáo viên";
                                break;
                            case R.id.radio_sv:
                                phanquyen = "Sinh viên";
                                break;
                        }

                        if (phanquyen.equals("Giáo viên")) {
                            hideProgress();
                            openTeachActivity();
                            SharedPrefs.getInstance().put(Constants.SPR_LOGIN, true);
                            SharedPrefs.getInstance().put(Constants.PHANQUYEN, 1);
                            SharedPrefs.getInstance().put(Constants.EMAIL,Email);
                        } else if (phanquyen.equals("Sinh viên")) {
                            hideProgress();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            SharedPrefs.getInstance().put(Constants.SPR_LOGIN, true);
                            SharedPrefs.getInstance().put(Constants.PHANQUYEN, 0);
                            SharedPrefs.getInstance().put(Constants.EMAIL,Email);
                        }
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                    } else {
                        hideProgress();
                        //User Logged in Failed
                        Snackbar.make(v, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });
    }

    private void openTeachActivity() {
        Intent intent = new Intent(getActivity(), TeacherActivity.class);
        startActivity(intent);
    }

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = mEditEmail.getText().toString();
        String Password = mEditPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            mTextInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            mTextInputLayoutEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            mTextInputLayoutPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                mTextInputLayoutPassword.setError(null);
            } else {
                valid = false;
                mTextInputLayoutPassword.setError("Password is to short!");
            }
        }

        return valid;
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

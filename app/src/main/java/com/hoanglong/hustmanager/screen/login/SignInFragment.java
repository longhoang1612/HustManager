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
import android.widget.Toast;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.TeacherActivity;
import com.hoanglong.hustmanager.base.BaseFragment;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.Teacher;
import com.hoanglong.hustmanager.database.User;
import com.hoanglong.hustmanager.screen.MainActivity;
import com.hoanglong.hustmanager.utils.Constants;
import com.hoanglong.hustmanager.utils.SharedPrefs;

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
    @BindView(R.id.et_email)
    EditText mEditEmail;
    @BindView(R.id.button_sign_in)
    Button mButtonSignIn;
    private ProgressDialog dialogProgress;
    private DatabaseHelper mDatabaseHelper;
    @BindView(R.id.text_input_ten)
    TextInputLayout mTextIPTen;
    @BindView(R.id.text_input_email)
    TextInputLayout mTextIPEmail;
    @BindView(R.id.text_input_password)
    TextInputLayout mTextIPPass;
    @BindView(R.id.text_input_repassword)
    TextInputLayout mTextIPRePass;

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
                if (validate()) {
                    showProgress();
                    String UserName = mEditFullName.getText().toString();
                    String Email = mEditEmail.getText().toString();
                    String Password = mEditPassword.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!mDatabaseHelper.isEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        mDatabaseHelper.addUser(new User(null, UserName, Email, Password));
                        String phanquyen = null;
                        switch (mRadioPhanQuyen.getCheckedRadioButtonId()) {
                            case R.id.radio_gv:
                                phanquyen = "Giáo viên";
                                break;
                            case R.id.radio_sv:
                                phanquyen = "Sinh viên";
                                break;
                        }

                        if (phanquyen.equals("Giáo viên")) {
                            Teacher teacher = new Teacher(UserName, Password);
                            mDatabaseHelper.insertGV(teacher);
                            hideProgress();
                            openTeachActivity();
                            SharedPrefs.getInstance().put(Constants.SPR_LOGIN, true);
                            SharedPrefs.getInstance().put(Constants.PHANQUYEN, 1);
                        } else if (phanquyen.equals("Sinh viên")) {
                            hideProgress();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            SharedPrefs.getInstance().put(Constants.SPR_LOGIN, true);
                            SharedPrefs.getInstance().put(Constants.PHANQUYEN, 0);
                        }
                        Snackbar.make(v, "User created successfully! Please continue ", Snackbar.LENGTH_LONG).show();
                        if(getActivity()!=null){
                            getActivity().finish();
                        }
                    } else {
                        hideProgress();
                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(v, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }


    private void createUser() {
        String fullname = mEditFullName.getText().toString();
        String password = mEditPassword.getText().toString();
        String phanquyen = null;

    }

    private void openTeachActivity() {
        Intent intent = new Intent(getActivity(), TeacherActivity.class);
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

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = mEditFullName.getText().toString();
        String Email = mEditEmail.getText().toString();
        String Password = mEditPassword.getText().toString();
        String rePassword = mEditRePassword.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            mTextIPTen.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                mTextIPTen.setError(null);
            } else {
                valid = false;
                mTextIPTen.setError("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            mTextIPEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            mTextIPEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            mTextIPPass.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                mTextIPPass.setError(null);
            } else {
                valid = false;
                mTextIPPass.setError("Password is to short!");
            }
        }

        if (!Password.equals(rePassword)) {
            valid = false;
            Toast.makeText(getContext(), "Mật khẩu và xác nhận mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
        }


        return valid;
    }

}

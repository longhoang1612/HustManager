package com.hoanglong.hustmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hoanglong.hustmanager.database.Class;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.User;
import com.hoanglong.hustmanager.utils.Constants;
import com.hoanglong.hustmanager.utils.SharedPrefs;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddClassActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    @BindView(R.id.spinner_hoc_ky)
    Spinner mSpinner;
    @BindView(R.id.text_save_new_subject)
    TextView mTextInputML;
    @BindView(R.id.edit_subject_code)
    EditText mEditSubjectCode;
    @BindView(R.id.edit_subject_name)
    EditText mEditSubjectName;
    @BindView(R.id.edit_subject_number)
    EditText mEditSoTc;
    DatabaseHelper mDatabaseHelper;
    String email;
    User mUser;
    String spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        ButterKnife.bind(this);
        mDatabaseHelper = new DatabaseHelper(this);
        email = SharedPrefs.getInstance().get(Constants.EMAIL, String.class);
        mUser = mDatabaseHelper.getUser(email);
        mTextInputML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClass();
            }
        });
        mSpinner.setOnItemSelectedListener(this);
    }

    private void addClass() {

        int hocky = Integer.parseInt(spn);
        String subjectCode = mEditSubjectCode.getText().toString();
        String subjectName = mEditSubjectName.getText().toString();
        String tengv = mUser.userName;
        int tc = Integer.parseInt(mEditSoTc.getText().toString());

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        String time = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.DATE)
                + "" + currentTime.getHours() + "" + currentTime.getMinutes() + "" + currentTime.getSeconds();

        Class addClass = new Class(time, subjectCode, tc, subjectName, tengv, email, hocky);
        mDatabaseHelper.addClass(addClass);

        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spn = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

package com.hoanglong.hustmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hoanglong.hustmanager.database.Class;
import com.hoanglong.hustmanager.database.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddClassActivity extends AppCompatActivity {

    @BindView(R.id.spinner_hoc_ky)
    Spinner mSpinner;
    @BindView(R.id.text_save_new_subject)
    TextView mTextInputML;
    @BindView(R.id.edit_subject_code)
    EditText mEditSubjectCode;
    @BindView(R.id.edit_subject_name)
    EditText mEditSubjectName;
    @BindView(R.id.edit_ma_lop)
    EditText mEditMaLop;
    DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        ButterKnife.bind(this);
        mDatabaseHelper = new DatabaseHelper(this);
        mTextInputML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClass();
            }
        });
    }

    private void addClass() {

        int hocky = 20181;
        String subjectCode = mEditSubjectCode.getText().toString();
        String subjectName = mEditSubjectName.getText().toString();
        String malop = mEditMaLop.getText().toString();
        String tengv = "a";
        int tc = 2;

        Class addClass = new Class(subjectCode,tc,subjectName,tengv,"123",20181);
        mDatabaseHelper.addClass(addClass);

        finish();
    }
}

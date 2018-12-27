package com.hoanglong.hustmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hoanglong.hustmanager.database.Class;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.screen.ClassAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener, ClassAdapter.OnClickClassListener {

    @BindView(R.id.relative_add_class)
    RelativeLayout mRelativeAddClass;
    @BindView(R.id.recycler_class)
    RecyclerView mRecyclerClass;
    DatabaseHelper mDatabaseHelper;
    List<Class> mClasses;
    ClassAdapter mClassAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        ButterKnife.bind(this);
        mDatabaseHelper = new DatabaseHelper(this);
        mRelativeAddClass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_add_class:
                addClass();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mClasses = new ArrayList<>();
        mClasses.addAll(mDatabaseHelper.getClassWithGV("a"));
        if (mClasses != null && mClasses.size() > 0) {
            Toast.makeText(this, "Co du lieu", Toast.LENGTH_SHORT).show();
            mClassAdapter = new ClassAdapter(mClasses, this);
            mRecyclerClass.setAdapter(mClassAdapter);
        }

    }

    private void addClass() {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickClass(Class mClass) {
        Intent intent = new Intent(this, DetailClassActivity.class);
        intent.putExtra(DetailClassActivity.BUNDLE_CLASS, mClass);
        startActivity(intent);
    }
}

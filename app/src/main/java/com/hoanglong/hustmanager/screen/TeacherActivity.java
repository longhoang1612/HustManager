package com.hoanglong.hustmanager.screen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.database.Class;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.User;
import com.hoanglong.hustmanager.screen.login.LoginActivity;
import com.hoanglong.hustmanager.utils.Constants;
import com.hoanglong.hustmanager.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener, ClassAdapter.OnClickClassListener {

    @BindView(R.id.relative_add_class)
    RelativeLayout mRelativeAddClass;
    @BindView(R.id.recycler_class)
    RecyclerView mRecyclerClass;
    @BindView(R.id.ic_exit)
    ImageView mImageExit;
    private DatabaseHelper mDatabaseHelper;
    private List<Class> mClasses;
    private ClassAdapter mClassAdapter;
    private String email;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        ButterKnife.bind(this);
        mDatabaseHelper = new DatabaseHelper(this);
        mRelativeAddClass.setOnClickListener(this);
        mImageExit.setOnClickListener(this);
        email = SharedPrefs.getInstance().get(Constants.EMAIL, String.class);
        mUser = mDatabaseHelper.getUser(email);
        Toast.makeText(this, mUser.userName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_add_class:
                addClass();
                break;
            case R.id.ic_exit:
                logout();
        }
    }

    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Bạn có muốn đăng xuất khỏi ứng dụng");

        // set dialog message
        alertDialogBuilder
                .setMessage("Chọn có để đăng xuấtt!")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(TeacherActivity.this, LoginActivity.class);
                        startActivity(intent);
                        SharedPrefs.getInstance().clear();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mClasses = new ArrayList<>();
        mClasses.addAll(mDatabaseHelper.getClassWithGV(mUser.userName));
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

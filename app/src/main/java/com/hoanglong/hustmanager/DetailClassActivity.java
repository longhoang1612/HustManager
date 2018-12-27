package com.hoanglong.hustmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hoanglong.hustmanager.database.Class;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.Student;
import com.hoanglong.hustmanager.screen.dialog.AddStudentFragment;
import com.hoanglong.hustmanager.screen.dialog.EditStudentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailClassActivity extends AppCompatActivity implements AddStudentFragment.OnChangeListener,
        View.OnClickListener, StudentAdapter.OnStudentClickListener,EditStudentFragment.OnChangeItemListener {

    public static final String BUNDLE_CLASS = "BUNDLE_CLASS";
    @BindView(R.id.ten_hp)
    TextView mTextHP;
    @BindView(R.id.ma_hp)
    TextView mMaHP;
    @BindView(R.id.hoc_ky)
    TextView mTextHocKy;
    @BindView(R.id.giao_vien)
    TextView mTextTenGV;
    @BindView(R.id.fab_add_student)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.recycler_student)
    RecyclerView mRecyclerStudent;
    private DatabaseHelper mDatabaseHelper;
    private List<Student> mStudentList;
    private StudentAdapter mStudentAdapter;
    private Class aClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_class);
        ButterKnife.bind(this);
        mDatabaseHelper = new DatabaseHelper(this);
        aClass = getIntent().getParcelableExtra(BUNDLE_CLASS);
        if (aClass == null) {
            return;
        }
        mStudentList = new ArrayList<>();
        Log.d("ABCD", "onCreate: "+aClass.getId());
        mStudentList.addAll(mDatabaseHelper.getStudentWithIdClass(aClass.getMaLH()));
        if(mStudentList!=null && mStudentList.size() > 0){
            Toast.makeText(this, "Có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        updateUI(aClass);
        mFloatingActionButton.setOnClickListener(this);
    }

    private void updateUI(Class aClass) {
        mTextHP.setText(String.format("Học phần : %s ", aClass.getTenHP()));
        mMaHP.setText(String.format("Mã học phần : %s ", aClass.getMaLH()));
        mTextHocKy.setText(String.format("Học phần : %d ", aClass.getHocky()));
        mTextTenGV.setText(String.format("Giáo viên : %s ", aClass.getTenGV()));

        mStudentAdapter = new StudentAdapter(mStudentList, this);
        mRecyclerStudent.setAdapter(mStudentAdapter);
    }

    @Override
    public void onChangeListener(long id) {
        Student n = mDatabaseHelper.getStudent(id);
        if (n != null) {
            // adding new note to array list at 0 position
            mStudentList.add(mStudentList.size(), n);

            // refreshing the list
            mStudentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_student:
                if (getFragmentManager() != null) {
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    android.support.v4.app.Fragment prev = getSupportFragmentManager().findFragmentByTag(AddStudentFragment.TAG);
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    DialogFragment dialogFragment = AddStudentFragment.newInstance(aClass);
                    dialogFragment.show(ft, AddStudentFragment.TAG);
                }
                break;
        }
    }

    @Override
    public void onStudentClick(Student student) {
        chooseImage(student);
    }

    private void chooseImage(final Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn chỉnh sửa");
        builder.setItems(new CharSequence[]{"Cập nhật", "Xóa"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(DetailClassActivity.this, "Cập nhật", Toast.LENGTH_SHORT).show();
                                if (getFragmentManager() != null) {
                                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    android.support.v4.app.Fragment prev = getSupportFragmentManager().findFragmentByTag(EditStudentFragment.TAG);
                                    if (prev != null) {
                                        ft.remove(prev);
                                    }
                                    ft.addToBackStack(null);
                                    DialogFragment dialogFragment = EditStudentFragment.newInstance(aClass,student);
                                    dialogFragment.show(ft, EditStudentFragment.TAG);
                                }
                                break;
                            case 1:
                                Toast.makeText(DetailClassActivity.this, "Xóa", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });

        builder.show();
    }

    @Override
    public void onChangeItemListener(long id) {
        mStudentAdapter.notifyDataSetChanged();
    }
}

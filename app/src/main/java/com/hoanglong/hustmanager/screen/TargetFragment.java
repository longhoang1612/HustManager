package com.hoanglong.hustmanager.screen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.TabletPointFragment;
import com.hoanglong.hustmanager.base.BaseFragment;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.Subject;
import com.hoanglong.hustmanager.screen.dialog.NewSubjectFragment;
import com.hoanglong.hustmanager.utils.FragmentTransactionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TargetFragment extends BaseFragment implements View.OnClickListener,TargetAdapter.OnClickSubjectListener {

    @BindView(R.id.image_add)
    ImageView mImageAdd;
    @BindView(R.id.image_back)
    ImageView mImageBack;
    @BindView(R.id.recycler_new_subject)
    RecyclerView mRecyclerNewSubject;
    private DatabaseHelper db;
    private List<Subject> mSubjects;
    private TargetAdapter mTargetAdapter;

    public static final String TAG = TargetFragment.class.getName();

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_target;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mImageAdd.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
        db = new DatabaseHelper(getContext());
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mSubjects = new ArrayList<>();
        mSubjects.addAll(db.getAllSubjects());
        mTargetAdapter = new TargetAdapter(mSubjects,this);
        mRecyclerNewSubject.setAdapter(mTargetAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_add:
                chooseType();
                break;
            case R.id.image_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
        }
    }

    private void chooseType() {
        if (getContext() == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.title_choose_target);
        builder.setItems(new CharSequence[]{getString(R.string.title_renew_subject), getString(R.string.title_new_subject)},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(getContext(), getString(R.string.title_renew_subject), Toast.LENGTH_SHORT).show();
                                FragmentTransaction ft;
                                if (getFragmentManager() != null) {
                                    ft = getFragmentManager().beginTransaction();
                                    Fragment prev = getFragmentManager().findFragmentByTag(TabletPointFragment.TAG);
                                    if (prev != null) {
                                        ft.remove(prev);
                                    }
                                    ft.addToBackStack(null);
                                    FragmentTransactionUtils.addFragment(getFragmentManager(),
                                            new TabletPointFragment(),
                                            R.id.frame_full,
                                            TabletPointFragment.TAG,
                                            true,-1,-1);
                                }
                                break;
                            case 1:
                                Toast.makeText(getContext(), getString(R.string.title_new_subject), Toast.LENGTH_SHORT).show();
                                if (getFragmentManager() != null) {
                                    ft = getFragmentManager().beginTransaction();
                                    Fragment prev = getFragmentManager().findFragmentByTag(NewSubjectFragment.TAG);
                                    if (prev != null) {
                                        ft.remove(prev);
                                    }
                                    ft.addToBackStack(null);
                                    DialogFragment dialogFragment = new NewSubjectFragment();
                                    dialogFragment.show(ft, NewSubjectFragment.TAG);
                                }
                                break;
                        }
                    }
                });
        builder.show();
    }

    public void onChangeListener(long id) {
        Subject n = db.getSubject(id);

        if (n != null) {
            // adding new note to array list at 0 position
            mSubjects.add(0, n);

            // refreshing the list
            mTargetAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickSubject(Subject subject) {

    }
}

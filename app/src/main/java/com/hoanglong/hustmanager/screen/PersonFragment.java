package com.hoanglong.hustmanager.screen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.base.BaseFragment;
import com.hoanglong.hustmanager.database.Subject;
import com.hoanglong.hustmanager.utils.Constants;
import com.hoanglong.hustmanager.utils.FragmentTransactionUtils;
import com.hoanglong.hustmanager.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonFragment extends BaseFragment implements View.OnClickListener, FailSubjectAdapter.OnClickSubjectListener {

    public static final String TAG = PersonFragment.class.getName();
    @BindView(R.id.relative_point)
    RelativeLayout mRelativePoint;
    @BindView(R.id.relative_target)
    RelativeLayout mRelativeTarget;
    @BindView(R.id.relative_lost_subject)
    RelativeLayout mRelativeFailSubject;
    @BindView(R.id.relative_sign_out)
    RelativeLayout mRelativeSignOut;
    @BindView(R.id.recycler_fail_subject)
    RecyclerView mRecyclerFailSubject;
    private FailSubjectAdapter mFailSubjectAdapter;
    private List<Subject> mSubjects;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mRelativePoint.setOnClickListener(this);
        mRelativeTarget.setOnClickListener(this);
        mRelativeFailSubject.setOnClickListener(this);
        mRelativeSignOut.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mSubjects = new ArrayList<>();
        mFailSubjectAdapter = new FailSubjectAdapter(mSubjects, this);
        mRecyclerFailSubject.setAdapter(mFailSubjectAdapter);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.relative_point:
                if (getFragmentManager() != null) {
                    fragment = getFragmentManager().findFragmentByTag(TabletPointFragment.TAG);
                    if (fragment == null) {
                        fragment = new TabletPointFragment();
                    }
                    openFragment(fragment, TabletPointFragment.TAG);
                }
                break;
            case R.id.relative_target:
                if (getFragmentManager() != null) {
                    fragment = getFragmentManager().findFragmentByTag(TargetFragment.TAG);
                    if (fragment == null) {
                        fragment = new TargetFragment();
                    }
                    openFragment(fragment, TargetFragment.TAG);
                }
                break;
            case R.id.relative_lost_subject:
                if (getFragmentManager() != null) {
                    fragment = getFragmentManager().findFragmentByTag(LostSubjectFragment.TAG);
                    if (fragment == null) {
                        fragment = new LostSubjectFragment();
                    }
                    openFragment(fragment, LostSubjectFragment.TAG);
                }
                break;
            case R.id.relative_sign_out:
                SharedPrefs.getInstance().put(Constants.SPR_LOGIN, false);
                if (getActivity() == null) return;
                getActivity().finish();
                break;
        }
    }

    public void openFragment(Fragment fragment, String tag) {
        if (getFragmentManager() == null) return;
        FragmentTransactionUtils.addFragment(
                getFragmentManager(),
                fragment, R.id.frame_full,
                tag, true, -1, -1);
    }

    @Override
    public void onClickSubject(Subject subject) {

    }
}

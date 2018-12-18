package com.hoanglong.hustmanager;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hoanglong.hustmanager.base.BaseFragment;
import com.hoanglong.hustmanager.database.Subject;
import com.hoanglong.hustmanager.screen.PointAdapter;
import com.hoanglong.hustmanager.screen.dialog.AddPointFragment;
import com.hoanglong.hustmanager.screen.dialog.NewSubjectFragment;
import com.hoanglong.hustmanager.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabletPointFragment extends BaseFragment implements View.OnClickListener
        ,SwipeRefreshLayout.OnRefreshListener,PointAdapter.OnClickPointListener {

    public static final String TAG = TabletPointFragment.class.getName();
    @BindView(R.id.recycler_tablet_point)
    RecyclerView mRecyclerTabletPoint;
    @BindView(R.id.image_back)
    ImageView mImageBack;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.ic_add)
    FloatingActionButton mFloatingActionAdd;
    private List<Subject> mSubjects;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_tablet_point;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this,view);
        mImageBack.setOnClickListener(this);
        mSwipeRefresh.setOnRefreshListener(this);
        mFloatingActionAdd.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mSubjects = new ArrayList<>();
        setData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.ic_add:
                if (getFragmentManager() != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag(NewSubjectFragment.TAG);
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    DialogFragment dialogFragment = new AddPointFragment();
                    dialogFragment.show(ft, AddPointFragment.TAG);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(false);
                setData();
            }
        }, Constants.DELAY_1000);
    }

    private void setData() {
        Subject header = new Subject();
        header.setHeader(true);
        mSubjects.add(header);
        PointAdapter pointAdapter = new PointAdapter(mSubjects,this);
        mRecyclerTabletPoint.setAdapter(pointAdapter);
    }

    @Override
    public void OnClickPoint(Subject subject) {

    }
}

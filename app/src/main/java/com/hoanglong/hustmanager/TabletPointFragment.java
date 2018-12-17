package com.hoanglong.hustmanager;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hoanglong.hustmanager.base.BaseFragment;
import com.hoanglong.hustmanager.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabletPointFragment extends BaseFragment implements View.OnClickListener
        ,SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = TabletPointFragment.class.getName();
    @BindView(R.id.recycler_tablet_point)
    RecyclerView mRecyclerTabletPoint;
    @BindView(R.id.image_back)
    ImageView mImageBack;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_tablet_point;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this,view);
        mImageBack.setOnClickListener(this);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
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

    }
}

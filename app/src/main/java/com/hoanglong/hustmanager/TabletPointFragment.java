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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hoanglong.hustmanager.base.BaseFragment;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.Subject;
import com.hoanglong.hustmanager.screen.PointAdapter;
import com.hoanglong.hustmanager.screen.dialog.AddPointFragment;
import com.hoanglong.hustmanager.utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabletPointFragment extends BaseFragment implements View.OnClickListener
        , SwipeRefreshLayout.OnRefreshListener, PointAdapter.OnClickPointListener {

    public static final String TAG = TabletPointFragment.class.getName();
    @BindView(R.id.recycler_tablet_point)
    RecyclerView mRecyclerTabletPoint;
    @BindView(R.id.image_back)
    ImageView mImageBack;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.ic_add)
    FloatingActionButton mFloatingActionAdd;
    @BindView(R.id.spinner_point)
    Spinner mSpinner;
    @BindView(R.id.total_tc)
    TextView mTotalTC;
    @BindView(R.id.text_cpa)
    TextView mTextCPA;
    private List<Subject> mSubjects;
    private DatabaseHelper mDatabaseHelper;
    private PointAdapter pointAdapter;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_tablet_point;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mImageBack.setOnClickListener(this);
        mSwipeRefresh.setOnRefreshListener(this);
        mFloatingActionAdd.setOnClickListener(this);
        mDatabaseHelper = new DatabaseHelper(getContext());
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        setData();
        OnUpdateTC();
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
                    Fragment prev = getFragmentManager().findFragmentByTag(AddPointFragment.TAG);
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
        mSubjects = new ArrayList<>();
        Subject header = new Subject();
        header.setHeader(true);
        mSubjects.add(header);

        mSubjects.addAll(mDatabaseHelper.getAllPoint());

        pointAdapter = new PointAdapter(mSubjects, this);
        mRecyclerTabletPoint.setAdapter(pointAdapter);
    }

    @Override
    public void OnClickPoint(Subject subject) {
        if (getFragmentManager() != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag(CaiThienFragment.TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment dialogFragment = CaiThienFragment.newInstance(subject);
            dialogFragment.show(ft, CaiThienFragment.TAG);
        }
    }

    @Override
    public void OnUpdateTC() {
        if (mSubjects.size() == 0) return;
        int tc = 0;
        double cpa = 0;
        for (int i = 1; i < mSubjects.size(); i++) {

            Subject subject = mSubjects.get(i);

            tc += subject.getSubjectSoTinChi();
            double point = 0;
            switch (subject.getPointSubject()) {
                case "A+":
                    point = 4;
                    break;
                case "A":
                    point = 4;
                    break;
                case "B+":
                    point = 3.5;
                    break;
                case "B":
                    point = 3;
                    break;
                case "C+":
                    point = 2.5;
                    break;
                case "C":
                    point = 2;
                    break;
                case "D+":
                    point = 1.5;
                    break;
                case "D":
                    point = 1;
                    break;
                case "F":
                    point = 0;
                    break;
            }
            cpa += (point * subject.getSubjectSoTinChi());
        }

        mTotalTC.setText(String.format("%s tín chỉ", String.valueOf(tc)));
        DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
        double dd2dec = new Double(df2.format(cpa/tc)).doubleValue();
        mTextCPA.setText(String.valueOf(dd2dec));
    }

    public void onChangePointListener(long id) {
        Subject n = mDatabaseHelper.getPoint(id);

        if (n != null) {
            // adding new note to array list at 0 position
            mSubjects.add(mSubjects.size(), n);

            // refreshing the list
            pointAdapter.notifyDataSetChanged();
        }
    }
}

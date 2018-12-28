package com.hoanglong.hustmanager.screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.base.BaseActivity;
import com.hoanglong.hustmanager.screen.dialog.AddPointFragment;
import com.hoanglong.hustmanager.screen.dialog.NewSubjectFragment;
import com.hoanglong.hustmanager.utils.FragmentTransactionUtils;

public class MainActivity extends BaseActivity implements NewSubjectFragment.OnChangeListener, AddPointFragment.OnChangePointListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    Fragment fragment;
                    fragment = getSupportFragmentManager().findFragmentByTag(PersonFragment.TAG);
                    if (fragment == null) {
                        fragment = new PersonFragment();
                    }
                    openFragment(fragment, PersonFragment.TAG);
                    return true;
                case R.id.navigation_notifications:
                    fragment = getSupportFragmentManager().findFragmentByTag(TargetFragment.TAG);
                    if (fragment == null) {
                        fragment = new TargetFragment();
                    }
                    openFragment(fragment, TargetFragment.TAG);
                    return true;
            }
            return false;
        }
    };

    private void openFragment(Fragment fragment, String tag) {
        FragmentTransactionUtils.addFragment(
                getSupportFragmentManager(),
                fragment, R.id.frame_main,
                tag, true, -1, -1);
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onChangeListener(long id) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TargetFragment.TAG);
        if (fragment instanceof TargetFragment) {
            ((TargetFragment) fragment).onChangeListener(id);
        }
    }

    @Override
    public void onChangePointListener(long id) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TabletPointFragment.TAG);
        if (fragment instanceof TabletPointFragment) {
            ((TabletPointFragment) fragment).onChangePointListener(id);
        }
    }
}

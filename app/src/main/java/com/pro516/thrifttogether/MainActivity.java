package com.pro516.thrifttogether;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.TextView;

import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.discover.DiscoverFragment;
import com.pro516.thrifttogether.ui.home.HomeFragment;
import com.pro516.thrifttogether.ui.mall.MallFragment;
import com.pro516.thrifttogether.ui.mine.MineFragment;

public class MainActivity extends BaseActivity {

    private Fragment mLastFragment;
    private TextView mTitle;
    private static final Fragment[] FRAGMENTS = new Fragment[4];

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initFragments();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initFragments() {
        FRAGMENTS[0] = new HomeFragment();
        FRAGMENTS[1] = new DiscoverFragment();
        FRAGMENTS[2] = new MallFragment();
        FRAGMENTS[3] = new MineFragment();
        setFragment(FRAGMENTS[0]);
        mTitle = findViewById(R.id.title);
        mTitle.setText(getString(R.string.home));
    }

    private void setFragment(Fragment fragment) {
        String tag = fragment.getClass().getSimpleName();
        Fragment result = getSupportFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mLastFragment != null) {
            transaction.hide(mLastFragment);
        }
        if (result == null) {
            transaction.add(R.id.main, fragment, tag).commit();
        } else {
            transaction.show(result).commit();
        }
        mLastFragment = fragment;
    }

    /**
     * 底部导航的监听事件
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.tabbar_home:
                            mTitle.setText(getString(R.string.home));
                            setFragment(FRAGMENTS[0]);
                            break;
                        case R.id.tabbar_discover:
                            mTitle.setText(getString(R.string.discover));
                            setFragment(FRAGMENTS[1]);
                            break;
                        case R.id.tabbar_mall:
                            mTitle.setText(getString(R.string.mall));
                            setFragment(FRAGMENTS[2]);
                            break;
                        case R.id.tabbar_mine:
                            mTitle.setText(getString(R.string.mine));
                            setFragment(FRAGMENTS[3]);
                            break;
                    }
                    return true;
                }
            };
}

package com.pro516.thrifttogether.ui.discover;

import android.os.Bundle;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;

/**
 * Created by hncboy on 2019-03-19.
 */
public class DiscoverFragment extends BaseFragment {

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_discover;
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}

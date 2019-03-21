package com.pro516.thrifttogether.ui.mall;

import android.view.View;
import android.widget.TextView;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;

/**
 * Created by hncboy on 2019-03-19.
 */
public class MallFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void init(View view) {
        TextView mTitle = view.findViewById(R.id.title);
        mTitle.setText(getString(R.string.points_mall));
    }
}

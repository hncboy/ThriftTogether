package com.pro516.thrifttogether.ui.mall;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

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
        AppCompatTextView title = view.findViewById(R.id.title);
        title.setText(getString(R.string.points_mall));
    }
}

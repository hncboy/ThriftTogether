package com.pro516.thrifttogether.ui.mall;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;

/**
 * Created by hncboy on 2019-03-19.
 */
public class MallFragment extends BaseFragment {

    private RecyclerView mRvGrid;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void init(View view) {
        TextView mTitle = view.findViewById(R.id.title);
        mRvGrid = view.findViewById(R.id.rv_grid);
        mTitle.setText(getString(R.string.points_mall));

        mRvGrid.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRvGrid.setAdapter(new GridAdapter(getActivity(), new GridAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getActivity(), "click: " + pos, Toast.LENGTH_SHORT).show();
            }
        }));
    }
}

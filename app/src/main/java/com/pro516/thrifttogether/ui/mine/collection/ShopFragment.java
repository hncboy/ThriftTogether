package com.pro516.thrifttogether.ui.mine.collection;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.mine.adapter.ShopAdapter;
import com.pro516.thrifttogether.entity.mine.ShopBean;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends BaseFragment implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    @Override
    protected void init(View view) {
        setListeners();
        initData();
        initRecyclerView(view);
    }

    private void setListeners() {
    }

    List<ShopBean> mData;
    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.mine_reservation);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ShopAdapter mAdapter = new ShopAdapter(R.layout.item_shop, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "点击：" + position, Toast.LENGTH_SHORT).show();
                //startActivity(OrderDetailsActivity.class);
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add(new ShopBean(
                    "海底捞",
                    "宁波亚细亚店",
                    5.0,
                    50,
                    "https://img.meituan.net/msmerchant/054b5de0ba0b50c18a620cc37482129a45739.jpg@380w_214h_1e_1c"
            ));
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine_collection_shops;
    }
}

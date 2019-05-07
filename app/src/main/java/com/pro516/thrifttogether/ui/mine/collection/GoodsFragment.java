package com.pro516.thrifttogether.ui.mine.collection;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.mine.adapter.GoodsAdapter;
import com.pro516.thrifttogether.ui.mine.adapter.ShopAdapter;
import com.pro516.thrifttogether.ui.mine.bean.GoodsBean;
import com.pro516.thrifttogether.ui.mine.bean.ShopBean;
import com.pro516.thrifttogether.ui.mine.reservation.MineReservationActivity;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GoodsFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public void onClick(View view) {

    }

    private void setListeners() {

    }

    @Override
    protected void init(View view) {
        setListeners();
        initData();
        initRecyclerView(view);
    }

    List<GoodsBean> mData;
    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.mine_reservation);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        GoodsAdapter mAdapter = new GoodsAdapter(R.layout.item_goods, mData);
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
            mData.add(new GoodsBean(
                    "https://img.meituan.net/msmerchant/054b5de0ba0b50c18a620cc37482129a45739.jpg@380w_214h_1e_1c",
                    "海底捞",
                    "宁波亚细亚店",
                    "火锅",
                    50.0
            ));
        }
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine_collection_goods;
    }
}

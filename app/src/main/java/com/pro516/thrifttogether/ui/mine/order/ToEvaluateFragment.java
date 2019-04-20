package com.pro516.thrifttogether.ui.mine.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.mine.adapter.OrderAdapter;
import com.pro516.thrifttogether.ui.mine.bean.OrderBean;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToEvaluateFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener{
    List<OrderBean> mData;

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void init(View view) {
        initData();
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.mine_order_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        OrderAdapter mAdapter = new OrderAdapter(R.layout.item_mine_order, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "点击：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add(new OrderBean(
                    "https://img.meituan.net/msmerchant/53016dc6b5bb3d03729e5cb3eea09550401792.jpg@380w_214h_1e_1c",
                    "干锅土豆鸡",
                    "待评价",
                    "https://img.meituan.net/msmerchant/53016dc6b5bb3d03729e5cb3eea09550401792.jpg@380w_214h_1e_1c",
                    new Date(),
                    12.0,
                    "去评价"));
        }
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine_order_to_evaluate;
    }
}


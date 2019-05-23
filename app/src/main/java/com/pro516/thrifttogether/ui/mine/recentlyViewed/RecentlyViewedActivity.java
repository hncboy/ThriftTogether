package com.pro516.thrifttogether.ui.mine.recentlyViewed;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.mine.adapter.ShopAdapter;
import com.pro516.thrifttogether.entity.mine.ShopBean;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RecentlyViewedActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_recently_viewed;
    }

    private void setListeners() {

    }

    @Override
    protected void init() {
        setListeners();
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.recently_viewed));
        initData();
        initRecyclerView();
    }

    List<ShopBean> mData;
    private void initRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.mine_reservation);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(RecentlyViewedActivity.this, DividerItemDecoration.VERTICAL_LIST));
        ShopAdapter mAdapter = new ShopAdapter(R.layout.item_shop, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecentlyViewedActivity.this, "点击：" + position, Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            default:
                break;
        }
    }
}

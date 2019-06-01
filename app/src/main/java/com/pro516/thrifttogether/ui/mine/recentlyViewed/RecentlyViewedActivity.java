package com.pro516.thrifttogether.ui.mine.recentlyViewed;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.ShopBean;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.mine.adapter.ShopAdapter;
import com.pro516.thrifttogether.ui.mine.order.UseActivity;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.io.IOException;
import java.util.List;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.USER_RECENTLY_BROWSE;

public class RecentlyViewedActivity extends BaseActivity implements View.OnClickListener {


    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void init() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(R.string.recentlyView);
        mRecyclerView = findViewById(R.id.mine_recently_viewed);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
        initRefreshLayout();
    }

    private void initRecyclerView(List<ShopBean> mData) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        ShopAdapter mAdapter = new ShopAdapter(R.layout.item_shop, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Log.d("团节", "onItemClick: ");
            Toast.makeText(this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, UseActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("data",mData.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(RecentlyViewedActivity.this, getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    break;
                case LOAD_ALL:
                    initRecyclerView((List<ShopBean>) msg.obj);
                    if (mSwipeRefresh.isRefreshing()) {
                        mSwipeRefresh.setRefreshing(false);
                    }
                    mProgressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(USER_RECENTLY_BROWSE);
                    List<ShopBean> mData = JsonParser.shopList(json);
                    System.out.println("---------------------------->" + mData);
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initRefreshLayout() {
        mSwipeRefresh = findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(() -> {
            loadData();
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_recently_viewed;
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

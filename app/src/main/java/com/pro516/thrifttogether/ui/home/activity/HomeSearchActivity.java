package com.pro516.thrifttogether.ui.home.activity;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.home.adapter.HomeHistorySearchAdapter;
import com.pro516.thrifttogether.ui.home.adapter.HomeHotSearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchActivity extends BaseActivity implements View.OnClickListener {

    private List<String> mHotSearchData;
    private List<String> mHistorySearchData;
    private HomeHotSearchAdapter mHomeHotSearchAdapter;
    private HomeHistorySearchAdapter mHomeHistorySearchAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void init() {
        initToolbar();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        AppCompatTextView searchTv = findViewById(R.id.common_toolbar_function_right);
        backImgBtn.setOnClickListener(this);
        searchTv.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mHistorySearchData = new ArrayList<>();
        mHotSearchData = new ArrayList<>();
        initHistorySearchAdapter();
        initHotSearchAdapter();
    }

    private void initData() {
        getHotSearchData();
        getHistorySearchdata();
    }

    /**
     * 初始化热门搜索适配器
     */
    private void initHotSearchAdapter() {
        RecyclerView mHotSearchRecyclerView = findViewById(R.id.hot_search_ry);
        mHomeHotSearchAdapter = new HomeHotSearchAdapter(mHotSearchData);
        mHotSearchRecyclerView.setAdapter(mHomeHotSearchAdapter);
        mHotSearchRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mHomeHotSearchAdapter.setOnItemClickListener((adapter, view, position) -> {
            String content = (String) adapter.getData().get(position);
            toast(content);
        });
    }

    /**
     * 初始化历史搜索适配器
     */
    private void initHistorySearchAdapter() {
        RecyclerView mHistorySearchRecyclerView = findViewById(R.id.history_search_ry);
        mHomeHistorySearchAdapter = new HomeHistorySearchAdapter(mHistorySearchData);
        mHistorySearchRecyclerView.setAdapter(mHomeHistorySearchAdapter);
        mHistorySearchRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mHomeHistorySearchAdapter.setOnItemClickListener((adapter, view, position) -> {
            String content = (String) adapter.getData().get(position);
            toast(content);
        });
    }

    /**
     * 得到热门搜索数据
     */
    private void getHotSearchData() {
        // TODO 从后台获取
        mHotSearchData.add("披萨工坊");
        mHotSearchData.add("港式便当");
        mHotSearchData.add("海底捞");
        mHotSearchData.add("欢乐牧场");
        mHotSearchData.add("海鲜航母王");
        mHomeHotSearchAdapter.notifyDataSetChanged();
    }

    /**
     * 得到历史搜索数据
     */
    private void getHistorySearchdata() {
        // TODO 从数据库获取
        mHistorySearchData.add("环球时代影城");
        mHistorySearchData.add("鱼的错");
        mHistorySearchData.add("极度沸点KTV");
        mHistorySearchData.add("欢乐牧场");
        mHistorySearchData.add("一只鸡腿传说");
        mHomeHistorySearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.common_toolbar_function_right:
                break;
            default:
                break;
        }
    }
}

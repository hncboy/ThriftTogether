package com.pro516.thrifttogether.ui.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.home.adapter.HomeHistorySearchAdapter;
import com.pro516.thrifttogether.ui.home.adapter.HomeHotSearchAdapter;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_HISTORY;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_HOT;
import static com.pro516.thrifttogether.ui.network.Url.SEARCH_HISTORY;
import static com.pro516.thrifttogether.ui.network.Url.SEARCH_HOT;

/**
 * 搜索
 */
public class HomeSearchActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<String> mHotSearchData;
    private ArrayList<String> mHistorySearchData;
    private HomeHotSearchAdapter mHomeHotSearchAdapter;
    private HomeHistorySearchAdapter mHomeHistorySearchAdapter;
    private AppCompatEditText appCompatEditText;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void init() {
        initToolbar();
        initRecyclerView();
        appCompatEditText = findViewById(R.id.search);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHotSearchData.clear();
        mHistorySearchData.clear();
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
            Intent intent = new Intent(HomeSearchActivity.this, HomeSearchResultActivity.class);
            intent.putExtra("key", content);
            startActivity(intent);
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
            Intent intent = new Intent(HomeSearchActivity.this, HomeSearchResultActivity.class);
            intent.putExtra("key", content);
            startActivity(intent);
        });
    }

    /**
     * 得到热门搜索数据
     */
    private void getHotSearchData() {
        // TODO 从后台获取
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String json = HttpUtils.getStringFromServer(SEARCH_HOT);
                    mHotSearchData = JsonParser.Search(json);
                    Log.d("hhh", "run: " + mHotSearchData);
                    mHandler.obtainMessage(LOAD_HOT, mHotSearchData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR, mHotSearchData).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 得到历史搜索数据
     */
    private void getHistorySearchdata() {
        // TODO 从数据库获取
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String json = HttpUtils.getStringFromServer(SEARCH_HISTORY);
                    mHistorySearchData = JsonParser.Search(json);
                    Log.d("ddd", "run: " + mHistorySearchData);
                    mHandler.obtainMessage(LOAD_HISTORY, mHistorySearchData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR, mHistorySearchData).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_HISTORY:
                    mHomeHistorySearchAdapter.notifyDataSetChanged();
                    initHistorySearchAdapter();
                    break;
                case LOAD_HOT:
                    mHomeHotSearchAdapter.notifyDataSetChanged();
                    initHotSearchAdapter();
                    break;
                case ERROR:
                    Toast.makeText(HomeSearchActivity.this, getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.common_toolbar_function_right:
                String searchContent = Objects.requireNonNull(appCompatEditText.getText()).toString();
                if (searchContent.equals("")) {
                    break;
                }
                Intent intent = new Intent(HomeSearchActivity.this, HomeSearchResultActivity.class);
                intent.putExtra("key", searchContent);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

package com.pro516.thrifttogether.ui.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.ShopBean;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.home.adapter.GirdDropDownAdapter;
import com.pro516.thrifttogether.ui.mine.adapter.ShopAdapter;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;
import com.yyydjk.library.DropDownMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.SHOP_FOODS;

/**
 * 美食
 */
public class HomeFoodActivity extends BaseActivity implements View.OnClickListener {

    private DropDownMenu mDropDownMenu;
    private GirdDropDownAdapter allFoodAdapter;
    private GirdDropDownAdapter nearbyAdapter;
    private GirdDropDownAdapter sortAdapter;
    private GirdDropDownAdapter filterAdapter;
    private List<View> popupViews = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefresh;

    private String headers[] = {"全部美食", "附近", "智能排序", "筛选"};
    private String allFoods[] = {"不限", "特色菜", "福建菜", "日本菜", "饮品店", "面包甜点", "生日蛋糕"};
    private String nearbys[] = {"附近", "海曙区", "鄞州区", "江北区", "北仑区", "镇海区", "余姚区", "慈溪市", "奉化市", "宁海县", "象山县"};
    private String sortings[] = {"智能排序", "离我最近", "好评优先", "销量最高"};
    private String filters[] = {"不限", "单人餐", "双人餐", "3~4人餐", "5~10人餐"};

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_food;
    }

    @Override
    protected void init() {
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        initToolbar();
        initDropMenu();

        loadData(SHOP_FOODS);
        initRefreshLayout();
    }

    private void initRecyclerView(List<ShopBean> mData) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        Random r = new Random();
        int n2 = r.nextInt(100);
        int n3 = r.nextInt(500) + 499;
        System.out.println("n2:"+n2);
        System.out.println("n3:"+n3);

        ShopAdapter mAdapter = new ShopAdapter(R.layout.item_shop, mData.subList(n2,n3));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(HomeFoodActivity.this,StoreActivity.class);
            intent.putExtra("storeId",mData.get(position).getShopId());
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
                    Toast.makeText(HomeFoodActivity.this, getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
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

    private void loadData(String string) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(string);
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
            loadData(SHOP_FOODS);
        });
    }

    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        backImgBtn.setVisibility(View.VISIBLE);
        backImgBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        AppCompatImageButton searchImgBtn = findViewById(R.id.common_toolbar_function_right);
        searchImgBtn.setVisibility(View.VISIBLE);
        searchImgBtn.setImageDrawable(getDrawable(R.drawable.ic_search_white_24dp));
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.food));
        backImgBtn.setOnClickListener(this);
        searchImgBtn.setOnClickListener(this);
    }

    private void initDropMenu() {
        // 全部美食
        ListView allFoodView = new ListView(this);
        allFoodAdapter = new GirdDropDownAdapter(this, Arrays.asList(allFoods));
        allFoodView.setDividerHeight(0);
        allFoodView.setAdapter(allFoodAdapter);
        allFoodView.setOnItemClickListener((parent, view, position, id) -> {
            allFoodAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[0] : allFoods[position]);
            loadData(SHOP_FOODS);
            mDropDownMenu.closeMenu();
        });

        // 附近
        ListView nearbyView = new ListView(this);
        nearbyAdapter = new GirdDropDownAdapter(this, Arrays.asList(nearbys));
        nearbyView.setDividerHeight(0);
        nearbyView.setAdapter(nearbyAdapter);
        nearbyView.setOnItemClickListener((parent, view, position, id) -> {
            nearbyAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[1] : nearbys[position]);
            loadData(SHOP_FOODS);
            mDropDownMenu.closeMenu();
        });

        // 智能排序
        ListView sortView = new ListView(this);
        sortAdapter = new GirdDropDownAdapter(this, Arrays.asList(sortings));
        sortView.setDividerHeight(0);
        sortView.setAdapter(sortAdapter);
        sortView.setOnItemClickListener((parent, view, position, id) -> {
            sortAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[2] : sortings[position]);
            loadData(SHOP_FOODS);
            mDropDownMenu.closeMenu();
        });

        // 筛选
        ListView filterView = new ListView(this);
        filterAdapter = new GirdDropDownAdapter(this, Arrays.asList(filters));
        filterView.setDividerHeight(0);
        filterView.setAdapter(filterAdapter);
        filterView.setOnItemClickListener((parent, view, position, id) -> {
            filterAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[3] : filters[position]);
            loadData(SHOP_FOODS);
            mDropDownMenu.closeMenu();
        });

        popupViews.add(allFoodView);
        popupViews.add(nearbyView);
        popupViews.add(sortView);
        popupViews.add(filterView);

        mRecyclerView=new RecyclerView(this);
        mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mDropDownMenu = findViewById(R.id.dropDownMenu);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mRecyclerView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.common_toolbar_function_right:
                startActivity(HomeSearchActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
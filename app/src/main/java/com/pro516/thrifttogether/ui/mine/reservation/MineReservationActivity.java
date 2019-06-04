package com.pro516.thrifttogether.ui.mine.reservation;

import android.annotation.SuppressLint;
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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.ReservationBean;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.discover.bean.DiscoverShopVO;
import com.pro516.thrifttogether.ui.mine.adapter.ReservationAdapter;
import com.pro516.thrifttogether.ui.mine.reservation.vo.SimpleReservationVO;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;

public class MineReservationActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    private SwipeRefreshLayout mSwipeRefresh;
    private ProgressBar mProgressBar;
    RecyclerView mRecyclerView;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_mine_reservation;
    }

    @Override
    protected void init() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("我的预订");
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        initRefreshLayout();
        initData();
    }

    private void initRecyclerView(List<SimpleReservationVO> mData) {
        Log.i("aaa","start init data");
        mRecyclerView = findViewById(R.id.mine_reservation);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MineReservationActivity.this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MineReservationActivity.this, DividerItemDecoration.VERTICAL_LIST));
        ReservationAdapter mAdapter = new ReservationAdapter(R.layout.item_mine_reservation, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MineReservationActivity.this, "点击：" + position, Toast.LENGTH_SHORT).show();
                //startActivity(OrderDetailsActivity.class);
            }
        });
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(MineReservationActivity.this, getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    break;
                case LOAD_ALL:
                    //initListView((List<ShopBean>) msg.obj);
                    initRecyclerView((List<SimpleReservationVO>) msg.obj);
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
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String json = HttpUtils.getStringFromServer(Url.RESERVATION+"/user/1");
                    List<SimpleReservationVO> mData=JsonParser.getUserAllReservation(json);
                    Log.i("aaa","load all"+mData.size());
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    Log.i("aaa","load failed");
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void initRefreshLayout() {
        mSwipeRefresh = findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(this::initData);
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

    @Override
    public void onLoadMoreRequested() {

    }
}

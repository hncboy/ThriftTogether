package com.pro516.thrifttogether.ui.mall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mall.SimpleCouponVO;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.io.IOException;
import java.util.List;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;

/**
 * Created by hncboy on 2019-03-19.
 */
public class MallFragment extends BaseFragment implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefresh;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void init(View view) {
        TextView mTitle = view.findViewById(R.id.title);
        mTitle.setText(getString(R.string.points_mall));
        initRefreshLayout(view);
        mRecyclerView = view.findViewById(R.id.mall_recyclerView);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
    }

    private void initRecyclerView(List<SimpleCouponVO> mData) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        MallAdapter mAdapter = new MallAdapter(R.layout.item_mall, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getActivity(), "ID：" + mData.get(position).getCouponId(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),MallExchangeActivity.class);
                intent.putExtra("ID",mData.get(position).getCouponId());
                startActivity(intent);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(getActivity(), getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    break;
                case LOAD_ALL:
                    initRecyclerView((List<SimpleCouponVO>) msg.obj);
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
                    String json = HttpUtils.getStringFromServer(Url.COUPON);
                    List<SimpleCouponVO> mData = JsonParser.VoucherPackage(json);
                    //System.out.println("---------------------------->" + mData.get(0).getImg());
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initRefreshLayout(View view) {
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(() -> {
            loadData();
        });
    }

    @Override
    public void onClick(View v) {

    }
}

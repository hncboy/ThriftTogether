package com.pro516.thrifttogether.ui.mall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mall.SimpleCouponVO;
import com.pro516.thrifttogether.entity.mine.User;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.UPDATE_AFTER_EXCHANGE;
import static com.pro516.thrifttogether.ui.network.Url.USER_INFO;

/**
 * Created by hncboy on 2019-03-19.
 */
public class MallFragment extends BaseFragment implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefresh;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private ImageView headPortrait;
    private TextView username;
    private TextView integral;
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
        headPortrait = view.findViewById(R.id.head_portrait);
        username = view.findViewById(R.id.username);
        integral=view.findViewById(R.id.integral);
        loadData();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
        initView();
    }

    private void updateData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String json = HttpUtils.getStringFromServer(USER_INFO);
                    User mData = JsonParser.getUserInfo(json);
                    mHandler.obtainMessage(UPDATE_AFTER_EXCHANGE, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView(){
        SharedPreferences userSettings =  Objects.requireNonNull(getActivity()).getSharedPreferences("setting", Context.MODE_PRIVATE);
        String avatorUrl=userSettings.getString("avatorUrl","http://img.52z.com/upload/news/image/20180122/20180122093427_87666.jpg");
        String name=userSettings.getString("name","mike");
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        Glide.with(this).load(avatorUrl).apply(mRequestOptions).into(headPortrait);
        username.setText(name);
        integral.setText("积分："+userSettings.getInt("integral",0));
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
                case UPDATE_AFTER_EXCHANGE:
                    User userInfo = ((User) msg.obj);
                    SharedPreferences userSettings = Objects.requireNonNull(getActivity()).getSharedPreferences("setting", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userSettings.edit();
                    editor.putInt("integral",userInfo.getIntegral());
                    editor.apply();
                    integral.setText("积分："+userInfo.getIntegral());
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

package com.pro516.thrifttogether.ui.mine.collection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.CollectedShopVO;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.mine.adapter.CollectionShopAdapter;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.io.IOException;
import java.util.List;

import static com.pro516.thrifttogether.ui.network.Url.COLLECTION;
import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.userID;

public class ShopFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void init(View view) {
        mRecyclerView = view.findViewById(R.id.mine_reservation);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
        initRefreshLayout(view);
    }

    private void initRecyclerView(List<CollectedShopVO> mData) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        CollectionShopAdapter mAdapter = new CollectionShopAdapter(R.layout.item_shop, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("团节", "onItemClick: ");
                Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("团节", "onItemLongClick: ");
                Toast.makeText(getActivity(), "shopID" + mData.get(position).getId(), Toast.LENGTH_SHORT).show();
                deleteConfirmationDialog(position,COLLECTION+userID+"/shop/"+mData.get(position).getId(),getActivity());
                loadData();
                return false;
            }
        });

        mRecyclerView.setAdapter(mAdapter);
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
                    initRecyclerView((List<CollectedShopVO>) msg.obj);
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

    public void deleteConfirmationDialog(int position, String url, Context context) {
        MaterialDialog.Builder mBuilder = new MaterialDialog.Builder(context);
        mBuilder.content("确认删除吗？");
        mBuilder.contentColor(Color.parseColor("#000000"));
        mBuilder.positiveText("确定");
        mBuilder.titleGravity(GravityEnum.CENTER);
        mBuilder.buttonsGravity(GravityEnum.START);
        mBuilder.negativeText("取消");
        mBuilder.cancelable(false);
        MaterialDialog mMaterialDialog = mBuilder.build();
        mMaterialDialog.show();
        mBuilder.onAny((dialog, which) -> {
            if (which == DialogAction.POSITIVE) {
                Toast.makeText(context, "进行删除操作: pos " + position, Toast.LENGTH_LONG).show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            HttpUtils.doDelete(url);
                            loadData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                mMaterialDialog.dismiss();
            } else if (which == DialogAction.NEGATIVE) {
                mMaterialDialog.dismiss();
            }
        });
    }

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(COLLECTION+userID+"/shop");
                    List<CollectedShopVO> mData = JsonParser.collectionShopList(json);
                    System.out.println("---------------------------->" + mData);
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
    public void onClick(View view) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine_collection_shops;
    }
}

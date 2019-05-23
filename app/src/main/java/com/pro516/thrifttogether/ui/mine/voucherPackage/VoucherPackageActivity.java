package com.pro516.thrifttogether.ui.mine.voucherPackage;

import android.os.Bundle;
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
import com.pro516.thrifttogether.entity.mine.VoucherPackageBean;
import com.pro516.thrifttogether.ui.mine.adapter.VoucherPackageAdapter;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoucherPackageActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    List<VoucherPackageBean> mData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_voucher_package;
    }

    @Override
    protected void init() {
        setListeners();
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("券包");
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.voucher_package_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(VoucherPackageActivity.this, DividerItemDecoration.VERTICAL_LIST));
        VoucherPackageAdapter mAdapter = new VoucherPackageAdapter(R.layout.item_mine_voucher_package, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(VoucherPackageActivity.this, "点击：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add(new VoucherPackageBean("5元券",new Date(),5));
        }
    }

    @Override
    public void onLoadMoreRequested() {

    }

    private void setListeners() {

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

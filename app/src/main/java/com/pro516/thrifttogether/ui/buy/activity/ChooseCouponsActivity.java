package com.pro516.thrifttogether.ui.buy.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.UserCoupon;
import com.pro516.thrifttogether.ui.mall.MallExchangeActivity;
import com.pro516.thrifttogether.ui.mine.adapter.VoucherPackageAdapter;
import com.pro516.thrifttogether.ui.mine.voucherPackage.VoucherPackageActivity;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.util.List;

public class ChooseCouponsActivity extends VoucherPackageActivity {

    @Override
    protected void initRecyclerView(List<UserCoupon> mData) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        VoucherPackageAdapter mAdapter = new VoucherPackageAdapter(R.layout.item_mine_voucher_package, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ChooseCouponsActivity.this, MallExchangeActivity.class);
                intent.putExtra("chosenCouponId", mData.get(position).getUserCouponId());
                intent.putExtra("chosenCouponName", mData.get(position).getCouponName());
                intent.putExtra("chosenCouponPrice",mData.get(position).getCouponDiscountedPrice());
                //设置返回数据
                ChooseCouponsActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                ChooseCouponsActivity.this.finish();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}

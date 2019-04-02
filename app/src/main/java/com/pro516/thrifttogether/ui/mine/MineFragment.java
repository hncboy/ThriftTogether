package com.pro516.thrifttogether.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.mine.collection.MineCollectionActivity;
import com.pro516.thrifttogether.ui.mine.order.BeforePaymentActivity;
import com.pro516.thrifttogether.ui.mine.settings.MineSetting;

/**
 * Created by hncboy on 2019-03-19.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mineSetting;
    private LinearLayout mineCollection;
    private LinearLayout mineRecentlyViewed;
    private LinearLayout mineOrderPendingPayment;
    @Override
    protected void init(View view) {
        mineSetting = view.findViewById(R.id.mine_setting);
        mineCollection=view.findViewById(R.id.mine_collection);
        mineRecentlyViewed=view.findViewById(R.id.mine_recently_viewed);
        mineOrderPendingPayment=view.findViewById(R.id.mine_order_pending_payment);
        setListeners();
    }

    private void setListeners() {
        mineSetting.setOnClickListener(this);
        mineCollection.setOnClickListener(this);
        mineRecentlyViewed.setOnClickListener(this);
        mineOrderPendingPayment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_setting:
                startActivity(MineSetting.class);
                break;
            case R.id.mine_collection:
                startActivity(MineCollectionActivity.class);
                break;
            case R.id.mine_recently_viewed:
                startActivity(RecentlyViewedActivity.class);
                break;
            case R.id.mine_order_pending_payment:
                startActivity(BeforePaymentActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine;
    }
}

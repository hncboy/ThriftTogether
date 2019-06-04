package com.pro516.thrifttogether.ui.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.mine.collection.MineCollectionActivity;
import com.pro516.thrifttogether.ui.mine.feedback.MineFeedBackActivity;
import com.pro516.thrifttogether.ui.mine.notification.NotificationActivity;
import com.pro516.thrifttogether.ui.mine.order.OrderActivity;
import com.pro516.thrifttogether.ui.mine.recentlyViewed.RecentlyViewedActivity;
import com.pro516.thrifttogether.ui.mine.reservation.MineReservationActivity;
import com.pro516.thrifttogether.ui.mine.settings.EditPersonalInformationActivity;
import com.pro516.thrifttogether.ui.mine.settings.MineSetting;
import com.pro516.thrifttogether.ui.mine.voucherPackage.VoucherPackageActivity;

import java.util.Objects;


/**
 * Created by hncboy on 2019-03-19.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mineSetting;
    private LinearLayout mineCollection;
    private LinearLayout mineRecentlyViewed;
    private LinearLayout mineOrderPendingPayment;
    private LinearLayout mineOrderToBeUsed;
    private LinearLayout mineOrderToBeEvaluate;
    private LinearLayout mineOrderRefundOrAfterSales;
    private LinearLayout mineVoucherPackage;
    private LinearLayout mineFeedBack;
    private LinearLayout mineAllOrders;
    private LinearLayout mineReservation;
    private ImageView mineNotification;
    private ImageView headPortrait;
    private TextView username;

    @Override
    protected void init(View view) {
        mineSetting = view.findViewById(R.id.mine_setting);
        mineCollection = view.findViewById(R.id.mine_collection);
        mineRecentlyViewed = view.findViewById(R.id.mine_recently_viewed);
        mineOrderPendingPayment = view.findViewById(R.id.mine_order_pending_payment);
        mineOrderToBeUsed = view.findViewById(R.id.mine_order_to_be_used);
        mineOrderToBeEvaluate = view.findViewById(R.id.mine_order_to_be_evaluate);
        mineOrderRefundOrAfterSales = view.findViewById(R.id.mine_order_refund_or_after_sales);
        mineVoucherPackage = view.findViewById(R.id.mine_voucher_package);
        mineFeedBack = view.findViewById(R.id.mine_feedback);
        mineAllOrders = view.findViewById(R.id.mine_all_order);
        mineReservation = view.findViewById(R.id.mine_reservation);
        mineNotification = view.findViewById(R.id.mine_notification);
        headPortrait = view.findViewById(R.id.headPortrait);
        username = view.findViewById(R.id.username);
        setListeners();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        SharedPreferences userSettings =  Objects.requireNonNull(getActivity()).getSharedPreferences("setting", Context.MODE_PRIVATE);
        String avatorUrl=userSettings.getString("avatorUrl","http://img.52z.com/upload/news/image/20180122/20180122093427_87666.jpg");
        String name=userSettings.getString("name","mike");
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        Glide.with(this).load(avatorUrl).apply(mRequestOptions).into(headPortrait);
        username.setText(name);
    }

    private void setListeners() {
        mineSetting.setOnClickListener(this);
        mineCollection.setOnClickListener(this);
        mineRecentlyViewed.setOnClickListener(this);
        mineOrderPendingPayment.setOnClickListener(this);
        mineOrderToBeUsed.setOnClickListener(this);
        mineOrderToBeEvaluate.setOnClickListener(this);
        mineOrderRefundOrAfterSales.setOnClickListener(this);
        mineVoucherPackage.setOnClickListener(this);
        mineFeedBack.setOnClickListener(this);
        mineAllOrders.setOnClickListener(this);
        mineReservation.setOnClickListener(this);
        mineNotification.setOnClickListener(this);
        headPortrait.setOnClickListener(this);
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
            case R.id.mine_voucher_package:
                startActivity(VoucherPackageActivity.class);
                break;
            case R.id.mine_feedback:
                startActivity(MineFeedBackActivity.class);
                break;
            case R.id.mine_all_order:
                startActivity(OrderActivity.class, "id", "0");
                break;
            case R.id.mine_order_pending_payment:
                startActivity(OrderActivity.class, "id", "1");
                break;
            case R.id.mine_order_to_be_used:
                startActivity(OrderActivity.class, "id", "2");
                break;
            case R.id.mine_order_to_be_evaluate:
                startActivity(OrderActivity.class, "id", "3");
                break;
            case R.id.mine_order_refund_or_after_sales:
                startActivity(OrderActivity.class, "id", "4");
                break;
            case R.id.mine_reservation:
                startActivity(MineReservationActivity.class);
                break;
            case R.id.mine_notification:
                startActivity(NotificationActivity.class);
                break;
            case R.id.headPortrait:
                startActivity(EditPersonalInformationActivity.class);
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

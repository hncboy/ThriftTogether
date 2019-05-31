package com.pro516.thrifttogether.ui.home.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.OrderBean;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.buy.activity.ProductInfoActivity;
import com.pro516.thrifttogether.ui.discover.DiscoverFragment;
import com.pro516.thrifttogether.ui.discover.adapter.SurroundingShopsAdapter;
import com.pro516.thrifttogether.ui.home.adapter.ShowStoreProductAdapter;
import com.pro516.thrifttogether.ui.home.entity.StoreProduct;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import org.jaaksi.pickerview.picker.TimePicker;
import org.jaaksi.pickerview.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StoreActivity extends BaseActivity implements View.OnClickListener, TimePicker.OnTimeSelectListener {
    List<StoreProduct> mData;
    RecyclerView mRecyclerView;
    ShowStoreProductAdapter mAdapter;
    private Button buyButton;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_store;
    }

    @Override
    protected void init() {
        initToolbar();
        initData();
        initRecyclerView(findViewById(R.id.store_product_recycle));
        Button mReservation = findViewById(R.id.store_reservation);
        mReservation.setOnClickListener(this);
    }
    private void initData(){
        mData = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mData.add(new StoreProduct(
                    "https://img.meituan.net/msmerchant/53016dc6b5bb3d03729e5cb3eea09550401792.jpg@380w_214h_1e_1c",
                    "干锅土豆鸡",
                    "周一到周日",
                    "123" ));
        }
    }
    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.store_product_recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new ShowStoreProductAdapter(R.layout.item_store_product, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.setAdapter(mAdapter);
        //item点击事件
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                buyButton = view.findViewById(R.id.store_product_buy_button);
                //导航按钮点击事件
                buyButton.setOnClickListener(StoreActivity.this);
            }
        });
    }
    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        backImgBtn.setOnClickListener(this);
    }

    public static final DateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    public static final SimpleDateFormat mDateFormat =
            new SimpleDateFormat("MM月dd日  E", Locale.CHINA);

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.store_reservation:
                Calendar calendar = Calendar.getInstance();
                long createTime = calendar.getTimeInMillis();
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);
                TimePicker mTimePicker = new TimePicker.Builder(this, TimePicker.TYPE_MIXED_DATE | TimePicker.TYPE_MIXED_TIME,
                        this)
                        // 设置不包含超出的结束时间<=
                        .setContainsEndDate(false)
                        // 设置时间间隔为30分钟
                        .setTimeMinuteOffset(60).setRangDate(createTime, calendar.getTimeInMillis())
                        .setFormatter(new TimePicker.DefaultFormatter() {
                            @Override
                            public CharSequence format(TimePicker picker, int type, int position, long value) {
                                if (type == TimePicker.TYPE_MIXED_DATE) {
                                    CharSequence text;
                                    int dayOffset = DateUtil.getDayOffset(value, System.currentTimeMillis());
                                    if (dayOffset == 0) {
                                        text = "今天";
                                    } else if (dayOffset == 1) {
                                        text = "明天";
                                    } else { // xx月xx日 星期 x
                                        text = mDateFormat.format(value);
                                    }
                                    return text;
                                }
                                return super.format(picker, type, position, value);
                            }
                        })
                        .create();
                Dialog pickerDialog = mTimePicker.getPickerDialog();
                pickerDialog.setCanceledOnTouchOutside(true);
                mTimePicker.getTopBar().getTitleView().setText("请选择时间");
                mTimePicker.show();
                break;
            case R.id.store_product_buy_button:
                startActivity(ProductInfoActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSelect(TimePicker picker, Date date) {
        Toast.makeText(StoreActivity.this, "点击：" + sSimpleDateFormat.format(date), Toast.LENGTH_SHORT).show();
        //传递些简单的参数
        Intent intent = new Intent();
        intent.setClass(StoreActivity.this,ReservationDetailsActivity.class);
        Bundle bundleSimple = new Bundle();
        bundleSimple.putString("time", sSimpleDateFormat.format(date));
        intent.putExtras(bundleSimple);
        startActivity(intent);
    }
}

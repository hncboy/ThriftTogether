package com.pro516.thrifttogether.ui.home.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.buy.activity.ProductInfoActivity;
import com.pro516.thrifttogether.ui.buy.layoutManage.MyLinearLayoutManager;
import com.pro516.thrifttogether.ui.home.adapter.ShowCommentsAdapter;
import com.pro516.thrifttogether.ui.home.adapter.ShowStoreProductAdapter;
import com.pro516.thrifttogether.ui.home.entity.VO.ShopDetailsVO;
import com.pro516.thrifttogether.ui.home.entity.VO.SimpleProductVO;
import com.pro516.thrifttogether.ui.home.entity.VO.SimpleReviewVO;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import org.jaaksi.pickerview.picker.TimePicker;
import org.jaaksi.pickerview.util.DateUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Response;

public class StoreActivity extends BaseActivity implements View.OnClickListener, TimePicker.OnTimeSelectListener {
    private RecyclerView mProductsRV, mCommentsRV;
    private TextView storeNameTv, priceTv, storeAddressTv;
    private SimpleRatingBar ratingBar;
    private ShowStoreProductAdapter mAdapter;
    private ProgressBar mProgressBar,mProgressBar2;
    private Button buyButton;
    private AppCompatImageButton love, share;
    private List<Integer> productId;
    private int storeId, isStar = 0;
    private static final int ERROR = -666;
    private static final int LOAD_ALL = 1;
    private static final int LOAD_ALL_REVIEW = 6;
    private static final int STAR_SUCCESSFUL = 2;
    private static final int CANCEL_STAR_SUCCESSFUL = 3;
    private static final int STAR_UNSUCCESSFUL = 4;
    private static final int CANCEL_STAR_UNSUCCESSFUL = 5;
    public static final DateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
    public static final SimpleDateFormat mDateFormat = new SimpleDateFormat("MM月dd日  E", Locale.CHINA);
    private int clickPosition;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_store;
    }

    @Override
    protected void init() {
        storeId = getIntent().getIntExtra("storeId", 1);
        initView();
        initData();
        Button mReservation = findViewById(R.id.store_reservation);
        mReservation.setOnClickListener(this);
    }

    private void initView() {
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar2 = findViewById(R.id.progress_bar2);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar2.setVisibility(View.VISIBLE);
        mProductsRV = findViewById(R.id.store_product_recycle);
        mCommentsRV = findViewById(R.id.store_product_comments_recycle);
        storeNameTv = findViewById(R.id.store_name_tv);
        priceTv = findViewById(R.id.store_price);
        storeAddressTv = findViewById(R.id.store_address);
        ratingBar = findViewById(R.id.store_rating);
        love = findViewById(R.id.common_toolbar_function_right_1);
        //share = findViewById(R.id.common_toolbar_function_right_2);
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        backImgBtn.setOnClickListener(this);
        love.setOnClickListener(this);
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(Url.SHOP + "/" + storeId + "/user/1");
                    ShopDetailsVO mData = JsonParser.shopDetail(json);
                    //System.out.println("---------------------------->" + mData);
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.i("json",Url.REVIEW + "/shop/" + storeId);
                    String json = HttpUtils.getStringFromServer(Url.REVIEW + "/shop/" + storeId);
                    Log.i("json",json+" ");
                    List<SimpleReviewVO> mData = JsonParser.storeReview(json);
                    //System.out.println("---------------------------->" + mData);
                    mHandler.obtainMessage(LOAD_ALL_REVIEW, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(StoreActivity.this, getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    break;
                case LOAD_ALL:
                    //initListView((List<ShopBean>) msg.obj);
                    initPage((ShopDetailsVO) msg.obj);
                    initProductsRV(((ShopDetailsVO) msg.obj).getSimpleProductList());
                    mProgressBar.setVisibility(View.GONE);

                    break;
                case LOAD_ALL_REVIEW:
                    initCommentsRV((List<SimpleReviewVO>)msg.obj);
                    mProgressBar2.setVisibility(View.GONE);
                    break;
                case STAR_SUCCESSFUL:
                    toast("收藏成功");
                    love.setImageResource(R.drawable.ic_favorite_black);
                    isStar = 1;
                    break;
                case CANCEL_STAR_SUCCESSFUL:
                    toast("取消收藏成功");
                    love.setImageResource(R.drawable.ic_favorite_border_black);
                    isStar = 0;
                    break;
                case STAR_UNSUCCESSFUL:
                    toast("收藏失败");
                    break;
                case CANCEL_STAR_UNSUCCESSFUL:
                    toast("取消收藏失败");
                    break;
                default:
                    break;
            }
        }
    };

    private void initPage(ShopDetailsVO obj) {
        storeNameTv.setText(obj.getShopName());
        storeAddressTv.setText(obj.getShopAddress());
        priceTv.setText(String.format(Locale.CHINA, "￥%d/人", obj.getShopAveragePrice()));
        ratingBar.setRating(obj.getShopAverageScore().floatValue());
        if (obj.getIsCollected()==1){
            love.setImageResource(R.drawable.ic_favorite_black);
            isStar = 1;
        } else {
            love.setImageResource(R.drawable.ic_favorite_border_black);
            isStar = 0;
        }

    }

    private void initProductsRV(List<SimpleProductVO> data) {
        productId = new ArrayList<>();
        mProductsRV.setLayoutManager(new LinearLayoutManager(this));
        mProductsRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new ShowStoreProductAdapter(R.layout.item_store_product, data,productId);
        mProductsRV.setNestedScrollingEnabled(false);
        //mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        //mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mProductsRV.setAdapter(mAdapter);
        //item点击事件
        mProductsRV.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                buyButton = view.findViewById(R.id.store_product_buy_button);
                clickPosition = position;
                //导航按钮点击事件
                buyButton.setOnClickListener(StoreActivity.this);
            }
        });
    }
    private void initCommentsRV(List<SimpleReviewVO> data){
        mCommentsRV.setLayoutManager(new LinearLayoutManager(this));
        mCommentsRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mCommentsRV.setNestedScrollingEnabled(false);
        ShowCommentsAdapter mAdapter = new ShowCommentsAdapter(this,R.layout.item_store_evaluation, data);
        //mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        //mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mCommentsRV.setAdapter(mAdapter);
        //item点击事件
    }
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
                Intent intent = new Intent(StoreActivity.this,ProductInfoActivity.class);
                intent.putExtra("storeId",storeId);
                intent.putExtra("productId", productId.get(clickPosition));
                startActivity(intent);
                break;
            case R.id.common_toolbar_function_right_1:
                if (isStar==0){
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Response rs = HttpUtils.doDelete(Url.COLLECTION + "/1/shop/" + storeId );
                                boolean mData = JsonParser.starStore(rs.body().string());
                                //System.out.println("---------------------------->" + mData);
                                if (mData)
                                    mHandler.obtainMessage(STAR_SUCCESSFUL).sendToTarget();
                                else
                                    mHandler.obtainMessage(STAR_UNSUCCESSFUL).sendToTarget();
                            } catch (IOException e) {
                                mHandler.obtainMessage(ERROR).sendToTarget();
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Response rs = HttpUtils.doGet(Url.COLLECTION + "/1/shop/" + storeId );
                                boolean mData = JsonParser.cancelStarStore(rs.body().string());
                                //System.out.println("---------------------------->" + mData);
                                if (mData)
                                    mHandler.obtainMessage(CANCEL_STAR_SUCCESSFUL).sendToTarget();
                                else
                                    mHandler.obtainMessage(CANCEL_STAR_UNSUCCESSFUL).sendToTarget();
                            } catch (IOException e) {
                                mHandler.obtainMessage(ERROR).sendToTarget();
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
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
        intent.setClass(StoreActivity.this, ReservationDetailsActivity.class);
        Bundle bundleSimple = new Bundle();
        bundleSimple.putString("time", sSimpleDateFormat.format(date));
        bundleSimple.putInt("storeId",storeId);
        intent.putExtras(bundleSimple);
        startActivity(intent);
    }
}

package com.pro516.thrifttogether.ui.mall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mall.CouponDetailsVO;
import com.pro516.thrifttogether.entity.mall.SimpleCouponVO;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;

import java.io.IOException;
import java.util.Objects;

import static com.pro516.thrifttogether.ui.network.Url.COUPON;
import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.userID;

public class MallExchangeActivity extends BaseActivity implements View.OnClickListener {

    private int mID;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_mall_exchange;
    }

    @Override
    protected void init() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("兑换");

        Button button = findViewById(R.id.exchange_btn);
        button.setOnClickListener(this);
        Intent intent = getIntent();
        mID = intent.getIntExtra("ID", 2);
        loadData();
    }

    private void initView(CouponDetailsVO couponDetailsVO) {
        ImageView imageView = findViewById(R.id.coupon_details_imageUrl);
        TextView couponIntegral = findViewById(R.id.coupon_integral);
        TextView couponInfo = findViewById(R.id.coupon_info);
        TextView couponExchangeInfo = findViewById(R.id.coupon_exchangeInfo);

        Glide.with(this).load(couponDetailsVO.getCouponDetailsImageUrl()).into(imageView);
        couponIntegral.setText("" + couponDetailsVO.getCouponIntegral());
        couponInfo.setText(couponDetailsVO.getCouponInfo());
        couponExchangeInfo.setText(couponDetailsVO.getCouponExchangeInfo());
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ERROR:
                    break;
                case LOAD_ALL:
                    initView((CouponDetailsVO) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    public void loadData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String json = HttpUtils.getStringFromServer(COUPON + "/" + mID);
                    CouponDetailsVO mData = JsonParser.VoucherPackageDetails(json);
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void exchange() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    HttpUtils.doGet(COUPON + "/" + mID + "/user/" + userID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Toast.makeText(this, "兑换成功", Toast.LENGTH_SHORT).show();
    }

    public void exchangeConfirmationDialog(Context context) {
        MaterialDialog.Builder mBuilder = new MaterialDialog.Builder(context);
        mBuilder.content("确认兑换吗？");
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
                exchange();
                mMaterialDialog.dismiss();
            } else if (which == DialogAction.NEGATIVE) {
                mMaterialDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.exchange_btn:
                exchangeConfirmationDialog(this);
                break;
            default:
                break;
        }
    }
}

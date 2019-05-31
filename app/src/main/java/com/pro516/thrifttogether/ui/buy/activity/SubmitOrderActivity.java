package com.pro516.thrifttogether.ui.buy.activity;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.buy.widget.AddAndSubView;

public class SubmitOrderActivity extends BaseActivity implements View.OnClickListener {

    AddAndSubView mAddAndSubView;
    TextView nameTv, priceTv, tempPriceTv, totalPriceTv, phoneTv, couponsTv;
    ImageView imgView;
    Button buyButton;
    RelativeLayout couponsRL;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_submit_order;
    }

    @Override
    protected void init() {
        initView();
        setListener();
    }

    private void initView() {
        mAddAndSubView = findViewById(R.id.submit_order_number_control);
        nameTv = findViewById(R.id.submit_order_name_tv);
        priceTv = findViewById(R.id.submit_order_price_tv);
        tempPriceTv = findViewById(R.id.submit_order_temp_price);
        totalPriceTv = findViewById(R.id.submit_order_total_price);
        phoneTv = findViewById(R.id.submit_order_phone_tv);
        couponsTv = findViewById(R.id.submit_order_coupons);
        imgView = findViewById(R.id.submit_order_img);
        buyButton = findViewById(R.id.submit_order_bt);
        couponsRL = findViewById(R.id.submit_order_rl);
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("测试文字");
    }

    private void setListener() {
        mAddAndSubView.setOnNumChangeListener((view, stype, num) -> {
            //点击加号，减号打印数量值
            Log.e("NUM", "====num=====" + num);
            double price = Double.parseDouble(priceTv.getText().toString());
            double temp = num * price;
            double now = Double.parseDouble(totalPriceTv.getText().toString().substring(1));
            if (stype == 0)
                now = now - price;
            else
                now = now + price;
            tempPriceTv.setText(String.format("￥%s", temp));
            totalPriceTv.setText(String.format("￥%s", now));
        });
        buyButton.setOnClickListener(this);
        couponsRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_order_bt:
                toast("还不能买！");
                break;
            case R.id.submit_order_rl:
                toast("还不能用优惠券");
                break;
        }
    }

}

package com.pro516.thrifttogether.ui.buy.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.OrderBean;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.buy.entity.VO.CreatedOrderVO;
import com.pro516.thrifttogether.ui.buy.widget.AddAndSubView;
import com.pro516.thrifttogether.util.OrderInfoUtil2_0;

import java.util.List;
import java.util.Map;

import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.APPID;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.PERMISSIONS_REQUEST_CODE;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.RSA2_PRIVATE;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.RSA_PRIVATE;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.SDK_PAY_FLAG;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.showAlert;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.showToast;

public class SubmitOrderActivity extends BaseActivity implements View.OnClickListener {

    AddAndSubView mAddAndSubView;
    TextView nameTv, priceTv, tempPriceTv, totalPriceTv, phoneTv, couponsTv;
    ImageView imgView;
    Button buyButton;
    RelativeLayout couponsRL;
    private int position;
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
    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {

                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
                    showToast(this, getString(R.string.permission_rejected));
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        showToast(this, getString(R.string.permission_rejected));
                        return;
                    }
                }

                // 所需的权限均正常获取
                showToast(this, getString(R.string.permission_granted));
            }
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {

            }
        }
    };
    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v, OrderBean mData, int pos) {
        position = pos;
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, mData, pos);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(SubmitOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_order_bt:
                CreatedOrderVO order = new CreatedOrderVO();
                order.setContactPhone(phoneTv.getText().toString());
                order.setProductAmountTotal(Double.parseDouble(totalPriceTv.getText().toString()));
                order.setProductBuyPrice(Double.parseDouble(priceTv.getText().toString()));
                order.setProductCount(mAddAndSubView.getNum());

                //payV2(v,);
                toast("还不能买！");
                break;
            case R.id.submit_order_rl:
                toast("还不能用优惠券");
                break;
        }
    }

}

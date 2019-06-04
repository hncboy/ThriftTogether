package com.pro516.thrifttogether.ui.buy.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.OrderBean;
import com.pro516.thrifttogether.entity.mine.UserCoupon;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.buy.entity.VO.CreatedOrderVO;
import com.pro516.thrifttogether.ui.buy.widget.AddAndSubView;
import com.pro516.thrifttogether.ui.mine.alipay.AuthResult;
import com.pro516.thrifttogether.ui.mine.alipay.PayResult;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;
import com.pro516.thrifttogether.util.OrderInfoUtil2_0;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Response;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.APPID;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.PERMISSIONS_REQUEST_CODE;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.RSA2_PRIVATE;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.RSA_PRIVATE;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.SDK_AUTH_FLAG;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.SDK_PAY_FLAG;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.showAlert;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.showToast;
import static com.pro516.thrifttogether.ui.network.Url.COUPON_USER;
import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.ORDER_DELETE_OR_UPDATE;

public class SubmitOrderActivity extends BaseActivity implements View.OnClickListener {

    private static final int SUBMIT_ORDER_SUCCESS = 100;
    private static final int SUBMIT_ORDER_ERROR = 200;
    AddAndSubView mAddAndSubView;
    TextView nameTv, priceTv, tempPriceTv, totalPriceTv, phoneTv, couponsTv;
    ImageView imgView;
    Button buyButton;
    RelativeLayout couponsRL,parentRL;
    SharedPreferences userSettings;
    Intent intent;
    CreatedOrderVO order;
    String newOrderId;
    private int position, usedCouponPrice;
    private boolean isHaveCoupon, isUseCoupon = false;
    private int usedCouponId;
    public static final DateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);

    @Override
    public int getLayoutRes() {
        return R.layout.activity_submit_order;
    }

    @Override
    protected void init() {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        userSettings = getSharedPreferences("setting", Context.MODE_PRIVATE);
        initView();
        loadData();
        setListener();
    }

    private void initView() {
        mAddAndSubView = findViewById(R.id.submit_order_number_control);
        intent = getIntent();
        nameTv = findViewById(R.id.submit_order_name_tv);
        priceTv = findViewById(R.id.submit_order_price_tv);
        tempPriceTv = findViewById(R.id.submit_order_temp_price);
        totalPriceTv = findViewById(R.id.submit_order_total_price);
        phoneTv = findViewById(R.id.submit_order_phone_tv);
        couponsTv = findViewById(R.id.submit_order_coupons);
        imgView = findViewById(R.id.submit_order_img);
        buyButton = findViewById(R.id.submit_order_bt);
        couponsRL = findViewById(R.id.submit_order_rl);
        parentRL = findViewById(R.id.submit_order);
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("确认订单");

        phoneTv.setText(userSettings.getString("phone","110"));
        priceTv.setText(String.valueOf(intent.getDoubleExtra("price",0)));
        tempPriceTv.setText(String.format("￥%s",intent.getDoubleExtra("price",0)));
        totalPriceTv.setText(String.format("￥%s",intent.getDoubleExtra("price",0)));
        nameTv.setText(intent.getStringExtra("name"));
        RoundedCorners roundedCorners = new RoundedCorners(30);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(this).load(intent.getStringExtra("img")).apply(options).into(imgView);
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
                case LOAD_ALL:
                    isHaveCoupon = ((List<UserCoupon>) msg.obj).size() > 0;
                    couponsTv.setText("选择优惠券");
                    break;
                case ERROR:
                    Toast.makeText(SubmitOrderActivity.this, getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    break;
                case SUBMIT_ORDER_SUCCESS:
                    OrderBean bean = new OrderBean();
                    Log.i("TAG", msg.obj.toString());
                    String temp = msg.obj.toString();
                    bean.setOrderNo(temp);
                    bean.setProductAmountTotal(order.getProductAmountTotal());
                    bean.setOrderStatus(1);
                    bean.setCreateTime(sSimpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis())));
                    bean.setProductName(intent.getStringExtra("name"));
                    payV2(parentRL,bean,0);
                    break;
                case SUBMIT_ORDER_ERROR:
                    Toast.makeText(SubmitOrderActivity.this, getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    break;
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //showAlert(getActivity(), getString(R.string.pay_success) + payResult);
                        Log.d(TAG, "handleMessage: position: " + position);
                        updateStatusLoadData();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(SubmitOrderActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(SubmitOrderActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(SubmitOrderActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
            }
        }
    };


    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(COUPON_USER);
                    List<UserCoupon> mData = JsonParser.UserVoucherPackageLists(json);
                    System.out.println("---------------------------->" + mData);
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_order_bt:
                order = new CreatedOrderVO();
                order.setContactPhone(phoneTv.getText().toString());
                order.setProductAmountTotal(Double.parseDouble(totalPriceTv.getText().toString().substring(1)));
                order.setProductBuyPrice(Double.parseDouble(priceTv.getText().toString()));
                order.setProductCount(mAddAndSubView.getNum());
                order.setUserId(userSettings.getInt("userId", 1));
                order.setContactPhone(phoneTv.getText().toString());
                order.setIsUseCoupon(isUseCoupon?1:0);
                order.setUserCouponId(usedCouponId);
                order.setProductId(intent.getIntExtra("id",1));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response json = HttpUtils.doPost(Url.ORDER,order);
                            String mData = JsonParser.getNewOrderId(json.body().string());
                            System.out.println("---------------------------->" + mData);
                            newOrderId = mData;
                            mHandler.obtainMessage(SUBMIT_ORDER_SUCCESS, mData).sendToTarget();
                        } catch (IOException e) {
                            mHandler.obtainMessage(SUBMIT_ORDER_ERROR).sendToTarget();
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.submit_order_rl:
                if (isHaveCoupon) {
                    startActivityForResult(new Intent(SubmitOrderActivity.this, ChooseCouponsActivity.class), 200);
                } else {
                    couponsTv.setText("暂无优惠券");
                }
                break;
            case R.id.common_toolbar_function_left:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200:
                assert data != null;
                couponsTv.setText(data.getStringExtra("chosenCouponName"));
                usedCouponId = data.getIntExtra("chosenCouponId", 0);
                int discount = data.getIntExtra("chosenCouponPrice", 0);
                int temp = discount;
                if (isUseCoupon)
                    discount = discount - usedCouponPrice;
                double now = Double.parseDouble(totalPriceTv.getText().toString().substring(1));
                totalPriceTv.setText(String.format("￥%s", now - discount));
                isUseCoupon = true;
                usedCouponPrice = temp;
                break;
        }
    }
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
    private void updateStatusLoadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(
                            ORDER_DELETE_OR_UPDATE + newOrderId + "/status/" + 2);
                    Log.d("ddd",json);
                    JsonParser.updateOrders(json);
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();

    }
}

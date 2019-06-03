package com.pro516.thrifttogether.ui.mine.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.OrderDetailsVO;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.mine.alipay.AuthResult;
import com.pro516.thrifttogether.ui.mine.alipay.PayResult;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.util.OrderInfoUtil2_0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.APPID;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.PERMISSIONS_REQUEST_CODE;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.RSA2_PRIVATE;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.RSA_PRIVATE;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.SDK_AUTH_FLAG;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.SDK_PAY_FLAG;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.showAlert;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.showToast;
import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.ORDER_DELETE_OR_UPDATE;
import static com.pro516.thrifttogether.ui.network.Url.ORDER_DETAILS;

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_order_details;
    }

    private Intent intent;
    private OrderDetailsVO mData;
    private TextView orderStatus;
    private Button button;
    private String orderNO;
    ArrayList<String> btn;
    ArrayList<String> change;

    @Override
    protected void init() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("订单详情");
        intent = getIntent();
        orderNO = intent.getStringExtra("orderID");
        loadData();
    }

    private void initView(OrderDetailsVO m) {
        button = findViewById(R.id.mine_order_details_btn);
        button.setOnClickListener(this);
        orderStatus = findViewById(R.id.mine_order_details_state);
        TextView orderTitle = findViewById(R.id.mine_order_details_order_title);
        ImageView orderImg = findViewById(R.id.mine_order_details_order_img);
        TextView orderContent = findViewById(R.id.mine_order_details_order_content);
        TextView orderContentCount = findViewById(R.id.mine_order_details_order_content_count);
        TextView orderPrice = findViewById(R.id.mine_order_details_order_price);
        TextView orderVoucherPrice = findViewById(R.id.mine_order_details_order_voucher_price);
        TextView orderTotalPrice = findViewById(R.id.mine_order_details_order_total_price);
        TextView orderNO = findViewById(R.id.mine_order_details_order_no);
        TextView orderCreateTime = findViewById(R.id.mine_order_details_order_create_time);
        TextView orderPayTime = findViewById(R.id.mine_order_details_order_pay_time);
        RelativeLayout relativeLayout=findViewById(R.id.mine_order_details_order_pay);
        change = new ArrayList<>();
        change.add("待付款");
        change.add("待使用");
        change.add("待评价");
        change.add("已完成");
        change.add("退款售后");
        Log.d("test", "initView: " + m.getOrderStatus());
        orderStatus.setText(change.get(m.getOrderStatus() - 1));

        btn = new ArrayList<>();
        btn.add("去付款");
        btn.add("去使用");
        btn.add("去评价");
        btn.add("申请售后");
        btn.add("退款售后中");
        button.setText(btn.get(m.getOrderStatus() - 1));

        orderTitle.setText(m.getShopName());
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(this).load(m.getProductCoverUrl()).apply(options).into(orderImg);
        orderContent.setText(m.getProductName());//TODO
        orderContentCount.setText("x" + m.getProductCount());
        orderPrice.setText("￥" + m.getProductBuyPrice() * m.getProductCount());
        if (m.getIsUseCoupon() == 1) {
            orderVoucherPrice.setText("￥" + m.getDiscountedPrice());
        }
        orderTotalPrice.setText("合计 ￥" + m.getProductAmountTotal());
        orderNO.setText("" + m.getOrderNo());
        orderCreateTime.setText(m.getCreateTime());
        if (m.getOrderStatus() == 2) {
            relativeLayout.setVisibility(View.VISIBLE);
            orderPayTime.setText(m.getPayTime());
        }
    }

    private void updateStatusLoadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(
                            ORDER_DELETE_OR_UPDATE + mData.getOrderNo() + "/status/" + 2);
                    Log.d("ddd", json);
                    JsonParser.updateOrders(json);
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(ORDER_DETAILS + orderNO);
                    mData = JsonParser.OrdersDetails(json);
                    System.out.println("---------------------------->" + mData);
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
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
                        updateStatusLoadData();
                        mData.setOrderStatus(2);
                        orderStatus.setText(change.get(mData.getOrderStatus() - 1));
                        button.setText(btn.get(mData.getOrderStatus() - 1));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(OrderDetailsActivity.this, getString(R.string.pay_failed) + payResult);
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
                        showAlert(OrderDetailsActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(OrderDetailsActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                case LOAD_ALL:
                    initView((OrderDetailsVO) msg.obj);
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {

                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
                    showToast(OrderDetailsActivity.this, getString(R.string.permission_rejected));
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        showToast(OrderDetailsActivity.this, getString(R.string.permission_rejected));
                        return;
                    }
                }

                // 所需的权限均正常获取
                showToast(OrderDetailsActivity.this, getString(R.string.permission_granted));
            }
        }
    }


    /**
     * 支付宝支付业务示例
     */
    public void payV2(OrderDetailsVO mData) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(OrderDetailsActivity.this, getString(R.string.error_missing_appid_rsa_private));
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
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, mData);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderDetailsActivity.this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.mine_order_details_btn:
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mData);
                switch (mData.getOrderStatus()) {
                    case 1:
                        payV2(mData);
                        break;
                    case 2:
                        intent = new Intent(this, UseActivity.class);
                        intent.putExtra("orderID", mData.getOrderNo());
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(this, OrderCommentActivity.class);
                        intent.putExtra("orderID", mData.getOrderNo());
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(this, AfterSaleActivity.class);
                        intent.putExtra("orderID", mData.getOrderNo());
                        startActivity(intent);
                        break;
                    case 5:
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }
    }
}

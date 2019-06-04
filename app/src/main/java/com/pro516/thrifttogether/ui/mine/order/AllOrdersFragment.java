package com.pro516.thrifttogether.ui.mine.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.OrderBean;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.mine.adapter.OrderAdapter;
import com.pro516.thrifttogether.ui.mine.alipay.AuthResult;
import com.pro516.thrifttogether.ui.mine.alipay.PayResult;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;
import com.pro516.thrifttogether.util.OrderInfoUtil2_0;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;
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
import static com.pro516.thrifttogether.ui.network.Url.ORDER_GET;
import static com.pro516.thrifttogether.ui.network.Url.userID;

public class AllOrdersFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<OrderBean> mData;
    private int position;

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void init(View view) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        mRecyclerView = view.findViewById(R.id.mine_order_list);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        loadData();
        initRefreshLayout(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
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
                        Log.d(TAG, "handleMessage: position: " + position);
                        updateStatusLoadData();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(getActivity(), getString(R.string.pay_failed) + payResult);
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
                        showAlert(getActivity(), getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(getActivity(), getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                case ERROR:
                    Toast.makeText(getActivity(), getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    break;
                case LOAD_ALL:
                    initRecyclerView((List<OrderBean>) msg.obj);
                    if (mSwipeRefresh.isRefreshing()) {
                        mSwipeRefresh.setRefreshing(false);
                    }
                    mProgressBar.setVisibility(View.GONE);
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
                    showToast(getActivity(), getString(R.string.permission_rejected));
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        showToast(getActivity(), getString(R.string.permission_rejected));
                        return;
                    }
                }

                // 所需的权限均正常获取
                showToast(getActivity(), getString(R.string.permission_granted));
            }
        }
    }


    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v, List<OrderBean> mData, int pos) {
        position = pos;
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(getActivity(), getString(R.string.error_missing_appid_rsa_private));
            return;
        }
        OrderBean data=mData.get(pos);
        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, data);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
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


    private void initRecyclerView(List<OrderBean> mData) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        OrderAdapter mAdapter = new OrderAdapter(R.layout.item_mine_order, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Log.d(TAG, "onItemChildClick: ");
            Toast.makeText(getActivity(), "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
            Intent intent;
            switch (mData.get(position).getOrderStatus()) {
                case 1:
                    payV2(view, mData, position);
                    break;
                case 2:
                    intent = new Intent(getActivity(), UseActivity.class);
                    intent.putExtra("orderID",mData.get(position).getOrderNo());
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(getActivity(), OrderCommentActivity.class);
                    intent.putExtra("orderID",mData.get(position).getOrderNo());
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(getActivity(), OrderDetailsActivity.class);
                    intent.putExtra("orderID",mData.get(position).getOrderNo());
                    startActivity(intent);
                    break;
                case 5:
                    break;
                default:
                    break;
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Log.d("团节", "onItemClick: ");
            Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
            intent.putExtra("orderID",mData.get(position).getOrderNo());
            startActivity(intent);
        });

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            Log.d("团节", "onItemLongClick: ");
            Toast.makeText(getActivity(), "productID" + mData.get(position).getOrderNo(), Toast.LENGTH_SHORT).show();
            if (mData.get(position).getOrderStatus() == 4) {
                deleteConfirmationDialog(position, ORDER_DELETE_OR_UPDATE + mData.get(position).getOrderNo(), getActivity());
            }
            return false;
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    public void deleteConfirmationDialog(int position, String url, Context context) {
        MaterialDialog.Builder mBuilder = new MaterialDialog.Builder(context);
        mBuilder.content("确认删除吗？");
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
                Toast.makeText(context, "进行删除操作: pos " + position, Toast.LENGTH_LONG).show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            HttpUtils.doDelete(url);
                            loadData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                mMaterialDialog.dismiss();
            } else if (which == DialogAction.NEGATIVE) {
                mMaterialDialog.dismiss();
            }
        });
    }

    private void updateStatusLoadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(
                            ORDER_DELETE_OR_UPDATE + mData.get(position).getOrderNo() + "/status/" + 2);
                    JsonParser.updateOrders(json);
                    loadData();
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
                    String json = HttpUtils.getStringFromServer(ORDER_GET + userID);
                    mData = JsonParser.Orders(json);
                    System.out.println("---------------------------->" + mData);
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initRefreshLayout(View view) {
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(() -> {
            loadData();
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine_order_all_orders;
    }
}

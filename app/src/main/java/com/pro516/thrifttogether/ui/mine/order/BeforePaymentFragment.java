package com.pro516.thrifttogether.ui.mine.order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.OrderBean;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.mine.adapter.OrderAdapter;
import com.pro516.thrifttogether.ui.mine.alipay.AuthResult;
import com.pro516.thrifttogether.ui.mine.alipay.H5PayDemoActivity;
import com.pro516.thrifttogether.ui.mine.alipay.PayResult;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;
import com.pro516.thrifttogether.util.OrderInfoUtil2_0;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;
import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.ORDER;
import static com.pro516.thrifttogether.ui.network.Url.userID;

public class BeforePaymentFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    private RecyclerView mRecyclerView;

    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<OrderBean> mData;
    private int position;
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016092900627695";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088102177878788";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";

    /**
     * pkcs8 格式的商户私钥。
     * <p>
     * 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * RSA2_PRIVATE。
     * <p>
     * 建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCh3+Zs12HJst1zqLdtUqBsXC9NVZ7fC8750IytAjP/cZewpvKxdnLkA1S2M6pE7UMbG2Hm1uASqe1VacRbAc87Eb7Xj7hnC6ANPOvglBl/N5kUsvIfyJf0Kv90u12QjyHO3/sQZDhRiBEtawYmrW840qLM7ipcCrjYTbfR385U/npPFD4vu1QDx/uc7mWqI40EHZiikaUJG4JO8YhfieiDnRLJBsM94tS9W4u58D6/wqaXDyaj+dZk3dT0pd5vMxbEl4FNmGDMxP6eK83u3WrtNMt2JlzZw1pSWgzWxfqsKJcWjOXJvpZiKBer0tnIKdvbUqw0g8YhHTNYZWyAyNkrAgMBAAECggEAWcVq66eSsqIAUBkcCIPPNej1zqSSI85PrZkruvLC1YIwhqY/Z41x7+OtAhKq9ejC03CfHb7Cq3cTiM/MNlBNlcukk7U/pd37l/vWMEjRzwyNV75Zqqi7JI0H+LvECtxl+m2atZ9u4EBejlqRIbgDO3cNFCts0HidxE9thvXgXp+UbaDxk8D99uyGahCaZ3Fy2L3nY0vcQBNa3/AbOvc1MoPRzfylVWqQaw0tvtxpW3Q8jCUoAbjNy1KYZ9CqjuuO8kWP6qjA6f8HhWqncuf22Iu57gxX3SNdTGkX+LWE6a/8YSNeploChLmTXVrSjsR3qBc12NTLvHjk9aZ0Xg0xGQKBgQDPmz1+lIPJa1oPpozlBIlA2CvQmiyxfMbz2HAib5fANRxEdqhrC2xSq+c1Q1nPyHDQL7WMcR/PsF5VzJ36q6YFFkI9K0Rld3QiyXvAS2GNKUQ+xnEuC8+4A6oxoJtfohA6PE7X3xnzJHj6EGVP3huh1xqTxIIOi36FuTIk7StllQKBgQDHm6YDJH/8pWoS0y1Bu6hFIG/cb8zBmGfkPOj1jlUed/ub6XjP6O2osOV825BPn3tPbm5RB81tt4uQd3fYYmI1qwFBdLn9ho0T837G8esoNKlbP7ST5Bhe0votgrEZVkIotTHdBZSNDGRnSKZqn1Y3ZG+OqgqhXfTwMbnBjeYTvwKBgGtJQoJDpcdVXvlvjKhdq8CC9kGbKzWd0gL5+xkQqk9+ItmOqIKJKLWgeJ+h6qviXcp4nU2yuEnQTy/vykRcqDNAyYJq9bGqBa5kiTtauVMF3GrEioDNQc9KtY1n/mNxkQlHWUdd2D86vfoctv6LPaRpUSbECqIRnJTmm+9RUXfxAoGBAJRP01csbKiVY65fGJZpz8qlIliA/XuvOSp+E3445ogAEIS1Qh4BbWzVG37dSWnQDvhjDjbI/FuUcG/ERNkTqPNFf2ZaS8PTkMmNN9qsb9Ts5BU3zX3BklYKnvbnz50K2ZeTolweRFVVKtkPQw+gGpiH2NgBAmsJ++/okqP9QkHTAoGASlHKXYQpJ0uGz/pyLbeBTeDhAXC6G7+xU1WInBTxUfpsuE7xpBgPjX2b+yR/+r9orJ3vOIn3qIc2zhx0beqeFKMyxHmkB9LFlSpQ2m4HzzrrH5zdJR0sZuVY+BVFT5odJugsmQFT7Kv4FboaYvb6tnevCXjm+UJIARY+KWYCc58=";
    public static final String RSA_PRIVATE = "";

    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_AUTH_FLAG = 2;

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
                        Log.d(TAG, "handleMessage: position: "+position);
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
     * 获取权限使用的 RequestCode
     */
    public static final int PERMISSIONS_REQUEST_CODE = 1002;

    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSIONS_REQUEST_CODE);

        } else {
            showToast(getActivity(), getString(R.string.permission_already_granted));
        }
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
        position=pos;
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(getActivity(), getString(R.string.error_missing_appid_rsa_private));
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

    /**
     * 支付宝账户授权业务示例
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            showAlert(getActivity(), getString(R.string.error_auth_missing_partner_appid_rsa_private_target_id));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(getActivity());
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * 获取支付宝 SDK 版本号。
     */
    public void showSdkVersion(View v) {
        PayTask payTask = new PayTask(getActivity());
        String version = payTask.getVersion();
        showAlert(getActivity(), getString(R.string.alipay_sdk_version_is) + version);
    }

    /**
     * 将 H5 网页版支付转换成支付宝 App 支付的示例
     */
    public void h5Pay(View v) {
        WebView.setWebContentsDebuggingEnabled(true);
        Intent intent = new Intent(getActivity(), H5PayDemoActivity.class);
        Bundle extras = new Bundle();

        /*
         * URL 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
         *
         * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
         * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
         * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
         * 进行测试。
         *
         * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
         * 可以参考它实现自定义的 URL 拦截逻辑。
         */
        String url = "https://m.taobao.com";
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

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

    private void initRecyclerView(List<OrderBean> mData) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        OrderAdapter mAdapter = new OrderAdapter(R.layout.item_mine_order, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("团节", "onItemClick: ");
                Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
                payV2(view, mData, position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void updateStatusLoadData(){
        new Thread(){
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(
                            "http://hncboy.natapp1.cc/thrifttogether/order/"+mData.get(position).getOrderNo()+"/status/"+2);
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
                    String json = HttpUtils.getStringFromServer(ORDER + userID + "/status/1");
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
    public void onLoadMoreRequested() {

    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine_order_before_payment;
    }
}

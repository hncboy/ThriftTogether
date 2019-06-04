package com.pro516.thrifttogether.ui.mine.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.OrderDetailsVO;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.mine.alipay.AuthResult;
import com.pro516.thrifttogether.ui.mine.alipay.PayResult;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.io.IOException;
import java.util.Map;

import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.SDK_AUTH_FLAG;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.SDK_PAY_FLAG;
import static com.pro516.thrifttogether.ui.mine.order.BeforePaymentFragment.showAlert;
import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.ORDER_DETAILS;

public class UseActivity extends BaseActivity implements View.OnClickListener {

    private String orderNO;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_mine_order_use;
    }

    @Override
    protected void init() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("使用");

        Intent intent = getIntent();
        orderNO = intent.getStringExtra("orderID");
        loadData();
    }

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(ORDER_DETAILS + orderNO);
                    OrderDetailsVO mData = JsonParser.OrdersDetails(json);
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
                case LOAD_ALL:
                    initView((OrderDetailsVO) msg.obj);
                    break;

                default:
                    break;
            }
        }
    };

    private void initView(OrderDetailsVO orderDetailsVO){
        TextView useTime=findViewById(R.id.use_time);
        useTime.setText(orderDetailsVO.getCreateTime());
        TextView orderNo=findViewById(R.id.order_no);
        orderNo.setText(orderDetailsVO.getOrderNo());
        TextView createTime=findViewById(R.id.create_time);
        createTime.setText(orderDetailsVO.getCreateTime());
        /*
         * contentEtString：字符串内容
         * w：图片的宽
         * h：图片的高
         * logo：不需要logo的话直接传null
         * */
        /*生成的图片*/
        ImageView contentIv = findViewById(R.id.contentIv);
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap bitmap = CodeCreator.createQRCode("" + orderDetailsVO.getOrderNo(), 400, 400, logo);
        contentIv.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            default:
                break;
        }
    }
}

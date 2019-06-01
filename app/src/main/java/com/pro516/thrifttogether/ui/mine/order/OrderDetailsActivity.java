package com.pro516.thrifttogether.ui.mine.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.OrderBean;
import com.pro516.thrifttogether.ui.base.BaseActivity;

import org.w3c.dom.Text;

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_order_details;
    }
    private Intent intent;
    private OrderBean orderBean;
    @Override
    protected void init() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("订单详情");
        Button button = findViewById(R.id.mine_order_details_btn);
        button.setOnClickListener(this);
        TextView orderTitle = findViewById(R.id.mine_order_details_order_title);
        ImageView orderImg = findViewById(R.id.mine_order_details_order_img);
        TextView orderContent = findViewById(R.id.mine_order_details_order_content);
        TextView orderContentCount = findViewById(R.id.mine_order_details_order_content_count);
        TextView orderPrice = findViewById(R.id.mine_order_details_order_price);
        TextView orderVoucherPrice = findViewById(R.id.mine_order_details_order_voucher_price);
        TextView orderTotalPrice = findViewById(R.id.mine_order_details_order_total_price);
        TextView orderNO = findViewById(R.id.mine_order_details_order_no);
        TextView orderCreateTime = findViewById(R.id.mine_order_details_order_create_time);

        intent= getIntent();
        orderBean = (OrderBean) intent.getSerializableExtra("data");
        orderTitle.setText(orderBean.getProductName());

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(this).load(orderBean.getProductCoverUrl()).apply(options).into(orderImg);

        orderContent.setText(orderBean.getProductName());//TODO
        orderContentCount.setText("x"+orderBean.getProductAmountTotal());
        orderPrice.setText(""+orderBean.getProductAmountTotal());
        orderVoucherPrice.setText("");
        orderTotalPrice.setText(""+orderBean.getProductAmountTotal());
        orderNO.setText(orderBean.getOrderNo());
        orderCreateTime.setText(orderBean.getCreateTime());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.mine_order_details_btn:
                intent=new Intent(OrderDetailsActivity.this,AfterSaleActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",orderBean);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}

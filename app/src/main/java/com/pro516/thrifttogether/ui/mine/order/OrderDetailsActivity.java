package com.pro516.thrifttogether.ui.mine.order;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener{

    @Override
    public int getLayoutRes() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void init() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("订单详情");
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

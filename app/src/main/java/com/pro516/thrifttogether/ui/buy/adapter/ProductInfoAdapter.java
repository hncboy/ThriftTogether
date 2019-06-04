package com.pro516.thrifttogether.ui.buy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.buy.entity.VO.ProductDetailsVO;

import java.util.List;

public class ProductInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ProductInfoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.product_detail_tv,item);
    }
}

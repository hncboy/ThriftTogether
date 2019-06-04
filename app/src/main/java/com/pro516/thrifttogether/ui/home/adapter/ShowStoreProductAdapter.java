package com.pro516.thrifttogether.ui.home.adapter;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.home.entity.StoreProduct;
import com.pro516.thrifttogether.ui.home.entity.VO.SimpleProductVO;

import java.util.List;

public class ShowStoreProductAdapter extends BaseQuickAdapter<SimpleProductVO,BaseViewHolder> {
    List<Integer> productId;

    public ShowStoreProductAdapter(int layoutResId, @Nullable List<SimpleProductVO> data,List<Integer> id) {
        super(layoutResId, data);
        productId = id;
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleProductVO item) {
        helper.setText(R.id.store_product_name, item.getProductName())
                .setText(R.id.store_product_price, "￥"+item.getProductPrice().toString())
                .setText(R.id.store_product_time, item.getProductUseTime())
                .setText(R.id.store_product_old_price,"￥"+item.getProductOriginalPrice().toString())
                .setText(R.id.store_product_month_sale,String.format("月销%s件",item.getProductSales()));
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        productId.add(item.getProductId());
        Glide.with(mContext).load(item.getProductCoverUrl()).apply(options).into((ImageView) helper.getView(R.id.store_product_image));
        TextView oldPrice = helper.getView(R.id.store_product_old_price);
        oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );

        Button button = helper.getView(R.id.store_product_buy_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

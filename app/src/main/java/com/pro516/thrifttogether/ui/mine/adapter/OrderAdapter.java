package com.pro516.thrifttogether.ui.mine.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.OrderBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {


    public OrderAdapter(int layoutResId, @Nullable List<OrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        ArrayList<String> change= new ArrayList<>();
        change.add("待付款");
        change.add("待使用");
        change.add("待评价");
        change.add("已完成");
        change.add("退款售后");
        ArrayList<String> btn= new ArrayList<>();
        btn.add("去付款");
        btn.add("去使用");
        btn.add("去评价");
        btn.add("再来一单");
        btn.add("退款售后");
        helper.setText(R.id.mine_order_title_name, item.getProductName())
                .setText(R.id.mine_order_title_state, change.get(item.getOrderStatus()-1))
                .setText(R.id.mine_order_content_time, "下单时间：" + item.getCreateTime())
                .setText(R.id.mine_order_content_price, "价格：￥" + item.getProductAmountTotal())
                .setText(R.id.mine_order_btn, btn.get(item.getOrderStatus()-1));
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(mContext).load(item.getProductCoverUrl()).apply(mRequestOptions).into((ImageView) helper.getView(R.id.mine_order_title_image));
        Glide.with(mContext).load(item.getProductCoverUrl()).apply(options).into((ImageView) helper.getView(R.id.mine_order_content_image));
    }
}

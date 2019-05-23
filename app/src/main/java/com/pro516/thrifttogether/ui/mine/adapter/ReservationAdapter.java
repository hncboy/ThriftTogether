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
import com.pro516.thrifttogether.entity.mine.ReservationBean;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReservationAdapter extends BaseQuickAdapter<ReservationBean, BaseViewHolder> {
    public ReservationAdapter(int layoutResId, @Nullable List<ReservationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReservationBean item) {
        helper.setText(R.id.mine_reservation_shop_name, item.getShopName())
                .setText(R.id.mine_reservation_address, item.getAddress())
                .setText(R.id.mine_reservation_count, "人数：" + item.getCount())
                .setText(R.id.mine_reservation_time,""+new SimpleDateFormat("yyyy-MM-dd").format(item.getTime()));

        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.mine_reservation_shop_iv));
    }
}

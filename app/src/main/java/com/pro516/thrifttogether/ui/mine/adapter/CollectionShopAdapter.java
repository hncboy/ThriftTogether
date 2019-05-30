package com.pro516.thrifttogether.ui.mine.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.CollectedShopVO;

import java.util.List;

public class CollectionShopAdapter extends BaseItemDraggableAdapter<CollectedShopVO, BaseViewHolder> {
    public CollectionShopAdapter(int layoutResId, @Nullable List<CollectedShopVO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectedShopVO item) {
        helper.setText(R.id.shop_name, item.getShopName())
                .setText(R.id.shop_address, item.getShopArea())
                .setText(R.id.shop_price, "人均：￥" + item.getAveragePrice())
                .setText(R.id.shop_score, "评分： "+item.getAverageScore());

        SimpleRatingBar simpleRatingBar = helper.getView(R.id.simpleRatingBar);
        simpleRatingBar.setRating(item.getAverageScore().floatValue());

        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(mContext).load(item.getCoverUrl()).apply(options).into((ImageView) helper.getView(R.id.shop_iv));
    }
}

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
import com.pro516.thrifttogether.entity.mine.NotificationBean;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationAdapter extends BaseQuickAdapter<NotificationBean, BaseViewHolder> {
    public NotificationAdapter(int layoutResId, @Nullable List<NotificationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NotificationBean item) {
        helper.setText(R.id.mine_notification_title, item.getTitle())
                .setText(R.id.mine_notification_content, item.getContent())
                .setText(R.id.mine_notification_time, ""+new SimpleDateFormat("yyyy-MM-dd").format(item.getTime()))
                .setText(R.id.mine_notification_btn, item.getBtnName());
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        //Glide.with(mContext).load(item.getImg()).apply(mRequestOptions).into((ImageView) helper.getView(R.id.mine_notification_image));
        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.mine_notification_image));
    }
}

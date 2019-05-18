package com.pro516.thrifttogether.ui.mall;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mall.MallBean;

import java.util.List;

public class MallAdapter extends BaseQuickAdapter<MallBean, BaseViewHolder> {


    public MallAdapter(int layoutResId, @Nullable List<MallBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallBean item) {
        helper.setText(R.id.coupons_title, item.getName())
                .setText(R.id.coupons_required_integral, ""+item.getRequiredIntegral());

        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.coupons_image));
        //Glide.with(mContext).load(item.getImg()).apply(options).into((ImageView) helper.getView(R.id.voucher_image));
    }
}

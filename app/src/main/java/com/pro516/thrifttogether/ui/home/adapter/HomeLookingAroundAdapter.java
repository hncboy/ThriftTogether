package com.pro516.thrifttogether.ui.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.LookingAroundShopVO;

import java.util.List;

/**
 * Created by hncboy on 2019-04-01.
 * 随便看看卡片的适配器
 */
public class HomeLookingAroundAdapter extends BaseItemDraggableAdapter<LookingAroundShopVO, BaseViewHolder> {
    public HomeLookingAroundAdapter(int layoutResId, @Nullable List<LookingAroundShopVO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LookingAroundShopVO item) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < item.getProductNameList().size(); i++) {
            str.append(item.getProductNameList().get(i));
        }

        helper.setText(R.id.title, item.getShopName())
                .setText(R.id.shop_address, item.getShopArea())
                .setText(R.id.price, "人均：￥" + item.getAveragePrice())
                .setText(R.id.tuan, str.toString());

        SimpleRatingBar simpleRatingBar = helper.getView(R.id.simpleRatingBar);
        simpleRatingBar.setRating(item.getAverageScore().floatValue());

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(mContext).load(item.getShopCoverUrl()).apply(options).into((ImageView) helper.getView(R.id.shop_iv));
    }
}


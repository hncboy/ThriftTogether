package com.pro516.thrifttogether.ui.home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.home.activity.StoreActivity;
import com.pro516.thrifttogether.ui.home.entity.VO.SimpleReviewVO;

import java.util.List;

public class ShowCommentsAdapter extends BaseQuickAdapter<SimpleReviewVO, BaseViewHolder> {

    public ShowCommentsAdapter(Context context, int layoutResId, @Nullable List<SimpleReviewVO> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleReviewVO item) {
        helper.setText(R.id.store_evaluation_content, item.getReviewContent())
                .setText(R.id.store_evaluation_time, item.getReviewTime().toString())
                .setText(R.id.store_evaluation_user_name, item.getUsername());
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        Log.i("Constraints", "init view");
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(100);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(100, 100);

        Glide.with(mContext).load(item.getAvatorUrl()).apply(options).into((ImageView) helper.getView(R.id.store_evaluation_user_img));
        SimpleRatingBar bar = helper.getView(R.id.store_evaluation_rating);
        bar.setRating(item.getReviewScore().floatValue());
        RecyclerView recyclerView = helper.getView(R.id.store_evaluation_content_img);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        StoreCommentsImageAdapter adapter = new StoreCommentsImageAdapter(mContext, R.layout.item_store_comments_img, item.getReviewPicUrlList());
        recyclerView.setAdapter(adapter);
    }
}

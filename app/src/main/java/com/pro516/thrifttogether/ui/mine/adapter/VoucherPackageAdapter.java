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
import com.pro516.thrifttogether.entity.mine.UserCoupon;
import com.pro516.thrifttogether.entity.mine.VoucherPackageBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class VoucherPackageAdapter extends BaseQuickAdapter<UserCoupon, BaseViewHolder> {

    public VoucherPackageAdapter(int layoutResId, @Nullable List<UserCoupon> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserCoupon item) {

        ArrayList<String> status=new ArrayList<>();
        status.add("已使用");
        status.add("未使用");
        status.add("已过期");
        helper.setText(R.id.voucher_package_status, status.get(item.getUserCouponStatus() - 1));
        helper.setText(R.id.voucher_package_name, item.getCouponName());
        helper.setText(R.id.voucher_package_date, "有效期至：" + item.getExpiredDate());
        helper.setText(R.id.voucher_package_price, "￥" + item.getCouponDiscountedPrice());

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(mContext).load(item.getCouponImageUrl()).apply(options).into((ImageView) helper.getView(R.id.voucher_package_image));
    }
}

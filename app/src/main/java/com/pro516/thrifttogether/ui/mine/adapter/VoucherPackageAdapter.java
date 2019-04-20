package com.pro516.thrifttogether.ui.mine.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.mine.bean.VoucherPackageBean;

import java.text.SimpleDateFormat;
import java.util.List;

public class VoucherPackageAdapter extends BaseQuickAdapter<VoucherPackageBean, BaseViewHolder> {

    public VoucherPackageAdapter(int layoutResId, @Nullable List<VoucherPackageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoucherPackageBean item) {
        helper.setText(R.id.voucher_package_name, item.getName());
        helper.setText(R.id.voucher_package_date, "有效期至：" + new SimpleDateFormat("yyyy-MM-dd").format(item.getTime()));
        helper.setText(R.id.voucher_package_price, "￥" + item.getPrice());
        Glide.with(mContext).load("https://img.meituan.net/msmerchant/53016dc6b5bb3d03729e5cb3eea09550401792.jpg@380w_214h_1e_1c").into((ImageView) helper.getView(R.id.voucher_package_image));
    }
}

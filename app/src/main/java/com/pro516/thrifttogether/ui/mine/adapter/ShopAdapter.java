package com.pro516.thrifttogether.ui.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pro516.thrifttogether.R;

public class ShopAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ShopAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView shopName, shopAddress, shopScore, shopPrice;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_shop, null);
            holder = new ViewHolder();
            holder.imageView = view.findViewById(R.id.shop_iv);
            holder.shopName = view.findViewById(R.id.shop_name);
            holder.shopAddress = view.findViewById(R.id.shop_address);
            holder.shopScore = view.findViewById(R.id.shop_score);
            holder.shopPrice = view.findViewById(R.id.shop_price);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.shopName.setText("商家名称");
        holder.shopAddress.setText("商家地址");
        holder.shopScore.setText("评分：5.0");
        holder.shopPrice.setText("人均：￥50");
        Glide.with(mContext).load("https://img.meituan.net/msmerchant/53016dc6b5bb3d03729e5cb3eea09550401792.jpg@380w_214h_1e_1c").into(holder.imageView);
        return view;
    }
}

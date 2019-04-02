package com.pro516.thrifttogether.ui.mine.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pro516.thrifttogether.R;

public class BeforePaymentOrderAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public BeforePaymentOrderAdapter(Context context) {
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
        ImageView imageView;
        TextView orderName, orderTime, orderPrice;
        Button button;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BeforePaymentOrderAdapter.ViewHolder holder = null;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_before_payment, null);
            holder = new BeforePaymentOrderAdapter.ViewHolder();
            holder.imageView = view.findViewById(R.id.before_payment_order_iv);
            holder.orderName = view.findViewById(R.id.before_payment_order_name);
            holder.orderTime = view.findViewById(R.id.before_payment_order_time);
            holder.orderPrice = view.findViewById(R.id.before_payment_order_price);
            holder.button = view.findViewById(R.id.before_payment_order_pay);
            view.setTag(holder);
        } else {
            holder = (BeforePaymentOrderAdapter.ViewHolder) view.getTag();
        }
        holder.orderName.setText("商品名");
        holder.orderTime.setText("下单时间：");
        holder.orderPrice.setText("人均：￥50");
        Glide.with(mContext).load("https://img.meituan.net/msmerchant/53016dc6b5bb3d03729e5cb3eea09550401792.jpg@380w_214h_1e_1c").into(holder.imageView);
        return view;
    }
}

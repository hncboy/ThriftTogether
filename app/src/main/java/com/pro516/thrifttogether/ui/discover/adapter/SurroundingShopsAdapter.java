package com.pro516.thrifttogether.ui.discover.adapter;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.discover.DiscoverFragment;
import com.pro516.thrifttogether.ui.discover.bean.DiscoverShopVO;
import com.pro516.thrifttogether.ui.discover.bean.SurroundingShopsBean;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class SurroundingShopsAdapter extends BaseQuickAdapter<DiscoverShopVO, BaseViewHolder> {
    AMap aMap;
    List<DiscoverFragment.SimpleStoreInfo> storeLocation;

    public SurroundingShopsAdapter(int layoutResId, @Nullable List<DiscoverShopVO> data, AMap aMap, List<DiscoverFragment.SimpleStoreInfo> storeLocation) {
        super(layoutResId, data);
        this.aMap = aMap;
        this.storeLocation = storeLocation;
    }

    public SurroundingShopsAdapter(int layoutResId, @Nullable List<DiscoverShopVO> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, DiscoverShopVO item) {
        helper.setText(R.id.discover_store_name, item.getShopName())
                .setText(R.id.discover_store_address, item.getShopAddress())
                .setText(R.id.discover_store_category, item.getShopArea())
                .setText(R.id.discover_price, "人均￥" + item.getAveragePrice().toString() + "起")
                .setText(R.id.discover_store_distance, "距您" + item.getDistance().toString() + "km");
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        Log.i("Constraints", "init view");
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(mContext).load(item.getShopCoverUrl()).apply(options).into((ImageView) helper.getView(R.id.discover_store_icon));
        SimpleRatingBar bar = helper.getView(R.id.discover_store_rating);
        bar.setRating(item.getAverageScore().floatValue());
        TextView textView = helper.getView(R.id.discover_store_distance);
        if (item.getDistance() < 1) {
            textView.setText("距您" + item.getDistance()*100 + "m");
        } else {
            textView.setText("距您" + item.getDistance() + "km");
        }
        LatLng latLng = new LatLng(item.getLatitude(), item.getLongitude());
        aMap.addMarker(new MarkerOptions().position(latLng).title(item.getShopName()).snippet("DefaultMarker"));
        DiscoverFragment.SimpleStoreInfo storeInfo = new DiscoverFragment.SimpleStoreInfo(latLng, item.getShopId());
        storeLocation.add(storeInfo);
    }

    public void addData(@NonNull DiscoverShopVO data, List<DiscoverFragment.SimpleStoreInfo> storeLocation) {
        super.addData(data);
        LatLng latLng = new LatLng(data.getLatitude(), data.getLongitude());
        aMap.addMarker(new MarkerOptions().position(latLng).title(data.getShopName()).snippet("DefaultMarker"));
        DiscoverFragment.SimpleStoreInfo storeInfo = new DiscoverFragment.SimpleStoreInfo(latLng, data.getShopId());
        storeLocation.add(storeInfo);
    }
}

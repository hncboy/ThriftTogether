package com.pro516.thrifttogether.ui.discover;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.discover.adapter.SurroundingShopsApapter;
import com.pro516.thrifttogether.ui.discover.bean.SurroundingShopsBean;
import com.pro516.thrifttogether.ui.discover.util.GPSUtils;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by hncboy on 2019-03-19.
 */
public class DiscoverFragment extends BaseFragment implements PoiSearch.OnPoiSearchListener {

    private List<HashMap<String, Object>> data;
    private MapView mMapView = null;
    private AMap aMap = null;
    private View discoverLayout;
    private String[] key = {"icon", "name", "pos", "address", "price", "score"};
    private int flag = 1;
    List<SurroundingShopsBean> mData;

    private static final int LOCATION_CODE = 1;
    private LocationManager locationManager;//【位置管理】

    public int checkPermission() {
        if (GPSUtils.getInstance(getContext()).isLocationProviderEnabled()) {//开了定位服务
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("BRG", "没有权限");
                // 没有权限，申请权限。
                // 申请授权。
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
                Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();
                return 1;
            } else {
                // 有权限了，去放肆吧。
                toast("有权限");
                return 1;
            }
        } else {
            Log.e("BRG", "系统检测到未开启GPS定位服务");
            toast("系统检测到未开启GPS定位服务");
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
            return 0;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。

                } else {
                    // 权限被用户拒绝了。
                    Toast.makeText(getActivity(), "定位权限被禁止，相关地图功能无法使用！", Toast.LENGTH_LONG).show();
                    flag = 0;
                }

            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //checkPermission();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        discoverLayout = inflater.inflate(getLayoutRes(), null);
        mMapView = discoverLayout.findViewById(R.id.discover_map);
        //initData();
        initMap(savedInstanceState);
        initRecyclerView(discoverLayout.findViewById(R.id.discover_store_list));
        return discoverLayout;
    }

    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.discover_store_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        SurroundingShopsApapter mAdapter = new SurroundingShopsApapter(R.layout.item_discover_surrounding_shops, mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "点击：" + position, Toast.LENGTH_SHORT).show();
                //startActivity(OrderDetailsActivity.class);
            }
        });
    }

    private void initMap(@Nullable Bundle savedInstanceState) {
        //获取权限为前提
        if (checkPermission() == 1 || flag == 1) {
            //map init
            Log.i("BRG", "init map start");

            mMapView.onCreate(savedInstanceState);
            aMap = mMapView.getMap();
            MyLocationStyle myLocationStyle;
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.interval(30000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            myLocationStyle.showMyLocation(true);
            myLocationStyle.strokeColor(0);
            myLocationStyle.radiusFillColor(0);
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            PoiSearch.Query query = new PoiSearch.Query("", "餐饮服务|购物服务|生活服务");
            //keyWord表示搜索字符串，
            //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
            //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
            query.setPageSize(100);// 设置每页最多返回多少条poiitem
            query.setPageNum(3);//设置查询页码

            PoiSearch poiSearch = new PoiSearch(getContext(), query);
            Location location = GPSUtils.getInstance(getContext()).getLocation();
            if (location != null) {
                Log.i(TAG, location.getLatitude() + " " + location.getLongitude());
                poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(location.getLatitude(), location.getLongitude()), 500));//设置周边搜索的中心点以及半径
                poiSearch.setOnPoiSearchListener(this);
                poiSearch.searchPOIAsyn();
            }
        }
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SurroundingShopsBean item = new SurroundingShopsBean(
                    "https://img.meituan.net/msmerchant/53016dc6b5bb3d03729e5cb3eea09550401792.jpg@380w_214h_1e_1c",
                    "店名" + i,
                    "宁财院 | 火锅",
                    "浙江宁波海曙区学院路",
                    "人均￥" + (int) (Math.random() * 100) + "元"
            );
            mData.add(item);
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.i(TAG, "Call back analysis result" + poiResult.getPois().size()+"   code:"+i);
        for (PoiItem item : poiResult.getPois()) {
            Log.i(TAG, "onPoiSearched: " + item.getLatLonPoint().getLongitude() + item.getLatLonPoint().getLatitude());
            LatLng latLng = new LatLng(item.getLatLonPoint().getLatitude(), item.getLatLonPoint().getLongitude());
            final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
            SurroundingShopsBean bean = new SurroundingShopsBean(
                    "https://img.meituan.net/msmerchant/53016dc6b5bb3d03729e5cb3eea09550401792.jpg@380w_214h_1e_1c",
                    item.getTitle(),
                    item.getTypeDes(),
                    item.getSnippet(),
                    "人均￥" + (int) (Math.random() * 100) + "元"
            );
            mData.add(bean);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    protected void init(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}

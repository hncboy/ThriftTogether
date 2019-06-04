package com.pro516.thrifttogether.ui.discover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.Photo;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.ShopBean;
import com.pro516.thrifttogether.ui.buy.activity.ProductInfoActivity;
import com.pro516.thrifttogether.ui.discover.bean.DiscoverShopVO;
import com.pro516.thrifttogether.ui.home.activity.StoreActivity;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;
import com.pro516.thrifttogether.util.GPSUtils;
import com.pro516.thrifttogether.util.MapUtil;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.discover.adapter.SurroundingShopsAdapter;
import com.pro516.thrifttogether.ui.discover.bean.SurroundingShopsBean;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by hncboy on 2019-03-19.
 */
public class DiscoverFragment extends BaseFragment implements AMap.InfoWindowAdapter, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener, AMap.OnInfoWindowClickListener {

    private MapView mMapView = null;
    private AMap aMap = null;
    private View discoverLayout;
    private SurroundingShopsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<SimpleStoreInfo> storeLocation;
    private double latx, laty;
    private String mAddress;
    private Button guideButton;
    private ProgressBar mProgressBar;
    private static final int ERROR = -666;
    private static final int LOAD_ALL = 1;
    private static final int LOAD_MORE = 2;
    private static final int LOAD_MORE_ERROR = 3;
    private SwipeRefreshLayout mSwipeRefresh;
    private int size = 3, page = 0;
    private Location location;
    private int pos;

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getActivity(),StoreActivity.class);
        intent.putExtra("storeId",storeLocation.get(pos).getId());
        startActivity(intent);
    }

    public static class SimpleStoreInfo{
        private LatLng latLng;
        private int id;

        public SimpleStoreInfo(LatLng latLng, int id) {
            this.latLng = latLng;
            this.id = id;
        }

        public LatLng getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(getActivity(), getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    break;
                case LOAD_ALL:
                    //initListView((List<ShopBean>) msg.obj);
                    initRecyclerView((List<DiscoverShopVO>) msg.obj);
                    if (mSwipeRefresh.isRefreshing()) {
                        mSwipeRefresh.setRefreshing(false);
                    }
                    mProgressBar.setVisibility(View.GONE);
                    break;
                case LOAD_MORE:
                    loadMoreStore((List<DiscoverShopVO>) msg.obj);
                    mAdapter.loadMoreComplete();
                    break;
                case LOAD_MORE_ERROR:
                    mAdapter.loadMoreFail();
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_discover;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        discoverLayout = inflater.inflate(getLayoutRes(), null);
        mMapView = discoverLayout.findViewById(R.id.discover_map);
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        init(discoverLayout);
        return discoverLayout;
    }

    @Override
    protected void init(View view) {
        initView();
        initRefreshLayout(view);
        Log.i("aaa", "loaddata");
        loadData();
        initMap();

    }

    private void initView() {
        mMapView = discoverLayout.findViewById(R.id.discover_map);
        mRecyclerView = discoverLayout.findViewById(R.id.discover_store_list);
        mProgressBar = discoverLayout.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //location = GPSUtils.getInstance(getContext()).getLocation();
                    location = aMap.getMyLocation();
                    if (location==null)
                        //location = new Location(29.88600455,121.47420162);
                    Log.i(TAG, location.getLatitude() + " " + location.getLongitude());
                    String json = HttpUtils.getStringFromServer(Url.SHOP + "/city/1/lat/" + location.getLatitude() + "/lng/" + location.getLongitude() + "?size=" + size);
                    List<DiscoverShopVO> mData = JsonParser.surroundingShop(json);
                    Log.i(TAG, "load all store");
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (Exception e) {
                    Log.i(TAG, "fail load all store");
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initRecyclerView(List<DiscoverShopVO> mData) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        storeLocation = new ArrayList<>();
        mAdapter = new SurroundingShopsAdapter(R.layout.item_discover_surrounding_shops, mData, aMap, storeLocation);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(true);   // 是否第一次才加载动画
        //item点击事件
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "点击：" + position, Toast.LENGTH_SHORT).show();

                double storeLatitude = storeLocation.get(position).getLatLng().latitude;
                double storeLongitude = storeLocation.get(position).getLatLng().longitude;
                latx = storeLatitude;
                laty = storeLongitude;
                pos = position;
                guideButton = view.findViewById(R.id.discover_guide);
                //导航按钮点击事件
                guideButton.setOnClickListener(DiscoverFragment.this);
                Log.i(TAG, storeLatitude + " " + storeLongitude);
                TextView titleView = view.findViewById(R.id.discover_store_name);
                String storeName = titleView.getText().toString();
                TextView distanceView = view.findViewById(R.id.discover_store_distance);
                String storeDistance = distanceView.getText().toString().trim();
                TextView addressView = view.findViewById(R.id.discover_store_address);
                mAddress = addressView.getText().toString().trim();
                //标记商家
                LatLng latLng = new LatLng(storeLatitude, storeLongitude);
                final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(storeName).snippet(storeDistance));
                //移动镜头
                CameraPosition cameraPosition = new CameraPosition(latLng, 20, 0, 0);
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                marker.showInfoWindow();

            }
        });
    }

    private void initMap() {
        //map init
        Log.i("BRG", "init map start");
        //获取用户位置
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(30000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        myLocationStyle.strokeColor(0);
        myLocationStyle.radiusFillColor(0);
        UiSettings settings = aMap.getUiSettings();
        settings.setZoomControlsEnabled(false);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(30));
        aMap.setInfoWindowAdapter(DiscoverFragment.this);
        aMap.setOnInfoWindowClickListener(this);
    }

    private void initRefreshLayout(View view) {
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(this::loadData);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discover_guide:
                showListDialog();
                break;
            case R.id.info_window:

                break;
        }
    }

    private void getMoreStore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    page = page + 1;
                    location = GPSUtils.getInstance(getContext()).getLocation();
                    Log.i(TAG, location.getLatitude() + " " + location.getLongitude());
                    Log.i(TAG, "request url " + Url.SHOP + "/city/1/lat/" + location.getLatitude() + "/lng/" + location.getLongitude() +
                            "?size=" + size + "&page=" + page);
                    String json = HttpUtils.getStringFromServer(
                            Url.SHOP + "/city/1/lat/" + location.getLatitude() + "/lng/" + location.getLongitude() +
                                    "?size=" + size + "&page=" + page);

                    List<DiscoverShopVO> mData = JsonParser.surroundingShop(json);
                    Log.i(TAG, "load all store");
                    mHandler.obtainMessage(LOAD_MORE, mData).sendToTarget();
                } catch (Exception e) {
                    Log.i(TAG, "fail load more store ");
                    mHandler.obtainMessage(LOAD_MORE_ERROR).sendToTarget();
                    e.printStackTrace();

                }
            }
        }).start();
    }

    public void loadMoreStore(List<DiscoverShopVO> mData) {
        for (DiscoverShopVO item : mData) {
            mAdapter.addData(item, storeLocation);
        }
        mAdapter.notifyDataSetChanged();
    }

    //自定义infowindow
    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = null;
        if (infoWindow == null) {
            infoWindow = LayoutInflater.from(getContext()).inflate(
                    R.layout.layout_discover_storeinfo_window, null);
        }
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        String title = marker.getTitle();
        TextView titleUi = view.findViewById(R.id.infoTitle);
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_dark_color)), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(16);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = view.findViewById(R.id.snippet);
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.theme_text)), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(12);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
    }

    private void showListDialog() {
        final String[] items = {"高德地图", "百度地图", "腾讯地图"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(getActivity());
        listDialog.setTitle("选择第三方导航软件");
        listDialog.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    if (MapUtil.isGdMapInstalled()) {
                        MapUtil.openGaoDeNavi(getActivity(), 0, 0, null, latx, laty, mAddress);
                    } else {
                        //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                        Toast.makeText(getActivity(), "尚未安装高德地图", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    if (MapUtil.isBaiduMapInstalled()) {
                        MapUtil.openBaiDuNavi(getActivity(), 0, 0, null, latx, laty, mAddress);
                    } else {
                        Toast.makeText(getActivity(), "尚未安装百度地图", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if (MapUtil.isTencentMapInstalled()) {
                        MapUtil.openTencentMap(getActivity(), 0, 0, null, latx, laty, mAddress);
                    } else {
                        Toast.makeText(getActivity(), "尚未安装腾讯地图", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        });
        listDialog.show();
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
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

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMoreStore();
            }
        }, 1000);
    }
}

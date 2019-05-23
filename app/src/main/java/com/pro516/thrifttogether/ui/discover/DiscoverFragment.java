package com.pro516.thrifttogether.ui.discover;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
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
import com.pro516.thrifttogether.util.GPSUtils;
import com.pro516.thrifttogether.util.MapUtil;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.discover.adapter.SurroundingShopsAdapter;
import com.pro516.thrifttogether.ui.discover.bean.SurroundingShopsBean;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by hncboy on 2019-03-19.
 */
public class DiscoverFragment extends BaseFragment implements AMap.InfoWindowAdapter, PoiSearch.OnPoiSearchListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    private List<HashMap<String, Object>> data;
    private MapView mMapView = null;
    private AMap aMap = null;
    private View discoverLayout;
    private SurroundingShopsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private double[][] storeLocation = new double[100][2];
    private int flag = 1;
    private int page_num = 20;
    private double latx, laty;
    private String mAddress;
    private Button guideButton;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        discoverLayout = inflater.inflate(getLayoutRes(), null);
        mMapView = discoverLayout.findViewById(R.id.discover_map);
        mMapView.onCreate(savedInstanceState);

        new Thread(this::initMap).run();
        return discoverLayout;
    }

    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.discover_store_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new SurroundingShopsAdapter(R.layout.item_discover_surrounding_shops, mData);
        //onLoadMoreRequested();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); // 加载动画类型
        mAdapter.isFirstOnly(false);   // 是否第一次才加载动画
        mRecyclerView.setAdapter(mAdapter);
        //item点击事件
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "点击：" + position, Toast.LENGTH_SHORT).show();
                double storeLatitude = storeLocation[position][0];
                double storeLongitude = storeLocation[position][1];
                latx = storeLatitude;
                laty = storeLongitude;
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
        //获取权限为前提
        if (checkPermission() == 1 || flag == 1) {
            //map init
            Log.i("BRG", "init map start");
            aMap = mMapView.getMap();
            //获取用户位置
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
            aMap.moveCamera(CameraUpdateFactory.zoomTo(30));
            aMap.setInfoWindowAdapter(DiscoverFragment.this);
            PoiSearch.Query query = new PoiSearch.Query("", "餐饮服务|购物服务|生活服务");
            //keyWord表示搜索字符串，
            //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
            //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
            query.setPageSize(page_num);// 设置每页最多返回多少条poiitem
            query.setPageNum(1);//设置查询页码
            PoiSearch poiSearch = new PoiSearch(getContext(), query);
            Location location = GPSUtils.getInstance(getContext()).getLocation();
            Log.i(TAG, "AAA");
            if (location != null) {
                Log.i(TAG, location.getLatitude() + " " + location.getLongitude());
                poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(location.getLatitude(), location.getLongitude()), 300));//设置周边搜索的中心点以及半径
                poiSearch.setOnPoiSearchListener(this);
                poiSearch.searchPOIAsyn();//会回调onPoiSearched方法
            } else {
                Log.e(TAG, "get location error，potion is null");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discover_guide:
                showListDialog();
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mAdapter.setOnLoadMoreListener(() -> mRecyclerView.postDelayed(() -> {
            mData.clear();
            page_num += 20;
            initMap();
            mAdapter.notifyDataSetChanged();
        }, 1000), mRecyclerView);
    }
    //poi搜索回调接口,第二个参数为解析结果代码
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.i(TAG, "Call back analysis result" + poiResult.getPois().size() + "   code:" + i);
        this.mData = new ArrayList<>();
        int cnt = 0;
        for (PoiItem item : poiResult.getPois()) {
            Log.i(TAG, "onPoiSearched: " + item.getLatLonPoint().getLongitude() + item.getLatLonPoint().getLatitude());
            List<Photo> photo = item.getPhotos();
            String url = "";
            if (photo.size() > 0)
                url = photo.get(0).getUrl();
            System.out.println("----------------------------->" + url);
            Log.i(TAG, "PHOTO URL:" + url);
            SurroundingShopsBean bean = new SurroundingShopsBean(
                    item.getTitle(),
                    item.getTypeDes().split(";")[2] + " | " + item.getAdName(),
                    item.getSnippet(),
                    "人均￥" + (int) (Math.random() * 100) + "元",
                    url,
                    "  距离您" + item.getDistance() + "米  "
            );
            storeLocation[cnt][0] = item.getLatLonPoint().getLatitude();
            storeLocation[cnt][1] = item.getLatLonPoint().getLongitude();
            cnt++;
            this.mData.add(bean);
            Log.i(TAG, "add one item" + item.getTypeDes());
            LatLng latLng = new LatLng(item.getLatLonPoint().getLatitude(), item.getLatLonPoint().getLongitude());
            final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
        }
        initRecyclerView(discoverLayout.findViewById(R.id.discover_layout));
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
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
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
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
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

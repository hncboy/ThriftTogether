package com.pro516.thrifttogether.ui.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.discover.adapter.SurroundingShopsApapter;
import com.pro516.thrifttogether.ui.discover.bean.SurroundingShopsBean;
import com.pro516.thrifttogether.ui.mine.order.OrderDetailsActivity;
import com.pro516.thrifttogether.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hncboy on 2019-03-19.
 */
public class DiscoverFragment extends BaseFragment implements PoiSearch.OnPoiSearchListener {

    private List<HashMap<String, Object>> data;
    private MapView mMapView = null;
    private AMap aMap = null;
    private View discoverLayout;
    private String[] key = {"icon", "name", "pos", "address", "price", "score"};
    List<SurroundingShopsBean> mData;

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
    protected int getLayoutRes() {
        return R.layout.fragment_discover;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        discoverLayout = inflater.inflate(getLayoutRes(), null);
        initData();
        initRecyclerView(discoverLayout.findViewById(R.id.discover_store_list));
        initMap(savedInstanceState);
        return discoverLayout;
    }

    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.mine_order_list);
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
                startActivity(OrderDetailsActivity.class);
            }
        });
    }

    private void initMap(@Nullable Bundle savedInstanceState) {
        //map init
        mMapView = discoverLayout.findViewById(R.id.discover_map);
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        PoiSearch.Query query = new PoiSearch.Query("", "0574");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        //query.setPageNum(currentPage);//设置查询页码
    }

    @Override
    protected void init(View view) {

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

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}

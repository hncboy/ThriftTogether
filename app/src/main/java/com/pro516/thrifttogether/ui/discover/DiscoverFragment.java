package com.pro516.thrifttogether.ui.discover;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by hncboy on 2019-03-19.
 */
public class DiscoverFragment extends BaseFragment {

    private ListView listView;
    private List<HashMap<String, Object>> data;
    private String[] key = {"icon", "name", "pos", "address", "price","score"};

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void init(View view) {
        listView = view.findViewById(R.id.discover_listView);
        initData();
        SimpleAdapter adapter = new SimpleAdapter(
                getActivity(), data, R.layout.item_discover, key,
                new int[]{R.id.discover_store_icon, R.id.discover_store_name, R.id.discover_store_pos,
                        R.id.discover_store_address, R.id.discover_price,R.id.discover_star}
        );
        listView.setAdapter(adapter);
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("name", "店名" + i);
            item.put("pos", "宁波财经学院");
            item.put("address", "浙江宁波海曙区学院路");
            item.put("price", "人均￥" + (int) (Math.random() * 100) + "元");
            item.put("icon", R.drawable.temp_map);
            item.put("score",R.drawable.three_and_half);
            data.add(item);
        }
    }
}

package com.pro516.thrifttogether.ui.discover;

import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            if (i%5 == 0) {
                item.put("name", "名羊天下");
                item.put("pos", "兴宁路");
                item.put("address", "鄞州区兴宁路47-2号");
                item.put("price", "人均￥45");
                item.put("icon", R.drawable.item_test_home1);
                item.put("score",R.drawable.three_and_half);
            } else if (i%5 == 1) {
                item.put("name", "许府牛火锅·祖传牛骨头汤");
                item.put("pos", "培罗成广场");
                item.put("address", "鄞州区鄞县大道培罗成广场裙楼1楼");
                item.put("price", "人均￥64");
                item.put("icon", R.drawable.item_test_home2);
                item.put("score",R.drawable.three_and_half);
            } else if (i%5 == 2) {
                item.put("name", "烤食汇自助烧烤火锅");
                item.put("pos", "东渡路");
                item.put("address", "海曙区东渡路36号（老银泰对面）");
                item.put("price", "人均￥66");
                item.put("icon", R.drawable.item_test_home3);
                item.put("score",R.drawable.three_and_half);
            } else if (i%5 == 3) {
                item.put("name", "尚品宫韩式自助烤肉");
                item.put("pos", "洛兹广场");
                item.put("address", "海曙区雅戈尔大道洛兹广场B座F3室");
                item.put("price", "人均￥46");

                item.put("icon", R.drawable.item_test_home4);
                item.put("score",R.drawable.three_and_half);
            } else if (i%5 == 4) {
                item.put("name", "钱小奴创意自助餐厅");
                item.put("pos", "环球银泰城");
                item.put("address", "鄞州区天童南路环球银泰城5层");
                item.put("price", "人均￥70");
                item.put("icon", R.drawable.item_test_home5);
                item.put("score",R.drawable.three_and_half);
            }

            data.add(item);
        }
    }
}

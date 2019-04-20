package com.pro516.thrifttogether.ui.mine.collection;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.mine.adapter.ShopAdapter;

public class ShopFragment extends BaseFragment implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    @Override
    protected void init(View view) {
        ListView mLv = view.findViewById(R.id.shop_list);
        mLv.setAdapter(new ShopAdapter(getActivity()));

        // 点击事件
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "点击 pos: " + i, Toast.LENGTH_SHORT).show();
            }
        });

        // 长按事件
        mLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "长按 pos: " + i, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine_collection_shops;
    }
}

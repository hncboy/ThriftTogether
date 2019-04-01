package com.pro516.thrifttogether.ui.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pro516.thrifttogether.R;

import java.util.List;

/**
 * Created by hncboy on 2019-04-01.
 * 历史搜索的适配器
 */
public class HomeHistorySearchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeHistorySearchAdapter(List<String> datas) {
        super(R.layout.item_home_search_recording, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.search_recording_tv, item);
    }
}

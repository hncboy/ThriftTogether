package com.pro516.thrifttogether.ui.buy.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.buy.entity.DetailAndNoteEntity;

import java.util.List;

public class SingleProductAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    private static final int ITEM_TITLE = 1;
    private static final int ITEM_CONTENT = 2;

    public SingleProductAdapter(@Nullable List<Object> data) {
        super(data);

        // 第一步：动态判断
        setMultiTypeDelegate(new MultiTypeDelegate<Object>() {
            @Override
            protected int getItemType(Object o) {
                // 当前例子中只有两种类型
                if (o instanceof String) {
                    // 加载布局1
                    return ITEM_TITLE;
                } else if (o instanceof DetailAndNoteEntity.CategoryDetailEntity) {
                    // 加载布局2
                    return ITEM_CONTENT;
                }
                return 0;
            }
        });

        // 第二步：设置type和layout的对应关系
        getMultiTypeDelegate().registerItemType(ITEM_TITLE, R.layout.item_package_detail_category_title)
                .registerItemType(ITEM_CONTENT, R.layout.item_package_detail_category_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        // 第三步：设置不同布局下的组件数据
        switch (helper.getItemViewType()) {
            case ITEM_TITLE:
                // 标题赋值
                helper.setText(R.id.package_details_category_title, (String) item);
                break;
            case ITEM_CONTENT:
                helper.setText(R.id.package_details_category_content_tv1, ((DetailAndNoteEntity.CategoryDetailEntity) item).getName());
                        //.setText(R.id.package_details_category_content_tv2, String.valueOf(((DetailAndNoteEntity.CategoryDetailEntity) item).getNum()));
                break;
        }

    }
}

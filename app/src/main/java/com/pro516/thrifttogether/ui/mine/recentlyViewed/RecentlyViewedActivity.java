package com.pro516.thrifttogether.ui.mine.recentlyViewed;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.mine.adapter.ShopAdapter;

public class RecentlyViewedActivity extends BaseActivity implements View.OnClickListener {
    private ListView mLv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLv1 = findViewById(R.id.shop_list);
        mLv1.setAdapter(new ShopAdapter(RecentlyViewedActivity.this));

        // 点击事件
        mLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(RecentlyViewedActivity.this, "点击 pos: " + i, Toast.LENGTH_SHORT).show();
            }
        });

        // 长按事件
        mLv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(RecentlyViewedActivity.this, "长按 pos: " + i, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_recently_viewed;
    }

    private void setListeners() {

    }

    @Override
    protected void init() {
        setListeners();
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.recently_viewed));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            default:
                break;
        }
    }
}

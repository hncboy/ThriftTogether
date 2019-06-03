package com.pro516.thrifttogether.ui.home.activity.hotel;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.home.activity.HomeSearchActivity;

/**
 * 酒店
 */
public class HomeHotelActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_hotel;
    }

    @Override
    protected void init() {
        initToolbar();
        initTabLayout();
    }

    private void initTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        String[] titles = {"国内", "国际/港澳台", "民宿公寓"};
        for (String title : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        LinearLayout datePickerLayout = findViewById(R.id.date_picker_layout);
        datePickerLayout.setOnClickListener(this);
    }

    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        backImgBtn.setVisibility(View.VISIBLE);
        backImgBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        AppCompatImageButton searchImgBtn = findViewById(R.id.common_toolbar_function_right);
        searchImgBtn.setVisibility(View.VISIBLE);
        searchImgBtn.setImageDrawable(getDrawable(R.drawable.ic_search_white_24dp));
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.hotel));
        backImgBtn.setOnClickListener(this);
        searchImgBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.common_toolbar_function_right:
                startActivity(HomeSearchActivity.class);
                break;
            case R.id.date_picker_layout:
                startActivity(HotelDatePickerActivity.class);
                break;
            default:
                break;
        }
    }
}

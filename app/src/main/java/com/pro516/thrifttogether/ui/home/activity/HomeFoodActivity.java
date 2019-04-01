package com.pro516.thrifttogether.ui.home.activity;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.home.adapter.GirdDropDownAdapter;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFoodActivity extends BaseActivity implements View.OnClickListener {

    private DropDownMenu mDropDownMenu;
    private GirdDropDownAdapter allFoodAdapter;
    private GirdDropDownAdapter nearbyAdapter;
    private GirdDropDownAdapter sortAdapter;
    private GirdDropDownAdapter filterAdapter;
    private List<View> popupViews = new ArrayList<>();

    private String headers[] = {"全部美食", "附近", "智能排序", "筛选"};
    private String allFoods[] = {"不限", "特色菜", "福建菜", "日本菜", "饮品店", "面包甜点", "生日蛋糕"};
    private String nearbys[] = {"附近", "海曙区", "鄞州区", "江北区", "北仑区", "镇海区", "余姚区", "慈溪市", "奉化市", "宁海县", "象山县"};
    private String sortings[] = {"智能排序", "离我最近", "好评优先", "销量最高"};
    private String filters[] = {"不限", "单人餐", "双人餐", "3~4人餐", "5~10人餐"};

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_food;
    }

    @Override
    protected void init() {
        initToolbar();
        initDropMenu();
    }

    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        backImgBtn.setVisibility(View.VISIBLE);
        backImgBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        AppCompatImageButton searchImgBtn = findViewById(R.id.common_toolbar_function_right);
        searchImgBtn.setVisibility(View.VISIBLE);
        searchImgBtn.setImageDrawable(getDrawable(R.drawable.ic_search_white_24dp));
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.food));
        backImgBtn.setOnClickListener(this);
    }

    private void initDropMenu() {
        // 全部美食
        ListView allFoodView = new ListView(this);
        allFoodAdapter = new GirdDropDownAdapter(this, Arrays.asList(allFoods));
        allFoodView.setDividerHeight(0);
        allFoodView.setAdapter(allFoodAdapter);
        allFoodView.setOnItemClickListener((parent, view, position, id) -> {
            allFoodAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[0] : allFoods[position]);
            mDropDownMenu.closeMenu();
        });

        // 附近
        ListView nearbyView = new ListView(this);
        nearbyAdapter = new GirdDropDownAdapter(this, Arrays.asList(nearbys));
        nearbyView.setDividerHeight(0);
        nearbyView.setAdapter(nearbyAdapter);
        nearbyView.setOnItemClickListener((parent, view, position, id) -> {
            nearbyAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[1] : nearbys[position]);
            mDropDownMenu.closeMenu();
        });

        // 智能排序
        ListView sortView = new ListView(this);
        sortAdapter = new GirdDropDownAdapter(this, Arrays.asList(sortings));
        sortView.setDividerHeight(0);
        sortView.setAdapter(sortAdapter);
        sortView.setOnItemClickListener((parent, view, position, id) -> {
            sortAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[2] : sortings[position]);
            mDropDownMenu.closeMenu();
        });

        // 筛选
        ListView filterView = new ListView(this);
        filterAdapter = new GirdDropDownAdapter(this, Arrays.asList(filters));
        filterView.setDividerHeight(0);
        filterView.setAdapter(filterAdapter);
        filterView.setOnItemClickListener((parent, view, position, id) -> {
            filterAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[3] : filters[position]);
            mDropDownMenu.closeMenu();
        });

        popupViews.add(allFoodView);
        popupViews.add(nearbyView);
        popupViews.add(sortView);
        popupViews.add(filterView);

        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        mDropDownMenu = findViewById(R.id.dropDownMenu);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.common_toolbar_function_right:
                toast("搜索");
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
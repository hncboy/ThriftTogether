package com.pro516.thrifttogether.ui.home.activity;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

public class HomeCityPickerActivity extends BaseActivity {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_city_picker;
    }

    @Override
    protected void init() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));

        CityPicker.from(HomeCityPickerActivity.this)
                .enableAnimation(false)
                .setLocatedCity(new LocatedCity("宁波", "浙江", "101210101"))  // APP自身已定位的城市，传null会自动定位（默认）
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {

                    @Override
                    public void onPick(int position, City data) {
                        finish();
                    }

                    @Override
                    public void onCancel(){
                        finish();
                    }

                    @Override
                    public void onLocate() {

                    }
                }).show();
    }
}

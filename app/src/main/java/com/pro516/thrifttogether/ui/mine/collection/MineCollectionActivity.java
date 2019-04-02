package com.pro516.thrifttogether.ui.mine.collection;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MineCollectionActivity extends BaseActivity implements View.OnClickListener {

    //写一个List集合，把每个页面，也就是Fragment,存进去
    private List<Fragment> list;
    private String[] titles = {"商家", "商品"};

    @Override
    public int getLayoutRes() {
        return R.layout.activity_mine_collection;
    }

    private void setListeners() {

    }

    @Override
    public void init() {
        //实例化
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.table_layout);
        //页面，数据源，里面是创建的（Fragment）
        list = new ArrayList<>();
        list.add(new ShopFragment());
        list.add(new GoodsFragment());
        //ViewPager的适配器，获得Fragment管理器
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //将TabLayout和ViewPager绑定在一起，一个动另一个也会跟着动
        tabLayout.setupWithViewPager(viewPager);

        setListeners();
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.my_collection));
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
    //创建Fragment的适配器
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        //获得每个页面的下标
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
        //获得List的大小
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}

package com.pro516.thrifttogether.ui.buy.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.buy.fragment.SingleProductFragment;
import com.pro516.thrifttogether.ui.home.activity.StoreActivity;
import com.pro516.thrifttogether.ui.home.entity.VO.ShopDetailsVO;
import com.pro516.thrifttogether.ui.home.entity.VO.SimpleReviewVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductInfoActivity extends BaseActivity implements View.OnClickListener {
    //写一个List集合，把每个页面，也就是Fragment,存进去
    private List<Fragment> list;
    private List<String> titles;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_product_info;
    }

    @Override
    protected void init() {
        //实例化
        ViewPager viewPager = findViewById(R.id.buy_goods_viewpager);
        TabLayout tabLayout = findViewById(R.id.buy_goods_tab_layout);
        //页面，数据源，里面是创建的（Fragment）
        list = new ArrayList<>();
        titles = new ArrayList<>();
        //TODO 初始化list,titles
        for(int i=0;i<2;i++){
            list.add(new SingleProductFragment());
            titles.add("test"+i);
        }

        //ViewPager的适配器，获得Fragment管理器
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //将TabLayout和ViewPager绑定在一起，一个动另一个也会跟着动
        tabLayout.setupWithViewPager(viewPager);

        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("选择团购");
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    break;
            }
        }
    };

    private void initPage(ShopDetailsVO obj) {

    }

    private void initData(){

    }

    private void initView(){

    }
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            return titles.get(position);
        }
    }
}

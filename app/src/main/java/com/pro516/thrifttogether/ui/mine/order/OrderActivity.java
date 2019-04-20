package com.pro516.thrifttogether.ui.mine.order;

import android.content.Intent;
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

public class OrderActivity extends BaseActivity implements View.OnClickListener {
    //写一个List集合，把每个页面，也就是Fragment,存进去
    private List<Fragment> list;
    private String[] titles = {"全部", "待付款","待使用","待评价","退款售后"};
    @Override
    public int getLayoutRes() {
        return R.layout.activity_order;
    }

    @Override
    protected void init() {
        //实例化
        ViewPager viewPager = findViewById(R.id.mine_order_viewpager);
        TabLayout tabLayout = findViewById(R.id.mine_order_table_layout);
        //页面，数据源，里面是创建的（Fragment）
        list = new ArrayList<>();
        list.add(new AllOrdersFragment());
        list.add(new BeforePaymentFragment());
        list.add(new ToBeUsedFragment());
        list.add(new ToEvaluateFragment());
        list.add(new RefundOrAfterSalesFragment());
        //ViewPager的适配器，获得Fragment管理器
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //将TabLayout和ViewPager绑定在一起，一个动另一个也会跟着动
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("id"));
        viewPager.setCurrentItem(id);

        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("我的订单");

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

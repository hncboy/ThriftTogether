package com.pro516.thrifttogether.ui.buy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.buy.entity.VO.ProductDetailsVO;
import com.pro516.thrifttogether.ui.buy.fragment.SingleProductFragment;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.SHOP;

public class ProductInfoActivity extends BaseActivity implements View.OnClickListener {
    //写一个List集合，把每个页面，也就是Fragment,存进去
    private List<Fragment> list;
    private List<String> titles;
    private List<ProductDetailsVO> data;
    private int storeId, chosenPos,chosenProductId;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressBar mProgressBar;
    private AppCompatImageButton loveBt;
    private final int STAR_SUCCESSFUL = 100;
    private final int CANCEL_STAR_SUCCESSFUL = 200;
    private final int STAR_UNSUCCESSFUL = 300;
    private final int CANCEL_STAR_UNSUCCESSFUL = 400;
    private boolean isStar;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_product_info;
    }

    @Override
    protected void init() {
        Log.i("Constraints", "init product info" + chosenPos);
        list = new ArrayList<>();
        titles = new ArrayList<>();
        Intent intent = getIntent();
        storeId = intent.getIntExtra("storeId", 1);
        chosenProductId = intent.getIntExtra("productId", 1);
        initView();
        initData();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_ALL:
                    initPage((List<ProductDetailsVO>) msg.obj);
                    mProgressBar.setVisibility(View.GONE);
                    break;
                case ERROR:
                    Toast.makeText(ProductInfoActivity.this, getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    break;
                case STAR_SUCCESSFUL:
                    toast("收藏成功");
                    setStarStatus(true);
                    break;
                case CANCEL_STAR_SUCCESSFUL:
                    toast("取消收藏成功");
                    setStarStatus(false);
                    break;
                case STAR_UNSUCCESSFUL:
                    toast("收藏失败");
                    break;
                case CANCEL_STAR_UNSUCCESSFUL:
                    toast("取消收藏失败");
                    break;
                default:
                    break;
            }
        }
    };

    private void initPage(List<ProductDetailsVO> mData) {
        //页面，数据源，里面是创建的（Fragment）
        int i=0;
        for (ProductDetailsVO details : mData) {
            SingleProductFragment singleProductFragment = new SingleProductFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", details);
            singleProductFragment.setArguments(bundle);
            list.add(singleProductFragment);
            titles.add(details.getProductName());
            if (details.getProductId() == chosenProductId){
                chosenPos = i;
            }
            Log.i("aaa", "initPage: fragment "+i+" is "+details.getIsCollected());
            i++;
        }
        data = mData;
        //ViewPager的适配器，获得Fragment管理器
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                isStar = mData.get(i).getIsCollected() == 1;
                chosenPos = i;
                setStarStatus(isStar);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        //将TabLayout和ViewPager绑定在一起，一个动另一个也会跟着动
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(chosenPos);
        isStar = mData.get(chosenPos).getIsCollected() == 1;
        setStarStatus(isStar);
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(SHOP + "/" + storeId + "/user/1/product");
                    List<ProductDetailsVO> mData = JsonParser.storeProductDetails(json);
                    System.out.println("---------------------------->" + mData);
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        //实例化
        viewPager = findViewById(R.id.buy_goods_viewpager);
        tabLayout = findViewById(R.id.buy_goods_tab_layout);
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        loveBt = findViewById(R.id.common_toolbar_function_right_1);
        loveBt.setVisibility(View.VISIBLE);
        loveBt.setOnClickListener(this);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("选择团购");
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
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
            case R.id.common_toolbar_function_right_1:
                Log.i("aaa", "onClick: isStar"+isStar);
                if (isStar) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Log.i("aaa", "run: get item to delete star" + viewPager.getCurrentItem());
                                Response rs = HttpUtils.doDelete(Url.COLLECTION + "/1/product/" + data.get(viewPager.getCurrentItem()).getProductId());
                                boolean mData = JsonParser.starStore(rs.body().string());
                                Log.i("aaa", "run: delete star res " + mData);
                                if (mData)
                                    mHandler.obtainMessage(CANCEL_STAR_SUCCESSFUL).sendToTarget();
                                else
                                    mHandler.obtainMessage(CANCEL_STAR_UNSUCCESSFUL).sendToTarget();
                            } catch (IOException e) {
                                mHandler.obtainMessage(ERROR).sendToTarget();
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Log.i("aaa", "run: get item to star" + viewPager.getCurrentItem());
                                Response rs = HttpUtils.doGet(Url.COLLECTION + "/1/product/" + data.get(viewPager.getCurrentItem()).getProductId());
                                boolean mData = JsonParser.cancelStarStore(rs.body().string());
                                //System.out.println("---------------------------->" + mData);
                                Log.i("aaa", "run:  star res" + mData);
                                if (mData)
                                    mHandler.obtainMessage(STAR_SUCCESSFUL).sendToTarget();
                                else
                                    mHandler.obtainMessage(STAR_UNSUCCESSFUL).sendToTarget();

                            } catch (IOException e) {
                                mHandler.obtainMessage(ERROR).sendToTarget();
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
                break;
            default:
                break;
        }
    }

    public void setStarStatus(boolean isStar) {
        if (isStar) {
            loveBt.setImageResource(R.drawable.ic_favorite_black);
            data.get(chosenPos).setIsCollected(1);
        } else {
            loveBt.setImageResource(R.drawable.ic_favorite_border_black);
            data.get(chosenPos).setIsCollected(0);
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

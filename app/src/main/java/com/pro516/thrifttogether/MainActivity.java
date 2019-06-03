package com.pro516.thrifttogether;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.pro516.thrifttogether.entity.mine.User;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.discover.DiscoverFragment;
import com.pro516.thrifttogether.ui.home.HomeFragment;
import com.pro516.thrifttogether.ui.mall.MallFragment;
import com.pro516.thrifttogether.ui.mine.MineFragment;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;

import java.io.IOException;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;
import static com.pro516.thrifttogether.ui.network.Url.LOAD_ALL;
import static com.pro516.thrifttogether.ui.network.Url.USER_INFO;

public class MainActivity extends BaseActivity {

    private Fragment mLastFragment;
    private static final Fragment[] FRAGMENTS = new Fragment[4];
    public User userInfo;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initFragments();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadData();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(MainActivity.this, getString(R.string.busy_server), Toast.LENGTH_SHORT).show();
                    break;
                case LOAD_ALL:
                    Toast.makeText(MainActivity.this,"获取到了用户信息", Toast.LENGTH_SHORT).show();
                    userInfo = ((User) msg.obj);
                    SharedPreferences userSettings = getSharedPreferences("setting", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userSettings.edit();
                    editor.putString("name",userInfo.getUsername());
                    editor.putString("avatorUrl",userInfo.getAvatorUrl());
                    editor.putString("phone",userInfo.getPhone());
                    editor.putInt("integral",userInfo.getIntegral());
                    editor.putInt("userId",userInfo.getUserId());
                    editor.apply();
                    Log.d("ddd",userInfo.getUsername());
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(USER_INFO);
                    User mData = JsonParser.getUserInfo(json);
                    mHandler.obtainMessage(LOAD_ALL, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initFragments() {
        FRAGMENTS[0] = new HomeFragment();
        FRAGMENTS[1] = new DiscoverFragment();
        FRAGMENTS[2] = new MallFragment();
        FRAGMENTS[3] = new MineFragment();
        setFragment(FRAGMENTS[0]);
    }

    private void setFragment(Fragment fragment) {
        String tag = fragment.getClass().getSimpleName();
        Fragment result = getSupportFragmentManager().findFragmentByTag(tag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mLastFragment != null) {
            transaction.hide(mLastFragment);
        }
        if (result == null) {
            transaction.add(R.id.main, fragment, tag).commit();
        } else {
            transaction.show(result).commit();
        }
        mLastFragment = fragment;
    }

    /**
     * 底部导航的监听事件
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.tabbar_home:
                setFragment(FRAGMENTS[0]);
                break;
            case R.id.tabbar_discover:
                setFragment(FRAGMENTS[1]);
                break;
            case R.id.tabbar_mall:
                setFragment(FRAGMENTS[2]);
                break;
            case R.id.tabbar_mine:
                setFragment(FRAGMENTS[3]);
                break;
        }
        return true;
    };
}

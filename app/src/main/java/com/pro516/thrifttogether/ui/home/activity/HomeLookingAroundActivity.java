package com.pro516.thrifttogether.ui.home.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import com.mcxtzhang.layoutmanager.swipecard.RenRenCallback;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.mine.LookingAroundShopVO;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.home.adapter.HomeLookingAroundAdapter;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;

import java.io.IOException;
import java.util.List;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;

/**
 * 随便看看
 */
public class HomeLookingAroundActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_looking_around;
    }

    @Override
    protected void init() {
        initToolbar();
        loadLookingAroundData();
    }

    private void initToolbar() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.just_looking_around));
        backBtn.setOnClickListener(this);
    }

    private void initCard(List<LookingAroundShopVO> lookingAroundShopVO) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new OverLayCardLayoutManager());
        HomeLookingAroundAdapter adapter = new HomeLookingAroundAdapter(R.layout.item_home_looking_around,lookingAroundShopVO);
        recyclerView.setAdapter(adapter);
        CardConfig.initConfig(this);
        CardConfig.MAX_SHOW_COUNT = 6;

        ItemTouchHelper.Callback callback = new RenRenCallback(recyclerView, adapter, lookingAroundShopVO);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    break;
                case 123:
                    //initListView((List<ShopBean>) msg.obj);
                    initCard((List<LookingAroundShopVO>) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadLookingAroundData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String json = HttpUtils.getStringFromServer(Url.LOOKING_AROUND);
                    List<LookingAroundShopVO> mData = JsonParser.lookingAroundShopVO(json);
                    //System.out.println("---------------------------->" + mData);
                    mHandler.obtainMessage(123, mData).sendToTarget();
                } catch (IOException e) {
                    mHandler.obtainMessage(ERROR).sendToTarget();
                    e.printStackTrace();
                }
            }
        }.start();
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

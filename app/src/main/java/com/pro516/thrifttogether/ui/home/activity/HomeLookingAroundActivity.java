package com.pro516.thrifttogether.ui.home.activity;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import com.mcxtzhang.layoutmanager.swipecard.RenRenCallback;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.entity.home.BannerInfo;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.home.adapter.HomeLookingAroundAdapter;

import java.util.ArrayList;
import java.util.List;

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
        initCard();
    }

    private void initToolbar() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.just_looking_around));
        backBtn.setOnClickListener(this);
    }

    private void initCard() {
        List<BannerInfo> bannerInfos = new ArrayList<>();
        String[] imageUrls = getResources().getStringArray(R.array.bannerImageUrls);
        for (String imageUrl : imageUrls) {
            BannerInfo bannerInfo = new BannerInfo();
            bannerInfo.setImageUrl(imageUrl);
            bannerInfos.add(bannerInfo);
        }
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new OverLayCardLayoutManager());
        HomeLookingAroundAdapter adapter = new HomeLookingAroundAdapter();
        recyclerView.setAdapter(adapter);
        CardConfig.initConfig(this);
        CardConfig.MAX_SHOW_COUNT = 6;

        ItemTouchHelper.Callback callback = new RenRenCallback(recyclerView, adapter, bannerInfos);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
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

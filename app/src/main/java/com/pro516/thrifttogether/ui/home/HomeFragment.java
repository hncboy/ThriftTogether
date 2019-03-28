package com.pro516.thrifttogether.ui.home;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.home.activity.HomeCityPickerActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yzq.zxinglibrary.android.CaptureActivity;


/**
 * Created by hncboy on 2019-03-19.
 */
public class HomeFragment extends BaseFragment implements OnClickListener {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View view) {
        AppCompatImageButton mScanQrcodeBtn = view.findViewById(R.id.scan_qrcode_btn);
        LinearLayout cityPickerLLayout = view.findViewById(R.id.city_picker_layout);
        LinearLayout homeSearchLLayout = view.findViewById(R.id.home_search_layout);
        mScanQrcodeBtn.setOnClickListener(this);
        cityPickerLLayout.setOnClickListener(this);
        homeSearchLLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_qrcode_btn:
                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.Group.STORAGE, Permission.Group.CAMERA)
                        .onGranted(permissions -> {
                            startActivity(CaptureActivity.class, false);
                        })
                        .onDenied(permissions -> {
                            toast("请开启权限");
                        })
                        .start();
                break;
            case R.id.city_picker_layout:
                startActivity(HomeCityPickerActivity.class);
                break;
            case R.id.home_search_layout:

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}

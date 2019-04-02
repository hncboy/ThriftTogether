package com.pro516.thrifttogether.ui.home.activity.hotel;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;

/**
 * 选择住宿时间
 */
public class HotelDatePickerActivity extends BaseActivity implements View.OnClickListener  {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_hotel_date_picker;
    }

    @Override
    protected void init() {
        initToolbar();
        initCalendarView();
    }

    private void initCalendarView() {

    }

    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        backImgBtn.setVisibility(View.VISIBLE);
        backImgBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.choose_date));
        backImgBtn.setOnClickListener(this);
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

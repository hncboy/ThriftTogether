package com.pro516.thrifttogether.ui.home.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;

import org.jaaksi.pickerview.picker.TimePicker;
import org.jaaksi.pickerview.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StoreActivity extends BaseActivity implements View.OnClickListener, TimePicker.OnTimeSelectListener {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_store;
    }

    @Override
    protected void init() {
        initToolbar();
        Button mReservation = findViewById(R.id.store_reservation);
        mReservation.setOnClickListener(this);
    }

    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        backImgBtn.setOnClickListener(this);
    }

    public static final DateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    public static final SimpleDateFormat mDateFormat =
            new SimpleDateFormat("MM月dd日  E", Locale.CHINA);

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.store_reservation:
                Calendar calendar = Calendar.getInstance();
                long createTime = calendar.getTimeInMillis();
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);
                TimePicker mTimePicker = new TimePicker.Builder(this, TimePicker.TYPE_MIXED_DATE | TimePicker.TYPE_MIXED_TIME,
                        this)
                        // 设置不包含超出的结束时间<=
                        .setContainsEndDate(false)
                        // 设置时间间隔为30分钟
                        .setTimeMinuteOffset(60).setRangDate(createTime, calendar.getTimeInMillis())
                        .setFormatter(new TimePicker.DefaultFormatter() {
                            @Override
                            public CharSequence format(TimePicker picker, int type, int position, long value) {
                                if (type == TimePicker.TYPE_MIXED_DATE) {
                                    CharSequence text;
                                    int dayOffset = DateUtil.getDayOffset(value, System.currentTimeMillis());
                                    if (dayOffset == 0) {
                                        text = "今天";
                                    } else if (dayOffset == 1) {
                                        text = "明天";
                                    } else { // xx月xx日 星期 x
                                        text = mDateFormat.format(value);
                                    }
                                    return text;
                                }
                                return super.format(picker, type, position, value);
                            }
                        })
                        .create();
                Dialog pickerDialog = mTimePicker.getPickerDialog();
                pickerDialog.setCanceledOnTouchOutside(true);
                mTimePicker.getTopBar().getTitleView().setText("请选择时间");
                mTimePicker.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSelect(TimePicker picker, Date date) {
        Toast.makeText(StoreActivity.this, "点击：" + sSimpleDateFormat.format(date), Toast.LENGTH_SHORT).show();
        //传递些简单的参数
        Intent intent = new Intent();
        intent.setClass(StoreActivity.this,ReservationDetailsActivity.class);
        Bundle bundleSimple = new Bundle();
        bundleSimple.putString("time", sSimpleDateFormat.format(date));
        intent.putExtras(bundleSimple);
        startActivity(intent);
    }
}

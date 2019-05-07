package com.pro516.thrifttogether.ui.home.activity;

import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.mine.voucherPackage.VoucherPackageActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.qqtheme.framework.picker.DatePicker;

public class StoreActivity extends BaseActivity implements View.OnClickListener {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.store_reservation:
                DatePicker picker = new DatePicker(this);
                picker.setRange(2019, 2022);//年份范围

                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");

                Calendar cal = Calendar.getInstance();
                System.out.println("现在时间："+sdf.format(cal.getTime()));
                //分别获取年、月、日
                //System.out.println("年："+cal.get(cal.YEAR));
                //System.out.println("月："+(cal.get(cal.MONTH)+1));//老外把一月份整成了0，翻译成中国月份要加1
                //System.out.println("日："+cal.get(cal.DATE));
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH)+1;
                int day=cal.get(Calendar.DATE);
                picker.setSelectedItem(year,month,day);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        Toast.makeText(StoreActivity.this, "点击：" + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
                break;
            default:
                break;
        }
    }
}

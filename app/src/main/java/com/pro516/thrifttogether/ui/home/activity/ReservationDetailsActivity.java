package com.pro516.thrifttogether.ui.home.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.buy.widget.AddAndSubView;
import com.pro516.thrifttogether.ui.home.entity.Reservation;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Response;

import static com.pro516.thrifttogether.ui.network.Url.ERROR;

public class ReservationDetailsActivity extends BaseActivity implements View.OnClickListener {

    AddAndSubView numberControl;
    EditText nameEt,phoneEt, noteEt;
    TextView priceTv;
    TextView timeText;
    Button reserveBt;
    Intent intent;
    Bundle bundle;
    Reservation data;
    String time;
    SharedPreferences userSetting;
    public static final int RESERVATION_SUCCESS = 100;
    public static final int RESERVATION_UNSUCCESS = 200;
    public static final DateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);

    @Override
    public int getLayoutRes() {
        return R.layout.activity_reservation_details;
    }

    @Override
    protected void init() {
        initToolbar();
        initView();
    }

    private void initView() {
        numberControl = findViewById(R.id.reservation_num);
        nameEt = findViewById(R.id.reservation_name);
        phoneEt = findViewById(R.id.reservation_phone);
        noteEt = findViewById(R.id.reservation_note);
        priceTv = findViewById(R.id.reservation_price);
        timeText=findViewById(R.id.reservation_time);
        reserveBt = findViewById(R.id.reservation_bt);
        userSetting = getSharedPreferences("setting", Context.MODE_PRIVATE);
        bundle = this.getIntent().getExtras();
        intent = getIntent();
        time = bundle != null ? bundle.getString("time") : null;
        timeText.setText(time);
        reserveBt.setOnClickListener(this);
    }

    private void initToolbar() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("预定详情");
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESERVATION_SUCCESS:
                    toast("预约成功");
                    finish();
                    break;
                case RESERVATION_UNSUCCESS:
                    toast("预约失败");
                    finish();
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.reservation_bt:
                data = new Reservation();
                data.setReservationPeopleName(nameEt.getText().toString());
                data.setReservationPeopleNum(numberControl.getNum());
                data.setReservationPeoplePhone(phoneEt.getText().toString());
                data.setReservationRemarks(noteEt.getText().toString());
                try {
                    data.setReserveTime(sSimpleDateFormat.parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                data.setShopId(bundle.getInt("storeId"));
                data.setUserId(userSetting.getInt("userId",1));
                Log.i("AAA","upload date "+data.getReserveTime());
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Response rs = HttpUtils.doPost(Url.RESERVATION,data);
                            boolean mData = JsonParser.newReservation(rs.body().string());
                            //System.out.println("---------------------------->" + mData);
                            if (mData)
                                mHandler.obtainMessage(RESERVATION_SUCCESS).sendToTarget();
                            else
                                mHandler.obtainMessage(RESERVATION_UNSUCCESS).sendToTarget();
                        } catch (IOException e) {
                            mHandler.obtainMessage(ERROR).sendToTarget();
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            default:
                break;
        }
    }
}

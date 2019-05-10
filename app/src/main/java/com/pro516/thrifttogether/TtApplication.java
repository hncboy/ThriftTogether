package com.pro516.thrifttogether;

import android.app.Application;
import android.graphics.Color;
import android.graphics.Rect;
import android.widget.LinearLayout;

import com.pro516.thrifttogether.ui.home.activity.CustomTopBar;

import org.jaaksi.pickerview.picker.BasePicker;
import org.jaaksi.pickerview.topbar.ITopBar;
import org.jaaksi.pickerview.util.Util;
import org.jaaksi.pickerview.widget.DefaultCenterDecoration;
import org.jaaksi.pickerview.widget.PickerView;

/**
 * Created by hncboy on 2019-03-28.
 */
public class TtApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 建议在application中初始化picker 默认属性实现全局设置
        initDefaultPicker();
    }

    private void initDefaultPicker() {
        // 利用修改静态默认属性值，快速定制一套满足自己app样式需求的Picker.
        // BasePickerView
        PickerView.sDefaultVisibleItemCount = 5;
        PickerView.sDefaultItemSize = 50;
        PickerView.sDefaultIsCirculation = false;
        //PickerView.sDefaultDrawIndicator = false;

        // PickerView
        PickerView.sOutTextSize = 18;
        PickerView.sCenterTextSize = 18;
        PickerView.sCenterColor =  Color.parseColor("#4693EC");
        PickerView.sOutColor = Color.GRAY;
        //PickerView.sShadowColors = null;

        // BasePicker
        int padding = Util.dip2px(this, 20);
        BasePicker.sDefaultPaddingRect = new Rect(padding, padding, padding, padding);
        BasePicker.sDefaultPickerBackgroundColor = Color.WHITE;
        BasePicker.sDefaultCanceledOnTouchOutside = false;
        // 自定义 TopBar
        BasePicker.sDefaultTopBarCreator = CustomTopBar::new;

        // DefaultCenterDecoration
        DefaultCenterDecoration.sDefaultLineWidth = 1;
        DefaultCenterDecoration.sDefaultLineColor = Color.parseColor("#4693EC");
        //DefaultCenterDecoration.sDefaultDrawable = new ColorDrawable(Color.WHITE);
        int leftMargin = Util.dip2px(this, 10);
        int topMargin = Util.dip2px(this, 2);
        DefaultCenterDecoration.sDefaultMarginRect =
                new Rect(leftMargin, -topMargin, leftMargin, -topMargin);
    }
}

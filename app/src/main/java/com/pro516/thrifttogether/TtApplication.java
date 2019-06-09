package com.pro516.thrifttogether;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.pro516.thrifttogether.app.cos.LocalCredentialProvider;
import com.pro516.thrifttogether.ui.home.activity.CustomTopBar;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.jaaksi.pickerview.picker.BasePicker;
import org.jaaksi.pickerview.util.Util;
import org.jaaksi.pickerview.widget.DefaultCenterDecoration;
import org.jaaksi.pickerview.widget.PickerView;

/**
 * Created by hncboy on 2019-03-28.
 */
public class TtApplication extends Application {

    private static final String TAG = "TtApplication";

    private CosXmlService cosXmlService;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化腾讯云配置
        initCos();
        initUPush();
        // 建议在application中初始化picker 默认属性实现全局设置
        initDefaultPicker();
        //Fresco初始化
        Fresco.initialize(this);
    }

    /**
     * 初始化友盟推送
     */
    private void initUPush() {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, "5cf50e264ca3575943000ca3", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "b65044793605da40fe3b24ad2e4aa376");
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        // deviceToken = AvSe94J_stLyACyXxJ7vXYtca0A3jAPNwi3dfFT6ClsY
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG,"注册成功：deviceToken：-------->  " + deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
    }

    /**
     * 初始化腾讯云对象配置
     */
    private void initCos() {
        String appid = "1251129737";
        String region = "ap-chengdu";

        String secretId = "AKIDTsLellxYP9j7DTAfU9ctysdO5e2FhBy8";
        String secretKey = "Uq23C6wbBewDMPHxRly1scFhOwKXcXLL";
        long keyDuration = 600; //SecretKey 的有效时间，单位秒

        //创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .setDebuggable(true)
                .builder();

        //创建获取签名类(请参考下面的生成签名示例，或者参考 sdk中提供的ShortTimeCredentialProvider类）
        LocalCredentialProvider localCredentialProvider = new LocalCredentialProvider(secretId, secretKey, keyDuration);

        //创建 CosXmlService 对象，实现对象存储服务各项操作.
        Context context = getApplicationContext(); //应用的上下文

        cosXmlService = new CosXmlService(context, serviceConfig, localCredentialProvider);
    }

    public CosXmlService getCosXmlService() {
        return cosXmlService;
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

package com.pro516.thrifttogether.ui.home.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;

/**
 * 车票
 */
public class HomeTicketActivity extends BaseActivity implements View.OnClickListener {

    private String url = "http://i.meituan.com/awp/h5/train/search/index.html?train_source=meituan@wap&cevent=homepage%2Fcategory1%2F20066";
    private WebView mWebView;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_ticket;
    }

    @Override
    protected void init() {
        initWebView();
        initToolbar();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        //得到WebView对象
        mWebView = findViewById(R.id.webview);
        //通过WebView得到WebSettings对象
        WebSettings webSettings = mWebView.getSettings();
        //设置支持Javascript的参数
        webSettings.setJavaScriptEnabled(true);
        //启用地理定位，默认为true
        webSettings.setGeolocationEnabled(true);
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        // 开启缓存
        webSettings.setAppCacheEnabled(true);
        // 开启http
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        // 开启H5访问
        webSettings.setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(final WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebView.loadUrl(url);
    }

    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        backImgBtn.setVisibility(View.VISIBLE);
        backImgBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        AppCompatImageButton searchImgBtn = findViewById(R.id.common_toolbar_function_right);
        searchImgBtn.setVisibility(View.VISIBLE);
        searchImgBtn.setImageDrawable(getDrawable(R.drawable.ic_search_white_24dp));
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.ticket));
        backImgBtn.setOnClickListener(this);
        searchImgBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.common_toolbar_function_right:
                startActivity(HomeSearchActivity.class);
                break;
            default:
                break;
        }
    }
}

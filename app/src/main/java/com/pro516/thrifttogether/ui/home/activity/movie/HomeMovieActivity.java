package com.pro516.thrifttogether.ui.home.activity.movie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.home.activity.HomeSearchActivity;
import com.pro516.thrifttogether.ui.home.adapter.GirdDropDownAdapter;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 电影
 */
public class HomeMovieActivity extends BaseActivity implements View.OnClickListener {

    private String url = "http://m.maoyan.com/imeituan/?_v_=yes&my_traffic_sources=group&ci=51&stid_b=1&cevent=imt%2Fhomepage%2Fcategory1%2F99#movie";
    private WebView mWebView;

    private DropDownMenu mDropDownMenu;
    private GirdDropDownAdapter brandAdapter;
    private GirdDropDownAdapter nearbyAdapter;
    private GirdDropDownAdapter sortAdapter;
    private GirdDropDownAdapter filterAdapter;
    private List<View> popupViews = new ArrayList<>();

    private String headers[] = {"品牌", "附近", "智能排序", "筛选"};
    private String allFoods[] = {"不限", "横店电影城", "大地影院", "万达影院", "保利国际影城", "环球时代影城"};
    private String nearbys[] = {"附近", "海曙区", "鄞州区", "江北区", "北仑区", "镇海区", "余姚区", "慈溪市", "奉化市", "宁海县", "象山县"};
    private String sortings[] = {"智能排序", "离我最近", "价格最低"};
    private String filters[] = {"IMAX厅", "4D厅", "儿童厅", "巨幕厅", "杜比全景声厅"};

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_movie;
    }

    @Override
    protected void init() {

        initToolbar();
        initWebView();
        //initMovie();
        //initDropMenu();
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


    private void initMovie() {
        LinearLayout movieLayout = findViewById(R.id.movie_layout);
        movieLayout.setOnClickListener(this);
    }

    private void initToolbar() {
        AppCompatImageButton backImgBtn = findViewById(R.id.common_toolbar_function_left);
        backImgBtn.setVisibility(View.VISIBLE);
        backImgBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        AppCompatImageButton searchImgBtn = findViewById(R.id.common_toolbar_function_right);
        searchImgBtn.setVisibility(View.VISIBLE);
        searchImgBtn.setImageDrawable(getDrawable(R.drawable.ic_search_white_24dp));
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.movie));
        backImgBtn.setOnClickListener(this);
        searchImgBtn.setOnClickListener(this);
    }

    private void initDropMenu() {
        // 品牌
        ListView brandView = new ListView(this);
        brandAdapter = new GirdDropDownAdapter(this, Arrays.asList(allFoods));
        brandView.setDividerHeight(0);
        brandView.setAdapter(brandAdapter);
        brandView.setOnItemClickListener((parent, view, position, id) -> {
            brandAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[0] : allFoods[position]);
            mDropDownMenu.closeMenu();
        });

        // 附近
        ListView nearbyView = new ListView(this);
        nearbyAdapter = new GirdDropDownAdapter(this, Arrays.asList(nearbys));
        nearbyView.setDividerHeight(0);
        nearbyView.setAdapter(nearbyAdapter);
        nearbyView.setOnItemClickListener((parent, view, position, id) -> {
            nearbyAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[1] : nearbys[position]);
            mDropDownMenu.closeMenu();
        });

        // 智能排序
        ListView sortView = new ListView(this);
        sortAdapter = new GirdDropDownAdapter(this, Arrays.asList(sortings));
        sortView.setDividerHeight(0);
        sortView.setAdapter(sortAdapter);
        sortView.setOnItemClickListener((parent, view, position, id) -> {
            sortAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[2] : sortings[position]);
            mDropDownMenu.closeMenu();
        });

        // 筛选
        ListView filterView = new ListView(this);
        filterAdapter = new GirdDropDownAdapter(this, Arrays.asList(filters));
        filterView.setDividerHeight(0);
        filterView.setAdapter(filterAdapter);
        filterView.setOnItemClickListener((parent, view, position, id) -> {
            filterAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[3] : filters[position]);
            mDropDownMenu.closeMenu();
        });

        popupViews.add(brandView);
        popupViews.add(nearbyView);
        popupViews.add(sortView);
        popupViews.add(filterView);

        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        mDropDownMenu = findViewById(R.id.dropDownMenu);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
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
            case R.id.movie_layout:
                startActivity(ChooseMovieSeatTableActivity.class);
                break;
            default:
                break;
        }
    }

   /* @Override
    public void onBackPressed() {
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }*/
}

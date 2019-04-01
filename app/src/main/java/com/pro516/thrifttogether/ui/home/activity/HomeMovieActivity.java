package com.pro516.thrifttogether.ui.home.activity;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;

public class HomeMovieActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_home_movie;
    }

    @Override
    protected void init() {
        initToolbar();
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

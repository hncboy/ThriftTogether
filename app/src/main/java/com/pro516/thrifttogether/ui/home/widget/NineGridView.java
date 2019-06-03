package com.pro516.thrifttogether.ui.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NineGridView extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;

    public NineGridView(Context context) {
        super(context);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected boolean displayOneImage(RatioImageView imageView, String url, int parentWidth) {
        return true;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        Log.i("aaaa","set image");
        Glide.with(mContext).load(url).into(imageView);
    }

    @Override
    protected void onClickImage(int position, String url, ArrayList<String> urlList) {

    }
}

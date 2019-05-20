package com.pro516.thrifttogether.ui.mine.order;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.yzq.zxinglibrary.encode.CodeCreator;

public class UseActivity extends BaseActivity implements View.OnClickListener{
    Bitmap bitmap = null;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_mine_order_use;
    }

    @Override
    protected void init() {

        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("使用");

        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("id"));

        /*
         * contentEtString：字符串内容
         * w：图片的宽
         * h：图片的高
         * logo：不需要logo的话直接传null
         * */
        /*生成的图片*/
        ImageView contentIv = findViewById(R.id.contentIv);

        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmap = CodeCreator.createQRCode("胡楠灿", 400, 400, logo);
        contentIv.setImageBitmap(bitmap);
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

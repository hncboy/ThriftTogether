package com.pro516.thrifttogether.ui.mine.feedback;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;

public class MineFeedBackActivity extends BaseActivity implements View.OnClickListener {
    private static final int MAX_COUNT = 200;
    private TextView mTextCount = null;
    private EditText mEtContent= null;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_mine_feed_back;
    }

    @Override
    protected void init() {
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("意见反馈");

        mEtContent = findViewById(R.id.mine_feedback_et_content);
        mTextCount = findViewById(R.id.mine_feedback_text_count);
        mEtContent.addTextChangedListener(new TextWatcher() { //对EditText进行监听
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTextCount.setText("剩余字数：" + (MAX_COUNT - editable.length()));
            }
        });
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

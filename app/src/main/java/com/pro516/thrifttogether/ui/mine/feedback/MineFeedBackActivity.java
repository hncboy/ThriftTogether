package com.pro516.thrifttogether.ui.mine.feedback;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.network.HttpUtils;

import java.io.IOException;

import static com.pro516.thrifttogether.ui.network.Url.USER_FEEDBACK;

public class MineFeedBackActivity extends BaseActivity implements View.OnClickListener {
    private static final int MAX_COUNT = 200;
    private TextView mTextCount = null;
    private EditText mEtContent = null;
    private Button mButton;

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
        mButton = findViewById(R.id.mine_feedback_btn_submit);
        mEtContent = findViewById(R.id.mine_feedback_et_content);
        mTextCount = findViewById(R.id.mine_feedback_text_count);
        mButton.setOnClickListener(this);
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

    private void submit() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    HttpUtils.doGet(USER_FEEDBACK + mEtContent.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.mine_feedback_btn_submit:
                submit();
                Toast.makeText(this,"评价成功！",Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                break;
        }
    }
}

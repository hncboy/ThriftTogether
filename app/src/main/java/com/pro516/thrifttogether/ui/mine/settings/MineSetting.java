package com.pro516.thrifttogether.ui.mine.settings;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.login.LoginActivity;

public class MineSetting extends BaseActivity implements View.OnClickListener {

    private LinearLayout mPersonalInformation;
    private Button mQuitAccount;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_mine_setting;
    }

    private void setListeners() {
        mPersonalInformation.setOnClickListener(this);
        mQuitAccount.setOnClickListener(this);
    }

    @Override
    public void init() {
        mPersonalInformation = findViewById(R.id.personal_information);
        mQuitAccount=findViewById(R.id.quit_account);
        setListeners();
        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText(getString(R.string.settings));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_information:
                startActivity(EditPersonalInformationActivity.class);
                break;
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.quit_account:
                startActivity(LoginActivity.class);
                break;
            default:
                break;
        }
    }
}

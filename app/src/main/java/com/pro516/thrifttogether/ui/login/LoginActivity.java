package com.pro516.thrifttogether.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pro516.thrifttogether.MainActivity;
import com.pro516.thrifttogether.R;

public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText accountEditTxt;
    private AppCompatEditText passwordEditTxt;
    private AppCompatButton loginBtn;
    private TextView link;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getPermission();
    }

    private void getPermission() {
        /*
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                }
        );
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            return;
        }

        /*
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。

                } else {
                    // 权限被用户拒绝了。
                    Toast.makeText(this, "定位权限被禁止，相关地图功能无法使用！", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        }
    }
    private void initView() {
        accountEditTxt = findViewById(R.id.login_account);
        passwordEditTxt = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.btn_login);
        link = findViewById(R.id.link_sign_up);
        loginBtn.setOnClickListener(new LoginClickListener());
        link.setOnClickListener(new LoginClickListener());
    }

    private class LoginClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_login) {
                String account = null, password = null;
                if (accountEditTxt.getText() != null)
                    account = accountEditTxt.getText().toString();
                if (passwordEditTxt.getText() != null)
                    password = passwordEditTxt.getText().toString();
                if (account != null && password != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            } else if (v.getId() == R.id.link_sign_up) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }
    }
}

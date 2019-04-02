package com.pro516.thrifttogether.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.TextView;

import com.pro516.thrifttogether.MainActivity;
import com.pro516.thrifttogether.R;

public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText accountEditTxt;
    private AppCompatEditText passwordEditTxt;
    private AppCompatButton loginBtn;
    private TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
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
            if (v.getId()==R.id.btn_login){
                String account = null, password = null;
                if (accountEditTxt.getText() != null)
                    account = accountEditTxt.getText().toString();
                if (passwordEditTxt.getText() != null)
                    password = passwordEditTxt.getText().toString();
                if (account != null && password != null) {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            } else if (v.getId()==R.id.link_sign_up){
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        }
    }
}

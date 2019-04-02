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

public class RegisterActivity extends AppCompatActivity {

    private AppCompatEditText accountEditTxt;
    private AppCompatEditText passwordEditTxt;
    private AppCompatEditText checkPasswordEditTxt;
    private AppCompatButton registerBtn;
    private TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        accountEditTxt = findViewById(R.id.register_account);
        passwordEditTxt = findViewById(R.id.register_password);
        checkPasswordEditTxt = findViewById(R.id.register_check_password);
        registerBtn = findViewById(R.id.btn_register);
        link = findViewById(R.id.link_sign_in);
        checkPasswordEditTxt = findViewById(R.id.register_check_password);
        registerBtn.setOnClickListener(new RegisterClickListener());
        link.setOnClickListener(new RegisterClickListener());
    }

    private class RegisterClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_register) {
                String account = null, password = null, checkPassword = null;
                if (accountEditTxt.getText() != null)
                    account = accountEditTxt.getText().toString();
                if (passwordEditTxt.getText() != null)
                    password = passwordEditTxt.getText().toString();
                if (checkPasswordEditTxt.getText() != null)
                    checkPassword = checkPasswordEditTxt.getText().toString();
                if (account != null && password != null && checkPassword != null && checkPassword.equals(password)) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            } else if (v.getId() == R.id.link_sign_in) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}

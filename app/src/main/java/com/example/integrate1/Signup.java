package com.example.integrate1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//目标是
public class Signup extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelperLoginSystem DBOpenHelper;
    private Button Signup;
    private Button login;
    private EditText mphonenumber;
    private EditText musername;
    private EditText mpassword;
    private EditText mpasswordagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_fragment);
        //初始化方法
        initUI();

        DBOpenHelper = new DBOpenHelperLoginSystem(this);
    }


    private void initUI() {

        Signup = findViewById(R.id.btn_Signup);
        login = findViewById(R.id.btn_signup_login);
        mphonenumber = findViewById(R.id.phone_signup);
        musername = findViewById(R.id.username);
        mpassword = findViewById(R.id.password);
        mpasswordagain = findViewById(R.id.confirm_password);


        login.setOnClickListener(this);
        Signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup_login:
                //跳转到登录界面
                Intent intent01 = new Intent();
                intent01.setClass(getApplicationContext(), Login.class);//指定字节码文件
                this.startActivity(intent01);
                break;

            case R.id.btn_Signup:    //注册按钮
                //获取用户输入的用户名、密码、手机号
                String username = musername.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String passwordagain = mpasswordagain.getText().toString().trim();
                String phonenumber = mphonenumber.getText().toString().trim();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phonenumber) ) {


                    if (passwordagain.equals(password)) {
                        //验证两次密码是否一致
                        DBOpenHelper.add(username, password);
                        Intent intent02 = new Intent(this, Login.class);
                        startActivity(intent02);
                        finish();
                        Toast.makeText(this,  "注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "前后密码输入不一致,注册失败", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
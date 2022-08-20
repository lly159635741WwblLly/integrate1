package com.example.integrate1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelperLoginSystem DBOpenHelper;
    private EditText Name;
    private EditText Password;
    private Button Login;
    private Button Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fragment);
        //初始化方法
        initUI();
        DBOpenHelper = new DBOpenHelperLoginSystem(this);
    }

    private void initUI() {
        Signup = findViewById(R.id.btn_login_signup);
        Login = findViewById(R.id.btn_Login);
        Name = findViewById(R.id.name_login);
        Password = findViewById(R.id.password_login);

        Signup.setOnClickListener(this);
        Login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_signup:
                //跳转到注册界面
                Intent intent01 = new Intent();
                intent01.setClass(getApplicationContext(), Signup.class);//指定字节码文件
                this.startActivity(intent01);
                break;
            case R.id.btn_Login:
                String name = Name.getText().toString().trim();
                String password = Password.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<com.example.integrate1.User> data = DBOpenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        //进入界面要改

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();//销毁此Activity
                    } else {
                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

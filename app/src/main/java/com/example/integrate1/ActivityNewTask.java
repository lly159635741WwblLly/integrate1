package com.example.integrate1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityNewTask extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        //初始化控件
        initUI();
    }

    private void initUI() {
        findViewById(R.id.button_acc_tilt_task).setOnClickListener(this);
        findViewById(R.id.button_pic_task).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()){
            case R.id.button_acc_tilt_task:
                //跳转到加速度和倾角测量
                intent.setClass(getApplicationContext(),ActivityAccAndTiltMonitor.class);
                break;
            case R.id.button_pic_task:
                //跳转到图像采集
                intent.setClass(getApplicationContext(), MainActivity_Camera.class);
                break;
        }
        startActivity(intent);
    }


}
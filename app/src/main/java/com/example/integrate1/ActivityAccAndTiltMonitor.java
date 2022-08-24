package com.example.integrate1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityAccAndTiltMonitor extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_and_tilt_monitor);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.button_sensor).setOnClickListener(this);
        findViewById(R.id.button_start_task).setOnClickListener(this);
        findViewById(R.id.button_result).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sensor:

                //跳转到第一个界面
                Intent intent01 = new Intent();
                intent01.setClass(getApplicationContext(), DetectSensors.class);//指定字节码文件
                this.startActivity(intent01);

                break;
            case R.id.button_start_task:
                //跳转到第二个界面
                Intent intent02 = new Intent();
                intent02.setClass(getApplicationContext(), Settings.class);//指定字节码文件
                this.startActivity(intent02);
                break;
            case R.id.button_result:
                //跳转到第三个界面,仍需修改
                Intent intent03 = new Intent();
                intent03.setClass(getApplicationContext(), ShowResults.class);//指定字节码文件
                this.startActivity(intent03);
                break;
        }
    }
}
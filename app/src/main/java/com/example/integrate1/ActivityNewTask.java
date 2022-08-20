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
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),ActivityAccAndTiltMonitor.class);
        startActivity(intent);
    }
}
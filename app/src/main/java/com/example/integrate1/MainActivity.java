package com.example.integrate1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        //初始化控件
        initUI();
    }
    private void initUI() {
        findViewById(R.id.button_my_pro).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),ActivityMyProject.class);
        startActivity(intent);
    }
}
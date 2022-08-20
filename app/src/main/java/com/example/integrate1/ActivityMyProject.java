package com.example.integrate1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMyProject extends AppCompatActivity implements View.OnClickListener {

    private ListView list_view_project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);
        //初始化控件
        initUI();
    }

    private void initUI() {
        findViewById(R.id.button_new_pro).setOnClickListener(this);
        list_view_project = findViewById(R.id.lv_pro);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),ActivityNewProject.class);
        startActivity(intent);
    }
}
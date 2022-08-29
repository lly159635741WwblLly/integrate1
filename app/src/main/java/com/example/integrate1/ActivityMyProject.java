package com.example.integrate1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMyProject extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);
        //初始化控件
        initUI();
    }

    private void initUI() {
        findViewById(R.id.button_new_pro).setOnClickListener(this);
        findViewById(R.id.button_data_file_list).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_new_pro:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ActivityNewProject.class);
                startActivity(intent);
                break;
            case R.id.button_data_file_list:
                Intent intent2 = new Intent();
                intent2.setClass(getApplicationContext(), ShowResults_Global.class);
                startActivity(intent2);
                break;
        }
    }
}
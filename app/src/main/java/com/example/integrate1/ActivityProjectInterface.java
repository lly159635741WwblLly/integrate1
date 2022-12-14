package com.example.integrate1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityProjectInterface extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_interface);
        //初始化控件
        initUI();

    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        TextView project_name_show = findViewById(R.id.pro_name_show);
        project_name_show.setText("项目名："+name);
        findViewById(R.id.button_create_new_task).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_new_task://文件上传
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ActivityNewTask.class);
                startActivity(intent);
                break;
            case R.id.button_data_file_list://文件上传
                Intent intent2 = new Intent();
                intent2.setClass(getApplicationContext(), ShowResults_Global.class);
                startActivity(intent2);
                break;
        }
    }
}
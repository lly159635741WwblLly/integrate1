package com.example.integrate1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActivityNewProject extends AppCompatActivity implements View.OnClickListener {

    private EditText project_name;
    private EditText project_intro;
    private EditText project_time;
    private EditText project_location;
    //建立一个string用来存放输入的项目名，当成项目文件夹的名字
    private String pro_folder_name;
    //每个项目文件夹下加一个txt记录项目页面输入的信息
    private final String mFilename = "pro_info.txt";
    private String[] project_information;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private String dir_childPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        //初始化界面
        initUI();
    }

    private void initUI() {
        findViewById(R.id.button_create_new_pro).setOnClickListener(this);
        //在点击按钮后获取输入的项目名
        project_name = findViewById(R.id.pro_name);
        //这里可以运行一下看看pro_folder_name是不是输入的值
        Log.i("data","pro_folder_name="+pro_folder_name);
        project_intro = findViewById(R.id.pro_intro);
        project_time = findViewById(R.id.pro_time);
        project_location = findViewById(R.id.pro_location);
        //用SharedPreferences储存项目文件夹路径
        sharedPreferences = getSharedPreferences("direction", MODE_PRIVATE);
        editor = sharedPreferences.edit();


    }

    @Override
    public void onClick(View view) {
        pro_folder_name = project_name.getText().toString();
        Log.i("data","pro_folder_name="+pro_folder_name);
        project_information = new String[4];
        project_information[0] = project_name.getText().toString();
        project_information[1] = project_intro.getText().toString();
        project_information[2] = project_time.getText().toString();
        project_information[3] = project_location.getText().toString();
        //调用下面声明的save方法
        save(project_information);
        //把项目文件夹路径存进SharedPreferences
        editor.putString("dir",dir_childPath);
        editor.commit();
        Log.i("data","项目文件夹路径："+dir_childPath);
        Intent intent = new Intent();
        //添加数据，用intent将输入的项目名传到下一个界面
        intent.putExtra("name",pro_folder_name);
        intent.setClass(getApplicationContext(), ActivityProjectInterface.class);

        if(!TextUtils.isEmpty(project_information[0])){
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "请输入项目名称", Toast.LENGTH_SHORT).show();}


    }
    private void save(String[] content){
        FileOutputStream fileOutputStream = null;
        String general_title = "项目信息";
        String[] subtitle = new String[4];
        subtitle[0] = "1.项目名称：";
        subtitle[1] = "2.项目简介：";
        subtitle[2] = "1.项目时间：";
        subtitle[3] = "1.项目地点：";
        File dir_parent = new File(Environment.getExternalStorageDirectory(),"Orion_cc");
        if(!dir_parent.exists() ){
            dir_parent.mkdirs();
        }
        //这里dir_child就是新建的项目文件储存路径
        File dir_child = new File(dir_parent,pro_folder_name);
        if (!dir_child.exists()) {
            dir_child.mkdirs();
        }
        //把dir_child从路径变为字符串
        dir_childPath = dir_child.getPath();
        File file = new File(dir_child,mFilename);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            //第一行标题
            fileOutputStream.write(general_title.getBytes());
            fileOutputStream.write("\r\n".getBytes());
            //用for循环写入每一条信息并换行
            for (int i = 0;i<content.length;i++){
                fileOutputStream.write((subtitle[i]+content[i]).getBytes());
                fileOutputStream.write("\r\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream !=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
package com.example.integrate1;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ShowResults extends AppCompatActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener{

    ListView listView;
    List<FileInfo_Results> list;
    MyAdapter adapter;
    TextView tv_path;
    TextView tv_info;

    String currPath; // 当前目录
    String parentPath; // 上级目录

    //确定文件目录
//    SharedPreferences sharedPreferences = getSharedPreferences("direction", MODE_PRIVATE);

    @SuppressLint("SdCardPath")
    String PATH = "/sdcard/Orion_cc";

    public static final int T_DIR = 0;// 文件夹
    public static final int T_FILE = 1;// 文件
    public static final int T_PIC = 2;// 文件:图片
    public static final int T_VIDEO = 3;// 文件:视频

    // 文件名的比较器
    Comparator<FileInfo_Results> nameComparator = new Comparator<FileInfo_Results>() {
        @Override
        public int compare(FileInfo_Results lhs, FileInfo_Results rhs) {
            return lhs.name.toLowerCase().compareTo(rhs.name.toLowerCase());
        }
    };



    @Override
    public  void onCreate(Bundle savedInstanceState) {
        // 设置全局配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)// 线程的优先级别
                .denyCacheImageMultipleSizesInMemory() // 拒绝不同的缓存大小
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// 对临时文件名加密
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb SDCard上的缓存空间
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 任务队列采取LIFO
                .writeDebugLogs() //调试日志-可在项目发布时删除
                .build();// 构建配置

        ImageLoader.getInstance().init(config);// 加载全局配置

        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
        setContentView(R.layout.activity_show_results);

        initView();// 初始化
        updateData(PATH);

    }
    private void initView(){
        tv_path = (TextView) findViewById(R.id.path);
        tv_info = (TextView) findViewById(R.id.tv_info);
        listView = (ListView) findViewById(R.id.list);
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void updateData(String path){
        currPath = path;// 记录当前的目录
        File file = new File(path);
        parentPath = file.getParent();// 更新了上级目录

        list = Utils_Results.getListData(path);// 数据
        KEYWORD = null;

        tv_info.setVisibility(View.GONE);// 不可见
        if (list.size() == 0){
            Toast.makeText(this, "当前目录为空", Toast.LENGTH_SHORT).show();
            tv_info.setVisibility(View.VISIBLE);// 恢复可见
        }
        list = getGroupList(list);// 排序
        adapter.setList(list);
        adapter.notifyDataSetChanged();// 刷新视图
        tv_path.setText(path);
    }
    public List<FileInfo_Results> getGroupList(List<FileInfo_Results> list){
        //1. 文件和文件夹分为两个集合
        List<FileInfo_Results> dirs = new ArrayList<FileInfo_Results>();// 文件夹
        List<FileInfo_Results> files = new ArrayList<FileInfo_Results>();// 文件
        for (FileInfo_Results item:list){
            if (item.type == T_DIR){
                dirs.add(item);
            }else{
                files.add(item);
            }
        }

        //2. 对2个集合分别排序
        Collections.sort(dirs,nameComparator);
        Collections.sort(files,nameComparator);
        //3. 集合合并:把文件集合添加到文件夹的结尾处
        dirs.addAll(files);
        return dirs;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        FileInfo_Results item = (FileInfo_Results) parent.getItemAtPosition(position);
        // 判断文件/文件夹
        if (item.type == T_DIR){
            // 进入
            updateData(item.path);

        }else{
            // 文件: 打开
            Utils_Results.openFile(this,new File(item.path));
        }

//        File file   = new File(item.path);
//        file.delete();//删除
//        file.mkdirs();// 创建目录

    }

    @Override
    public void onBackPressed() {
        // 返回 --> 打开上级
        if (currPath.equals(PATH)){
            // 退出流程
            getExit();
        }else {
            //打开上目录
            updateData(parentPath);
        }
    }

    private void getExit() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.stat_sys_warning)
                .setMessage("确定停止查看项目文件吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }

    // 1声明进度框对象
    ProgressDialog pd;

    // 显示一个环形进度框
    public void showProgressDialog() {
        pd = new ProgressDialog(ShowResults.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("刷新列表");
        pd.setMessage("请耐心等待");
        pd.show();
    }

    // 2声明handler对象,处理子线程结束后,UI主线程的更新
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                pd.dismiss();// 关闭进度框
            }
        }
    };

    //3.子线程
    private void updateData() {
        // 启动新线程,处理耗时操作
        new Thread() {
            public void run() {

                Utils_Results.getSearchList(currPath,KEYWORD,list);// 递归 //耗时操作


                handler.sendEmptyMessage(1);
            }
        }.start();
        showProgressDialog();// 显示进度框
    }


    public static String KEYWORD;

    @Override
    public boolean onQueryTextSubmit(String query) {

        list = new ArrayList<FileInfo_Results>();

        KEYWORD = query;
        updateData();// 搜索
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}


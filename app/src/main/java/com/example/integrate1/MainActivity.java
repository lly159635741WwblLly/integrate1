package com.example.integrate1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AMap aMap;
    MapView mMapView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        //初始化控件
        initUI();
        //获取地图控件引用
        mMapView = (MapView)findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
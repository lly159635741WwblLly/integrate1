package com.example.integrate1;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Date;


public class StartExperiment extends AppCompatActivity{


    private SensorManager sensorManager;
    private MySensorEventListener sensorEventListener;
    private TextView linearaccelerometerView;
    private TextView orientationView;
    private TextView accelerometerView;
    private TextView rotationvectorView;
    private TextView locationinfoView;
    private TextView fileView;
    private AutoCompleteTextView fileTextViewlinearacc;
    private AutoCompleteTextView fileTextViewang;
    private AutoCompleteTextView fileTextViewacc;
    private AutoCompleteTextView fileTextViewrov;
    private LocationManager locationManager;
    private String locationmessage;
    private double longitude;
    private double latitude;
    private double altitude;

    public SharedPreferences myPreferencessla;
    public SharedPreferences myPreferencessa;
    public SharedPreferences myPreferencessg;
    public SharedPreferences myPreferencessr;
    public SharedPreferences myPreferencestlaX;
    public SharedPreferences myPreferencestlaY;
    public SharedPreferences myPreferencestlaZ;
    public SharedPreferences myPreferencestaX;
    public SharedPreferences myPreferencestaY;
    public SharedPreferences myPreferencestaZ;
    public SharedPreferences myPreferencestgX;
    public SharedPreferences myPreferencestgY;
    public SharedPreferences myPreferencestgZ;
    public SharedPreferences myPreferencestrX;
    public SharedPreferences myPreferencestrY;
    public SharedPreferences myPreferencestrZ;



    private Handler handler = new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {
            if ( msg.what == 0x001 ) {
                locationinfoView.setText(locationmessage);
            }

            return false;
        }
    });
            //准备位置信息管理器
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 当GPS定位信息发生改变时，更新定位
            updateShow(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

            // 如果没权限，打开设置页面让用户自己设置
            if ( checkCallingOrSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(StartExperiment.this, "请打开GPS~", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 0);
                return;
            }
            // 当GPS LocationProvider可用时，更新定位
            updateShow(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(String provider) {
            updateShow(null);
        }
    };
    private String pro_folder_dir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_experiment);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取传感器数据实时显示框
        sensorEventListener = new MySensorEventListener();
        linearaccelerometerView = this.findViewById(R.id.linearaccelerometerView);
        orientationView = this.findViewById(R.id.gyrosopeView);
        accelerometerView = this.findViewById(R.id.accelerometerView);
        rotationvectorView = this.findViewById(R.id.rotationvectorView);
        //获取GPS数据实时显示框
        locationinfoView = findViewById(R.id.locationinfoView);
        //获取文件保存位置显示框
        fileView = this.findViewById(R.id.fileView);
        //获取文件数据显示框
        fileTextViewlinearacc = this.findViewById(R.id.fileTextViewlinearacc);
        fileTextViewang = this.findViewById(R.id.fileTextViewang);
        fileTextViewacc = this.findViewById(R.id.fileTextViewacc);
        fileTextViewrov = this.findViewById(R.id.fileTextViewrov);


    }


    //定义一个更新位置的方法
    private void updateShow(Location location) {
        if (location != null) {
        StringBuilder sb = new StringBuilder();
        sb.append("当前的位置信息：\n");
        sb.append("经度 Longitude：" + location.getLongitude() + "\n");
        sb.append("纬度 Latitude：" + location.getLatitude() + "\n");
        sb.append("高度 Altitude：" + location.getAltitude() + "\n");
        sb.append("速度 Speed：" + location.getSpeed() + "\n");
        sb.append("方向 Bearing：" + location.getBearing() + "\n");
        sb.append("定位精度 Accuracy：" + location.getAccuracy() + "\n");

        //保存下来位置信息,方便用于记录
            longitude =  location.getLongitude();
            latitude = location.getLatitude();
            altitude = location.getAltitude();
            locationmessage = sb.toString();
        } else locationmessage = "";

    handler.sendEmptyMessage(0x001);
}

    public void locationUpdate() {

        // 如果没权限，打开设置页面让用户自己设置
        if ( checkCallingOrSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(StartExperiment.this, "请打开GPS~", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        updateShow(location);

        //设置间隔获得一次 GPS 定位信息
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,mLocationListener);
    }



        //传感器部分
        //确定按钮的触发事件
        //开始记录传感器数据
    public void begin(View view){
        Toast.makeText(StartExperiment.this, "开始采集数据", Toast.LENGTH_SHORT).show();

        //SENSOR_DELAY_NORMAL  //默认的更新速率,延时：200ms
        //SENSOR_DELAY_FASTEST  //可以实现的最快更新速率,延时：0ms
        //SENSOR_DELAY_GAME  //适合控制游戏的更新速率,延时：20ms
        //SENSOR_DELAY_UI  //适合更新UI的速率,延时：60ms


        myPreferencessla = getSharedPreferences("Settings", Context.MODE_PRIVATE);//括号中第一个参数为保存的文件名；第二个参数为程序私有访问模式
        myPreferencessa = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencessg = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencessr = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestlaX = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestlaY = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestlaZ = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestaX = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestaY = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestaZ = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestgX = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestgY = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestgZ = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestrX = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestrY = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        myPreferencestrZ = getSharedPreferences("Settings", Context.MODE_PRIVATE);


        //先实例化对象，以调用非静态方法
        //注册陀螺仪传感器
        Sensor gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(sensorEventListener, gyroSensor, (int) myPreferencessg.getFloat("SampleRateSG", 0.0f));
        //注册线性加速度传感器
        Sensor linearaccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(sensorEventListener, linearaccelerometerSensor, (int) myPreferencessla.getFloat("SampleRateSLA", 0.0f));
        //注册加速度传感器
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, (int) myPreferencessa.getFloat("SampleRateSA", 0.0f));
        //注册旋转向量传感器
        Sensor rotationvectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(sensorEventListener, rotationvectorSensor, (int) myPreferencessr.getFloat("SampleRateSR", 0.0f));
        //开始更新位置数据
        locationUpdate();
    }



        //结束传感器数据记录
        //结束更新位置信息
    public void end(View view){
        Toast.makeText(StartExperiment.this, "数据采集暂停", Toast.LENGTH_SHORT).show();
        sensorManager.unregisterListener(sensorEventListener);
        locationManager.removeUpdates(mLocationListener);
    }



        //显示记录的传感器数据
        //读取指定目录的文件
    public void getFile(View view){
        fileTextViewlinearacc.setText(FileUtils.getFileContent(new File(pro_folder_dir+"linearaccel.txt")));
        fileTextViewang.setText(FileUtils.getFileContent(new File(pro_folder_dir+"gyro.txt")));
        fileTextViewacc.setText(FileUtils.getFileContent(new File(pro_folder_dir+"accel.txt")));
        fileTextViewrov.setText(FileUtils.getFileContent(new File(pro_folder_dir+"rov.txt")));

        Toast.makeText(StartExperiment.this, "已显示部分数据", Toast.LENGTH_SHORT).show();

    }


        //开始考虑怎么记录传感器数据
        private class MySensorEventListener implements SensorEventListener {
            private MySensorEventListener() {
            }

            //根据陀螺仪数据计算角度变化，基本思路如下：
            //定义一个极小的时间作为时间微元
            //获取时间微段内陀螺仪的数据，得到的是角速度
            //将数据累加得到倾角
            final float nanosecondsPerSecond = 1.0f / 1000000000.0f;//选择纳秒作为时间单位
            private long lastTime = 0;
            final float[] angle = new float[3];

            @SuppressLint("SetTextI18n")
            @Override
            public void onSensorChanged(SensorEvent event) {

                //获取时间，并转化为日常标准格式,Date()获取中国制时间，getTime（）转化为时间戳
                long timeNow = new Date().getTime();


                //得到陀螺仪的值
                if (lastTime != 0) {
                    final float dT = (event.timestamp - lastTime) * nanosecondsPerSecond;//数值变化时间

                    if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                        //如果传感器监听器监听到数值变化，将读取的数值以累加形式赋予angle[]
                        angle[0] += event.values[0] * dT;
                        angle[1] += event.values[1] * dT;
                        angle[2] += event.values[2] * dT;
                        //将得到的数值记录到文件中
                        orientationView.setText("各轴倾角值 (rad)：\n" + angle[0] + "\n " + angle[1] + "\n " + angle[2]);
                        //获得SharedPreferences中储存的项目文件夹路径字符串
                        SharedPreferences sharedPreferences = getSharedPreferences("direction", MODE_PRIVATE);
                        pro_folder_dir = sharedPreferences.getString("dir", "");
                        FileUtils.writeTxtToFile(timeNow + "   " + longitude + "   " + latitude + "   " + altitude + "   " + angle[0] + "   " + angle[1] + "   " + angle[2], pro_folder_dir, "gyro.txt");
                        fileView.setText("数据文件存储地址为：" + pro_folder_dir + "linearaccel.txt" + "  " + "gyro.txt" + "  " + "accel.txt" + "  " + "rov.txt");

                        //检测是否超过阈值
                        //如果超过阈值开始报警
                        if(Math.abs(angle[0]) > myPreferencestgX.getFloat("ThresholdGX", 0.0f)){
                            Toast.makeText(StartExperiment.this, "X轴倾角超过阈值", Toast.LENGTH_SHORT).show();
                        }
                        if(Math.abs(angle[1]) > myPreferencestgY.getFloat("ThresholdGY", 0.0f)){
                            Toast.makeText(StartExperiment.this, "Y轴倾角超过阈值", Toast.LENGTH_SHORT).show();
                        }
                        if(Math.abs(angle[2]) > myPreferencestgZ.getFloat("ThresholdGZ", 0.0f)){
                            Toast.makeText(StartExperiment.this, "Z轴倾角超过阈值", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                lastTime = event.timestamp;


                //得到线性加速度的值
                if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                    angle[0] = event.values[0];
                    angle[1] = event.values[1];
                    angle[2] = event.values[2];
                    linearaccelerometerView.setText("各轴线性加速度值 (m/s²)：\n" + angle[0] + "\n " + angle[1] + "\n " + angle[2]);
                    FileUtils.writeTxtToFile(timeNow + "   " + longitude + "   " + latitude + "   " + altitude + "   " + "   " + angle[0] + "   " + angle[1] + "   " + angle[2], pro_folder_dir, "linearaccel.txt");

                    //检测是否超过阈值
                    //如果超过阈值开始报警
                    if(Math.abs(angle[0]) > myPreferencestlaX.getFloat("ThresholdLAX", 0.0f)){
                        Toast.makeText(StartExperiment.this, "X轴线性加速度超过阈值", Toast.LENGTH_SHORT).show();
                    }
                    if(Math.abs(angle[1]) > myPreferencestlaY.getFloat("ThresholdLAY", 0.0f)){
                        Toast.makeText(StartExperiment.this, "Y轴线性加速度超过阈值", Toast.LENGTH_SHORT).show();
                    }
                    if(Math.abs(angle[2]) > myPreferencestlaZ.getFloat("ThresholdLAZ", 0.0f)){
                        Toast.makeText(StartExperiment.this, "Z轴线性加速度超过阈值", Toast.LENGTH_SHORT).show();
                    }

                }



                //得到加速度的值
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    angle[0] = event.values[0];
                    angle[1] = event.values[1];
                    angle[2] = event.values[2];
                    accelerometerView.setText("各轴加速度值 (m/s²)：\n" + angle[0] + "\n " + angle[1] + "\n " + angle[2]);
                    FileUtils.writeTxtToFile(timeNow + "   " + longitude + "   " + latitude + "   " + altitude + "   " + "   " + angle[0] + "   " + angle[1] + "   " + angle[2], pro_folder_dir, "accel.txt");

                    //检测是否超过阈值
                    //如果超过阈值开始报警
                    if(Math.abs(angle[0]) > myPreferencestaX.getFloat("ThresholdAX", 0.0f)){
                        Toast.makeText(StartExperiment.this, "X轴加速度超过阈值", Toast.LENGTH_SHORT).show();
                    }
                    if(Math.abs(angle[1]) > myPreferencestaY.getFloat("ThresholdAY", 0.0f)){
                        Toast.makeText(StartExperiment.this, "Y轴加速度超过阈值", Toast.LENGTH_SHORT).show();
                    }
                    if(Math.abs(angle[2]) > myPreferencestaZ.getFloat("ThresholdAZ", 0.0f)){
                        Toast.makeText(StartExperiment.this, "Z轴加速度超过阈值", Toast.LENGTH_SHORT).show();
                    }

                }

                //得到旋转向量的值
                if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                    angle[0] = event.values[0];
                    angle[1] = event.values[1];
                    angle[2] = event.values[2];
                    rotationvectorView.setText("各轴旋转向量值 (θ/2)：\n" + angle[0] + "\n " + angle[1] + "\n " + angle[2]);
                    FileUtils.writeTxtToFile(timeNow + "   " + longitude + "   " + latitude + "   " + altitude + "   " + "   " + angle[0] + "   " + angle[1] + "   " + angle[2], pro_folder_dir, "rov.txt");

                    //检测是否超过阈值
                    //如果超过阈值开始报警
                    if(Math.abs(angle[0]) > myPreferencestrX.getFloat("ThresholdRX", 0.0f)){
                        Toast.makeText(StartExperiment.this, "X轴旋转向量超过阈值", Toast.LENGTH_SHORT).show();
                    }
                    if(Math.abs(angle[1]) > myPreferencestrY.getFloat("ThresholdRY", 0.0f)){
                        Toast.makeText(StartExperiment.this, "Y轴旋转向量超过阈值", Toast.LENGTH_SHORT).show();
                    }
                    if(Math.abs(angle[2]) > myPreferencestrZ.getFloat("ThresholdRZ", 0.0f)){
                        Toast.makeText(StartExperiment.this, "Z轴旋转向量超过阈值", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }

        }

        //注销监听器以节约性能
        @Override
        public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
        locationManager.removeUpdates(mLocationListener);
        }
        @Override
        public void onStop(){
        super.onStop();
        sensorManager.unregisterListener(sensorEventListener);
        locationManager.removeUpdates(mLocationListener);
    }
        @Override
        public void onDestroy(){
        super.onDestroy();
        sensorManager.unregisterListener(sensorEventListener);
        locationManager.removeUpdates(mLocationListener);
    }
}


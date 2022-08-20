package com.example.integrate1;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DetectSensors extends AppCompatActivity {

    private TextView ms_sensor;
    private SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detectsensors);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        ms_sensor = (TextView) findViewById(R.id.ms_sensor);

        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sb = new StringBuilder();

        sb.append("你手机上有以下 " + allSensors.size() + " 类传感器：\n\n");
        for(Sensor s:allSensors){
            switch (s.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    sb.append( "加速度传感器(Accelerometer sensor)" + "\n");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    sb.append( "陀螺仪传感器(Gyroscope sensor)" + "\n");
                    break;
                case Sensor.TYPE_LIGHT:
                    sb.append( "光线传感器(Light sensor)" + "\n");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    sb.append( "磁场传感器(Magnetic field sensor)" + "\n");
                    break;
                case Sensor.TYPE_ORIENTATION:
                    sb.append( "方向传感器(Orientation sensor)" + "\n");
                    break;
                case Sensor.TYPE_PRESSURE:
                    sb.append( "气压传感器(Pressure sensor)" + "\n");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    sb.append( "距离传感器(Proximity sensor)" + "\n");
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    sb.append( "温度传感器(Temperature sensor)" + "\n");
                    break;
                default:
                    sb.append( "其他传感器" + "\n");
                    break;
            }
            sb.append("设备名称：" + s.getName() + "\n设备版本：" + s.getVersion() + "\n供应商："
                    + s.getVendor() +"\n精度值："+s.getResolution()+"\n最大量程："+s.getMaximumRange()+"\n耗电量："+ s.getPower()+ "\n\n");
        }
        ms_sensor.setText(sb.toString());
        Toast.makeText(this, "检测完毕，可以进行测量", Toast.LENGTH_SHORT).show();
    }
}

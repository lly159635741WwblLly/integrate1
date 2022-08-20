package com.example.integrate1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private Button button = null;
    public SharedPreferences preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        loadSettings();

        button = findViewById(R.id.savesettings);
        button.setOnClickListener(this);

    }

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.savesettings:
                    savesettings();
                    //跳转到第一个界面
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), com.example.integrate1.StartExperiment.class);//指定字节码文件
                    this.startActivity(intent);

                    break;
            }
        }

    private void savesettings() // 单击保存数据事件
    {

        SharedPreferences.Editor editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();// 获取 Sharedpreferences.Editor 对象

        //判断输入框是否为空，若为空则返回默认值，若不为空则读取输入值
        //对于采样频率，若为空则设定为50HZ
        //输入的采样频率，储存时转化为采样周期
        //对于传感器阈值，若为空则设置为1000000000
        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.sampleratelacc)).getText().toString().trim()))){editor.putFloat("SampleRateSLA", 100000);}
        else{
           editor.putFloat("SampleRateSLA", Math.round(1000000/(Float.parseFloat(((EditText) findViewById(R.id.sampleratelacc)).getText().toString().trim()))));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.samplerateacc)).getText().toString().trim()))){editor.putFloat("SampleRateSA", 100000);}
        else{
            editor.putFloat("SampleRateSA", Math.round(1000000/(Float.parseFloat(((EditText) findViewById(R.id.samplerateacc)).getText().toString().trim()))));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.samplerategyro)).getText().toString().trim()))){editor.putFloat("SampleRateSG", 100000);}
        else{
            editor.putFloat("SampleRateSG", Math.round(1000000/(Float.parseFloat(((EditText) findViewById(R.id.samplerategyro)).getText().toString().trim()))));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.sampleraterov)).getText().toString().trim()))){editor.putFloat("SampleRateSR", 100000);}
        else{
            editor.putFloat("SampleRateSR", Math.round(1000000/(Float.parseFloat(((EditText) findViewById(R.id.sampleraterov)).getText().toString().trim()))));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdlinearaccX)).getText().toString().trim()))){editor.putFloat("ThresholdLAX", 1000000000);}
        else{
            editor.putFloat("ThresholdLAX", Float.parseFloat(((EditText) findViewById(R.id.thresholdlinearaccX)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdlinearaccY)).getText().toString().trim()))){editor.putFloat("ThresholdLAY", 1000000000);}
        else{
            editor.putFloat("ThresholdLAY", Float.parseFloat(((EditText) findViewById(R.id.thresholdlinearaccY)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdlinearaccZ)).getText().toString().trim()))){editor.putFloat("ThresholdLAZ", 1000000000);}
        else{
            editor.putFloat("ThresholdLAZ", Float.parseFloat(((EditText) findViewById(R.id.thresholdlinearaccZ)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdaccX)).getText().toString().trim()))){editor.putFloat("ThresholdAX", 1000000000);}
        else{
            editor.putFloat("ThresholdAX", Float.parseFloat(((EditText) findViewById(R.id.thresholdaccX)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdaccY)).getText().toString().trim()))){editor.putFloat("ThresholdAY", 1000000000);}
        else{
            editor.putFloat("ThresholdAY", Float.parseFloat(((EditText) findViewById(R.id.thresholdaccY)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdaccZ)).getText().toString().trim()))){editor.putFloat("ThresholdAZ", 1000000000);}
        else{
            editor.putFloat("ThresholdAZ", Float.parseFloat(((EditText) findViewById(R.id.thresholdaccZ)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdgyroX)).getText().toString().trim()))){editor.putFloat("ThresholdGX", 1000000000);}
        else{
            editor.putFloat("ThresholdGX", Float.parseFloat(((EditText) findViewById(R.id.thresholdgyroX)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdgyroY)).getText().toString().trim()))){editor.putFloat("ThresholdGY", 1000000000);}
        else{
            editor.putFloat("ThresholdGY", Float.parseFloat(((EditText) findViewById(R.id.thresholdgyroY)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdgyroZ)).getText().toString().trim()))){editor.putFloat("ThresholdGZ", 1000000000);}
        else{
            editor.putFloat("ThresholdGZ", Float.parseFloat(((EditText) findViewById(R.id.thresholdgyroZ)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdrovX)).getText().toString().trim()))){editor.putFloat("ThresholdRX", 1000000000);}
        else{
            editor.putFloat("ThresholdRX", Float.parseFloat(((EditText) findViewById(R.id.thresholdrovX)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdrovY)).getText().toString().trim()))){editor.putFloat("ThresholdRY", 1000000000);}
        else{
            editor.putFloat("ThresholdRY", Float.parseFloat(((EditText) findViewById(R.id.thresholdrovY)).getText().toString().trim()));
        }

        if(TextUtils.isEmpty( (((EditText) findViewById(R.id.thresholdrovZ)).getText().toString().trim()))){editor.putFloat("ThresholdRZ", 1000000000);}
        else{
            editor.putFloat("ThresholdRZ", Float.parseFloat(((EditText) findViewById(R.id.thresholdrovZ)).getText().toString().trim()));
        }


        editor.apply(); // 上传数据
        Toast.makeText(this, "传感器设置已保存", Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("SetTextI18n")
    private void loadSettings()
    {
        preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        // 第一个参数是“键”，第二个参数是如果数据不存在默认的返回数据
        //显示时将采样周期再转化为采样频率
        float SampleRateSLA = Math.round(1000000/(preferences.getFloat("SampleRateSLA", 0.0f)));
        float SampleRateSA = Math.round(1000000/(preferences.getFloat("SampleRateSA", 0.0f)));
        float SampleRateSG = Math.round(1000000/(preferences.getFloat("SampleRateSG", 0.0f)));
        float SampleRateSR = Math.round(1000000/(preferences.getFloat("SampleRateSR", 0.0f)));

        float ThresholdLAX = preferences.getFloat("ThresholdLAX", 0.0f);
        float ThresholdLAY = preferences.getFloat("ThresholdLAY", 0.0f);
        float ThresholdLAZ = preferences.getFloat("ThresholdLAZ", 0.0f);
        float ThresholdAX = preferences.getFloat("ThresholdAX", 0.0f);
        float ThresholdAY = preferences.getFloat("ThresholdAY", 0.0f);
        float ThresholdAZ = preferences.getFloat("ThresholdAZ", 0.0f);
        float ThresholdGX = preferences.getFloat("ThresholdGX", 0.0f);
        float ThresholdGY = preferences.getFloat("ThresholdGY", 0.0f);
        float ThresholdGZ = preferences.getFloat("ThresholdGZ", 0.0f);
        float ThresholdRX = preferences.getFloat("ThresholdRX", 0.0f);
        float ThresholdRY = preferences.getFloat("ThresholdRY", 0.0f);
        float ThresholdRZ = preferences.getFloat("ThresholdRZ", 0.0f);

        //如果保存默认设置打钩了，那么加载时加载默认设置
        CheckBox checkBox = (CheckBox) findViewById(R.id.savesettingsCheckBox);

        if(checkBox.isChecked())
        {

            ((EditText) findViewById(R.id.sampleratelacc)).setText(Float.toString(SampleRateSLA));
            ((EditText) findViewById(R.id.samplerateacc)).setText(Float.toString(SampleRateSA));
            ((EditText) findViewById(R.id.samplerategyro)).setText(Float.toString(SampleRateSG));
            ((EditText) findViewById(R.id.sampleraterov)).setText(Float.toString(SampleRateSR));
            ((EditText) findViewById(R.id.thresholdlinearaccX)).setText(Float.toString(ThresholdLAX));
            ((EditText) findViewById(R.id.thresholdlinearaccY)).setText(Float.toString(ThresholdLAY));
            ((EditText) findViewById(R.id.thresholdlinearaccZ)).setText(Float.toString(ThresholdLAZ));
            ((EditText) findViewById(R.id.thresholdaccX)).setText(Float.toString(ThresholdAX));
            ((EditText) findViewById(R.id.thresholdaccY)).setText(Float.toString(ThresholdAY));
            ((EditText) findViewById(R.id.thresholdaccZ)).setText(Float.toString(ThresholdAZ));
            ((EditText) findViewById(R.id.thresholdgyroX)).setText(Float.toString(ThresholdGX));
            ((EditText) findViewById(R.id.thresholdgyroY)).setText(Float.toString(ThresholdGY));
            ((EditText) findViewById(R.id.thresholdgyroZ)).setText(Float.toString(ThresholdGZ));
            ((EditText) findViewById(R.id.thresholdrovX)).setText(Float.toString(ThresholdRX));
            ((EditText) findViewById(R.id.thresholdrovY)).setText(Float.toString(ThresholdRY));
            ((EditText) findViewById(R.id.thresholdrovZ)).setText(Float.toString(ThresholdRZ));

        }

        if(!TextUtils.isEmpty(Float.toString(SampleRateSLA))
                &&!TextUtils.isEmpty(Float.toString(SampleRateSA))
                &&!TextUtils.isEmpty(Float.toString(SampleRateSG))
                &&!TextUtils.isEmpty(Float.toString(SampleRateSR))
                &&!TextUtils.isEmpty(Float.toString(ThresholdLAX))
                &&!TextUtils.isEmpty(Float.toString(ThresholdLAY))
                &&!TextUtils.isEmpty(Float.toString(ThresholdLAZ))
                &&!TextUtils.isEmpty(Float.toString(ThresholdAX))
                &&!TextUtils.isEmpty(Float.toString(ThresholdAY))
                &&!TextUtils.isEmpty(Float.toString(ThresholdAZ))
                &&!TextUtils.isEmpty(Float.toString(ThresholdGX))
                &&!TextUtils.isEmpty(Float.toString(ThresholdGY))
                &&!TextUtils.isEmpty(Float.toString(ThresholdGZ))
                &&!TextUtils.isEmpty(Float.toString(ThresholdRX))
                &&!TextUtils.isEmpty(Float.toString(ThresholdRY))
                &&!TextUtils.isEmpty(Float.toString(ThresholdRZ)))
        {
            ((CheckBox) findViewById(R.id.savesettingsCheckBox)).setChecked(true); // 更新单选框的状态
        }
    }
}

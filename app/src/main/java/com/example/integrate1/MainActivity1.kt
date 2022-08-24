package com.example.integrate1

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import com.example.integrate1.databinding.ActivityListBinding


class MainActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photograph=findViewById<Button>(R.id.photograph)

        photograph.setOnClickListener{
            //从当前Activity跳转到SecondActivity
            val intent = Intent(this,ActivityEditPicture::class.java)
            //startActivity()方法专门用于启动Activity
            //他接受一个Intent参数
            startActivity(intent)
        }

        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            ), 1
        )
        binding.btnTakePicture.setOnClickListener {
            startActivity(CameraActivity4::class.java)

        }
    }

   private fun startActivity(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

    /*fun jump(view:View){
        val intent=Intent()
        intent.setClass(this,ActivityEditPicture::class.java)
        startActivity(intent)
    }*/




}




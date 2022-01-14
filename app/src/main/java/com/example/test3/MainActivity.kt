package com.example.test3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
//        setContentView(R.layout.activity_main)
//
//        //将当前Activity添加进Activity数组
//        ActivityCollector.addActivity(this)
//
//        //创建数据库
//        val dbHelper = MyDatabaseHelper(this,"UserStore.db",1)
//        dbHelper. writableDatabase
//
//        //设置按钮的透明度
//        val v: View = findViewById(R.id.toLogin)
//        v.background.alpha = 150
//
//        //跳转到登录页面
//        toLogin.setOnClickListener{
//            val intent = Intent(this,Login::class.java)
//            startActivity(intent)
//        }
//
//        //跳转到注册页面
//        toRegister.setOnClickListener{
//            val intent = Intent(this,Register::class.java)
//            startActivity(intent)
//        }

        setContentView(R.layout.activity_start)

        val lottie: LottieAnimationView = findViewById(R.id.lottie)
        lottie.animate().translationY(-1000F).setDuration(3000).startDelay = 4900

        Handler().postDelayed(Runnable {
            kotlin.run {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }, 5000)
    }
}
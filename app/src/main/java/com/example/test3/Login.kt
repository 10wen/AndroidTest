package com.example.test3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private val check = CheckString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        //将当前Activity添加进Activity数组
        ActivityCollector.addActivity(this)

        //利用sharePreferences实现记住账号密码
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val isRemember = prefs.getBoolean("remember_password", false) //默认未选中
        if (isRemember) {
            //将账号密码设置到文本框
            val phone = prefs.getString("phone","")
            val pwd = prefs.getString("pwd","")
            userPhone.setText(phone)
            userPwd.setText(pwd)
            rememberPass.isChecked = true
        }
        userLogin.setOnClickListener {
            val phone = userPhone.text.toString()
            val pwd = userPwd.text.toString()
            if (checkLogin(phone, pwd)) {
                if (checkUser(phone, pwd)) { //查找数据库表userTable中用户信息是否存在
                    //实现记住账号密码
                    val editor = prefs.edit()
                    if (rememberPass.isChecked) {  //再次判断是否选中记住账号密码
                        editor.putBoolean("remember_password", true)
                        editor.putString("phone",phone)
                        editor.putString("pwd",pwd)
                    } else {
                        editor.clear()  //清空sharePreferences存储的内容
                    }
                    editor.apply()

                    //输入合法，数据库表有用户信息，且账号密码正确则登录成功
                    val intent = Intent(this, FriendList::class.java)
                    intent.putExtra("userPhone",phone)//传递用户信息
                    startActivity(intent)
                    Toast.makeText(this,"登录成功！", Toast.LENGTH_LONG).show()
                    finish()
                }
            } else {
                if (!check.checkPhoneNum(phone)) {
                    userPhone.error = "请输入正确账号！"
                }
                if (!check.checkPassword(pwd)) {
                    userPwd.error = "密码错误！"
                }
            }
        }

        toRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

    }

    private fun checkUser(userPhone: String, userPwd: String): Boolean {
        //查数据库userTable表是否存在该用户
        val select = ServerForUserDB(this)
        return when (select.checkUser(userPhone, userPwd)) {
            0 -> {
//                Toast.makeText(this, "用户不存在！",Toast.LENGTH_LONG).show()
                AlertDialog.Builder(this).apply {
                    setTitle("登录失败")
                    setMessage("用户不存在！")
                    setCancelable(false)
                    setPositiveButton("前往注册") { dialog, which ->
                        val intent = Intent(this@Login, Register::class.java)
                        startActivity(intent)
                    }
                    setNegativeButton("取消") { dialog, which ->
                    }
                    show()
                }
                false
            }
            1 -> {
                Toast.makeText(this, "用户密码不正确！",Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                true
            }
        }
    }

    //先进行字符输入的合法性，合法返回true
    private fun checkLogin(userPhone: String, userPwd: String): Boolean {
        return if (!check.checkPhoneNum(userPhone)) {
            false
        } else check.checkPassword(userPwd)
    }
}


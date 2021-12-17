package com.example.test3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private val check = CheckString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val prefs = getPreferences(Context.MODE_PRIVATE)
        val isRemember = prefs.getBoolean("remember_password", false)
        if (isRemember) {
            //将账号密码设置到文本框
            val phone = prefs.getString("phone","")
            val pwd = prefs.getString("pwd","")
            userPhone.setText(phone)
            userPwd.setText(pwd)
            rememberPass.isChecked = true
        }

        toRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        userLogin.setOnClickListener {

            val phone = userPhone.text.toString()
            val pwd = userPwd.text.toString()

            if (checkLogin(phone, pwd)) {
                //查找数据库表userTable中用户信息是否存在
                if (checkUser(phone, pwd)) {
                    //实现记住账号密码
                    val editor = prefs.edit()
                    if (rememberPass.isChecked) {
                        editor.putBoolean("remember_password", true)
                        editor.putString("phone",phone)
                        editor.putString("pwd",pwd)
                    } else {
                        editor.clear()
                    }
                    editor.apply()

                    val intent = Intent(this, FriendList::class.java)
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
    }

    private fun checkUser(userPhone: String, userPwd: String): Boolean {
        //查数据库userTable表是否存在该用户
        val select = ServerForUserDB(this)
        return when (select.checkUser(userPhone, userPwd)) {
            0 -> {
                Toast.makeText(this, "用户不存在！",Toast.LENGTH_LONG).show()
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

    private fun checkLogin(userPhone: String, userPwd: String): Boolean {
        return if (!check.checkPhoneNum(userPhone)) {
            false
        } else check.checkPassword(userPwd)
    }
}


package com.example.test3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import org.intellij.lang.annotations.RegExp
import java.util.regex.Pattern

class Login : AppCompatActivity() {

    private val check = CheckString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        toRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        userLogin.setOnClickListener {

            val user_Phone = userPhone.text.toString()
            val user_pwd = userPwd.text.toString()

            if (checkLogin(user_Phone, user_pwd)) {
                val intent = Intent(this, FriendList::class.java)
                startActivity(intent)
            } else {
                if (!check.checkPhoneNum(user_Phone)) {
                    userPhone.setError("请输入正确账号！")
                }
                if (!check.checkPassword(user_pwd)) {
                    userPwd.setError("密码错误！")
                }
            }

        }


    }

    private fun checkLogin(userPhone: String, userPwd: String): Boolean {
        return if (!check.checkPhoneNum(userPhone)) {
            false
        } else check.checkPassword(userPwd)

    }
}


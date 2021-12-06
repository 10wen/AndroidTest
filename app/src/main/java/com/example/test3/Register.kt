package com.example.test3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private val check = CheckString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        userRegister.setOnClickListener{

            val user_Id = userId.text.toString()
            val user_Phone = userPhone.text.toString()
            val user_pwd = userPwd.text.toString()
            val user_pwd2 = userPwd2.text.toString()

            if (checkRegister(user_Id,user_Phone,user_pwd,user_pwd2)) {
                val intent = Intent(this, BindInfo::class.java)
                startActivity(intent)
            }else{
                if (!check.checkUserId(user_Id)){
                    userId.setError("以字母开头，长度在6~16之间，只能包含字母、数字和下划线")
                }
                if (!check.checkPhoneNum(user_Phone)){
                    userPhone.setError("请输入正确的电话号码")
                }
                if (!check.checkPassword(user_pwd)){
                    userPwd.setError("以字母开头，长度在6~16之间，只能包含字母、数字和下划线")
                }
                if (user_pwd != user_pwd2) {
                    userPwd2.setError("前后密码不一致")
                    Toast.makeText(this,"密码不一致！",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkRegister(userId: String, userPhone: String, userPwd: String, userPwd2: String): Boolean {
        return if (!check.checkUserId(userId)){
            false
        }else if (!check.checkPhoneNum(userPhone)){
            false
        }else if (!check.checkPassword(userPwd)){
            false
        }else  (userPwd == userPwd2)

    }
}
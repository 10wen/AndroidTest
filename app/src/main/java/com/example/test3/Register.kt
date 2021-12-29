package com.example.test3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
                //向数据库表userTable插入数据
                if (insertUserData(user_Id,user_Phone,user_pwd)){
                    val intent = Intent(this, BindInfo::class.java)
                    intent.putExtra("userPhone", user_Phone)
                    startActivity(intent)
                }
            }else{
                if (!check.checkUserId(user_Id)){
                    userId.error = "以字母开头，长度在6~16之间，只能包含字母、数字和下划线"
                }
                if (!check.checkPhoneNum(user_Phone)){
                    userPhone.error = "请输入正确的电话号码"
                }
                if (!check.checkPassword(user_pwd)){
                    userPwd.error = "以字母开头，长度在6~16之间，只能包含字母、数字和下划线"
                }
                if (user_pwd != user_pwd2) {
                    userPwd2.error = "前后密码不一致"
                    Toast.makeText(this,"密码不一致！",Toast.LENGTH_SHORT).show()
                }
            }
        }

        cancelRegister.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    //向数据库表userTable插入数据
    private fun insertUserData(userId: String, userPhone: String, userPwd: String): Boolean {
        val insert = ServerForUserDB(this)
        val code = insert.checkUser(userPhone,userPwd)
        return when {
            code in listOf(1,2) -> {  // 用户已经注册，提示注册失败
    //            Toast.makeText(this,"用户已存在！",Toast.LENGTH_LONG).show()
                AlertDialog.Builder(this).apply {
                    setTitle("注册失败")
                    setMessage("该用户已注册！")
                    setCancelable(false)
                    setPositiveButton("前往登录") { _, _ ->
                        val intent = Intent(this@Register, Login::class.java)
                        startActivity(intent)
                    }
                    setNegativeButton("取消") { _, _ ->
                    }
                    show()
                }
                false
            }
            insert.checkUserIdExist(userId) -> {
                //已有账号绑定该ID
                AlertDialog.Builder(this).apply {
                    setTitle("注册失败")
                    setMessage("用户名已存在！")
                    setCancelable(false)
                    setPositiveButton("确认") { _, _ ->
                        val edit: EditText = findViewById(R.id.userId)
                        edit.text.clear()
                    }
                    setNegativeButton("取消") { _, _ ->
                    }
                    show()
                }
                false
            }
            else -> {  //注册账号成功
                insert.insertData(userId,userPhone,userPwd)
                Toast.makeText(this,"注册成功！",Toast.LENGTH_LONG).show()
                true
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
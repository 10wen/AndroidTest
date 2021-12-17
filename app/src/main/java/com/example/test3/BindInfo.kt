package com.example.test3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bind_info.*

class BindInfo : AppCompatActivity() {

    private val check = CheckString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_info)
        supportActionBar?.hide()

        val cityData = listOf(
            "济南", "石家庄", "长春", "哈尔滨", "沈阳", "呼和浩特", "乌鲁木齐", "兰州", "银川",
            "太原", "西安", "郑州", "合肥", "南京", "杭州", "福州", "广州", "南昌", "海口",
            "南宁", "贵阳", "长沙", "武汉", "成都", "昆明", "拉萨", "西宁", "天津", "上海", "重庆",
            "北京", "台北"
        )
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,cityData)
        userCity.adapter = adapter

        val schoolData = listOf(
            "中山大学", "华南理工大学", "暨南大学", "华南农业大学", "南方医科大学",
            "广州中医药大学", "华南师范大学", "广东工业大学", "深圳大学", "南方科技大学"
        )
        val adapter2 = ArrayAdapter(this,android.R.layout.simple_list_item_1,schoolData)
        userSchool.setAdapter(adapter2)

        JumpToMain.setOnClickListener{
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

        var userSex = "男"
        rgbtn_boy.setOnClickListener{
            userSex = "男"
        }
        rgbtn_girl.setOnClickListener{
            userSex = "女"
        }

        bindInfo.setOnClickListener{

            val user_name = userName.text.toString()
            val user_qq = userQq.text.toString()
            val user_email = userEmail.text.toString()
            val user_sex = userSex
            val user_city = userCity.selectedItem.toString()
            val user_school = userSchool.text.toString()
            val user_address = userAddress.text.toString()

            if (checkBindInfo(user_name,user_qq,user_email)) {
                //更新数据库表userTable中用户的信息
                val userPhone = intent.getStringExtra("userPhone")
                updateUserInfo(userPhone,user_name,user_qq,user_email,
                user_sex,user_city,user_school,user_address)

                val intent = Intent(this,Login::class.java)
                startActivity(intent)
            }else{
                if (!check.checkUserName(user_name)) {
                    userName.error = "昵称错误"
                }
                if (!check.checkUserQq(user_qq)) {
                    userQq.error = "账号错误"
                }
                if (!check.checkUserEmail(user_email)) {
                    userEmail.error = "邮箱错误"
                }
            }
        }
    }

    //更新数据到userTable, 按userPhone查找
    private fun updateUserInfo(
        userPhone: String?,
        userName: String,
        userQq: String,
        userEmail: String,
        userSex: String,
        userCity: String,
        userSchool: String,
        userAddress: String
    ) {
        val update = ServerForUserDB(this)
        update.updateData(userPhone,userName,userQq,userEmail,userSex,
            userCity,userSchool,userAddress)
        Toast.makeText(this,"绑定信息成功！",Toast.LENGTH_LONG).show()
    }

    private fun checkBindInfo(userName: String, userQq: String, userEmail: String): Boolean {
        return if (!check.checkUserName(userName)) {
            false
        }else if (!check.checkUserQq(userQq)) {
            false
        }else check.checkUserEmail(userEmail)
    }
}
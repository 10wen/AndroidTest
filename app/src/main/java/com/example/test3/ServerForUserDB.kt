package com.example.test3

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context

class ServerForUserDB(val context: Context) {

    private val dbHelper = MyDatabaseHelper(context, "UserStore.db", 1)
    private val db = dbHelper.writableDatabase

    //判断userTable是否存在该用户
    @SuppressLint("Range")
    fun checkUser(phone: String, password: String) : Int {
        var code = 0
        val cursor = db.query("userTable", arrayOf("userPhone","userPwd"),
            "userPhone= ?", arrayOf(phone),null,null,null)
        if (cursor.moveToFirst()) {
            val pwd = cursor.getString(cursor.getColumnIndex("userPwd"))
            code = if (pwd == password) 2 //用户存在并且密码正确
                   else 1 //用户存在但密码不正确
            cursor.close()
        }
        return code
    }

    //register
    fun insertData(userId: String, phone: String, password: String) {
        val values = ContentValues().apply {
            put("userId", userId)
            put("userPhone", phone)
            put("userPwd", password)
            put("userName", "波吉")
//            put("userImg",null)
//            put("userQq",null)
//            put("userSex",null)
//            put("userEmail",null)
//            put("userSchool",null)
//            put("userCity",null)
//            put("userAddress",null)
        }
        db.insert("userTable", null, values)
    }

    //bind user info
    fun updateData(
        userPhone: String?,
        userName: String,
        userQq: String,
        userEmail: String,
        userSex: String,
        userCity: String,
        userSchool: String,
        userAddress: String
    ){
        val values = ContentValues().apply {
            put("userName", userName)
            put("userQq",userQq)
            put("userSex",userSex)
            put("userEmail",userEmail)
            put("userSchool",userSchool)
            put("userCity",userCity)
            put("userAddress",userAddress)
        }
        db.update("userTable", values, "userPhone= ?", arrayOf(userPhone))
    }
}
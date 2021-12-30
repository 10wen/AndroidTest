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

    //查询Id是否已有账号使用
    @SuppressLint("Range")
    fun checkUserIdExist(userId: String) : Boolean {
        val cursor = db.query("userTable", arrayOf("userId"),
            null, null,null,null,null)
        if (cursor.moveToFirst()) {
            val id = cursor.getString(cursor.getColumnIndex("userId"))
            if (id == userId) {
                return true
            }
            cursor.close()
        }
        return false
    }

    //查询QQ、Email是否已被绑定
    @SuppressLint("Range")
    fun checkEmailExist(userQq: String, userEmail: String) : Boolean {
        val cursor = db.query("userTable", arrayOf("userQq","userEmail"),
            null, null,null,null,null)
        if (cursor.moveToFirst()) {
            val email = cursor.getString(cursor.getColumnIndex("userEmail"))
            val qq = cursor.getString(cursor.getColumnIndex("userQq"))
            if (email == userEmail || qq == userQq) {
                return true
            }
            cursor.close()
        }
        return false
    }

    //查询用户ID
    @SuppressLint("Range")
    fun selectUserId(userPhone: String) : String?{
        val cursor = db.query("userTable", arrayOf("userId"),
            "userPhone= ?", arrayOf(userPhone),null,null,null)
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("userId"))
            cursor.close()
        }
        return null
    }

    //查询用户Email
    @SuppressLint("Range")
    fun selectUserEmail(userPhone: String) : String?{
        val cursor = db.query("userTable", arrayOf("userEmail"),
            "userPhone= ?", arrayOf(userPhone),null,null,null)
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("userEmail"))
            cursor.close()
        }
        return null
    }
}
package com.example.test3

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper (private val context: Context, name: String, version: Int):
        SQLiteOpenHelper(context, name, null, version){

    private val createUser = "create table userTable (" +
            "userId varchar(20)," +
            "userPhone varchar(11) primary key," +
            "userPwd varchar(20)," +
            "userName varchar(20)," +
            "userQq varchar(11)," +
            "userSex varchar(2)," +
            "userEmail varchar(50)," +
            "userSchool varchar(20)," +
            "userCity varchar(20)," +
            "userAddress varchar(100)" +
            ")"
//    private val createFriend = "create table friendTable(" +
//            "friPhone varchar(12) primary key," +
//            "friName text," +
//            "friImg blob," +
//            "userPhone varchar(12)," +
//            "foreign key(userPhone) references userTable(userPhone)" +
//            ")"
//    private val createLogin = "create table loginStore(" +


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createUser)
//        db.execSQL(createFriend)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists userTable")
//        db.execSQL("drop table if exists friendTable")
        onCreate(db)
    }
}
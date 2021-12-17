package com.example.test3

import java.util.regex.Pattern

class CheckString {

    fun checkUserId(userId: String): Boolean {
        val regExp = "^[a-zA-Z]\\w{4,15}\$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(userId)
        return m.matches()
    }

    fun checkPhoneNum(num: String): Boolean {
        val regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5-9])|(166)|(19[8,9])|)\\d{8}$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(num)
        return m.matches()
    }

    fun checkPassword(userPwd: String): Boolean {
        val regExp = "^[a-zA-Z]\\w{5,15}\$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(userPwd)
        return m.matches()
    }

    fun checkUserName(userName: String): Boolean {
        val regExp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]{1,15}\$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(userName)
        return m.matches()
    }

    fun checkUserQq(userQq: String): Boolean {
        val regExp = "^[1-9][0-9]{4,11}\$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(userQq)
        return m.matches()
    }

    fun checkUserEmail(userEmail: String): Boolean {
        val regExp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(userEmail)
        return m.matches()
    }


}
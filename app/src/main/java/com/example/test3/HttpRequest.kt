package com.example.test3

import okhttp3.OkHttpClient
import okhttp3.Request


object HttpRequest {

    //OkHttp发起网络请求，回调机制
    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(callback) //这里开启子线程，结果返回到Callback的onResponse方法
    }
//    调用方法：
//    HttpUtil.sendOkHttpRequest(address, object: Callback {
//        override fun onResponse(call: Call, response: Response) {
//            //得到服务器返回的具体内容
//            val responseData = response.body?.string()
//        }
//
//        override fun onFailure(call: Call, e: IOException) {
//            //在这里对异常情况进行处理
//        }
//    })


}
package com.example.test3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_web_view)

        ActivityCollector.addActivity(this)

        retToFriend.setOnClickListener{
            val intent = Intent(this, FriendList::class.java)
            startActivity(intent)
        }

        load.setOnClickListener{
            val url = httpUrl.text.toString()
//            val url = "https://www.baidu.com"
            webView.settings.javaScriptEnabled = true //使支持JavaScript脚本
            webView.webViewClient = WebViewClient() //使在当前WebView显示网页，不打开系统浏览器
            webView.loadUrl(url)
        }
    }
}
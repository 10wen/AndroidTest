package com.example.test3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        supportActionBar?.hide()

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        val urlData = intent.getStringArrayExtra("urlData")
        webView.loadUrl(urlData.toString())

        retToMoreList.setOnClickListener{
            val intent = Intent(this, MoreList::class.java)
            startActivity(intent)
        }
    }
}
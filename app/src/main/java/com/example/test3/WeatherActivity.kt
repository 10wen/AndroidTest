package com.example.test3

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_weather.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        supportActionBar?.hide()

        ActivityCollector.addActivity(this)

        retToFriList.setOnClickListener{
            val intent = Intent(this, FriendList::class.java)
            startActivity(intent)
        }

        val address = "http://wthrcdn.etouch.cn/weather_mini?city=%E4%BD%9B%E5%B1%B1"
        HttpRequest.sendOkHttpRequest(address, object: Callback {
            override fun onResponse(call: Call, response: Response) {
                //得到服务器返回的具体内容
                val responseData = response.body?.string()
                parseJSONWithJSONObject(responseData)
            }
            override fun onFailure(call: Call, e: IOException) {
                //在这里对异常情况进行处理
                e.printStackTrace()
            }
        })

    }


    private fun parseJSONWithJSONObject(jsonData: String?) {
        try {
            val jsonObject = JSONObject(jsonData)

            val dataObject = jsonObject.getJSONObject("data")
            val forecastString = dataObject.getString("forecast")
            val forecastArrayObject = JSONArray(forecastString)
            val todayObject = forecastArrayObject.getJSONObject(0)
            val tomorrowObject = forecastArrayObject.getJSONObject(1)

            val day_today = todayObject.getString("date")
            val today_low = todayObject.getString("low")
            val today_high = todayObject.getString("high")
            val day_tomorrow = todayObject.getString("date")
            val tomorrow_low = tomorrowObject.getString("low")
            val tomorrow_high = tomorrowObject.getString("high")

            val ganmaoTip = dataObject.getString("ganmao")
            val wendu = dataObject.getString("wendu")


            runOnUiThread {
                today.text = day_today
                todayLow.text = today_low
                todayHigh.text = today_high
                tomorrow.text = day_tomorrow
                tomorrowLow.text = tomorrow_low
                tomorrowHigh.text = tomorrow_high

                tip.text = ganmaoTip
                wenDu.text = "$wendu ℃"
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
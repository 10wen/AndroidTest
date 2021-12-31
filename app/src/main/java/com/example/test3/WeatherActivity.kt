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

        //利用OkHttp回调的实现方式网络请求
        val address = "http://wthrcdn.etouch.cn/weather_mini?city=%E4%BD%9B%E5%B1%B1"
        HttpRequest.sendOkHttpRequest(address, object: Callback {
            override fun onResponse(call: Call, response: Response) {
                //得到服务器返回的具体内容
                val responseData = response.body?.string()
                parseJSONWithJSONObject(responseData)  //JSONObject解析json数据
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
            val todayObject = forecastArrayObject.getJSONObject(0)  //获取今天数据对象
            val tomorrowObject = forecastArrayObject.getJSONObject(1) //获取明天数据对象

            val day_today = todayObject.getString("date") //时间
            val today_low = todayObject.getString("low") //最低温度
            val today_high = todayObject.getString("high") //最高温度
            val day_tomorrow = todayObject.getString("date")
            val tomorrow_low = tomorrowObject.getString("low")
            val tomorrow_high = tomorrowObject.getString("high")

            val ganmaoTip = dataObject.getString("ganmao")  //提示语
            val wendu = dataObject.getString("wendu")  //当前气温

            //切换到主线程进行UI操作
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
package com.example.test3

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_friend_list.*
import kotlinx.android.synthetic.main.activity_friend_list.weatherBtn
import kotlinx.android.synthetic.main.nav_header.*

class FriendList : AppCompatActivity() {

    private val userList = ArrayList<Friend>()
    private val content: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)
        supportActionBar?.hide()

        ActivityCollector.addActivity(this)

        // 左滑动菜单的读取数据库中用户Id与邮箱
        val userPhone = intent.getStringExtra("userPhone")
        val select = ServerForUserDB(this)
        val userId = select.selectUserId(userPhone!!)
        val userEmail = select.selectUserEmail(userPhone!!)

        //获取滑动菜单TextView的id号
        val navigationView = findViewById<NavigationView>(R.id.navView)
        val headerLayout = navigationView.getHeaderView(0)
        val userIdText = headerLayout.findViewById<TextView>(R.id.userText)
        val userMailText = headerLayout.findViewById<TextView>(R.id.mailText)
        userIdText.text = userId
        userMailText.text = userEmail

        // 打开滑动窗菜单
        drawer.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }

        //事件处理
        navView.setCheckedItem(R.id.navCall)  //默认选中打电话按钮
        navView.setNavigationItemSelectedListener {
//            Log.d("MenuItem:","$it")
            when (it.itemId) { // 获取事件 id
                R.id.navCall -> makeCallFun()
                R.id.navFriend -> openFriendBook()
                R.id.navLoad -> startWebActivity()
                R.id.navExit -> ActivityCollector.finishAll()
                else -> Toast.makeText(content,"You clicked $it",
                                            Toast.LENGTH_SHORT).show()
            }

            drawerLayout.closeDrawers()  //关闭滑窗
            true
        }

        initUsers()
        val layoutManager = LinearLayoutManager(this)
        friendRecycleView.layoutManager = layoutManager
        val adapter = FriendAdapter(userList)
        friendRecycleView.adapter = adapter

        //实现RecyclerView事件监听接口
        adapter.setMyOnClickListener(object : FriendAdapter.MyOnClickListener{
            override fun clickListener(position: Int) {
                Toast.makeText(content, "You click ${userList[position].Name}.",
                    Toast.LENGTH_SHORT).show()
                val intent = Intent(content,Message::class.java)
                intent.putExtra("friendName", userList[position].Name)
                startActivity(intent)
            }
        })

        //跳转天气预报Activity
        weatherBtn.setOnClickListener{
            Toast.makeText(this, "天气预报.",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }
    }

    //跳转网页请求Activity
    private fun startWebActivity() {
        val intent = Intent(this, WebViewActivity::class.java)
        startActivity(intent)
    }

    //打开通讯录
    private fun openFriendBook() {
        val intent = Intent(this, FriendBook::class.java)
        startActivity(intent)
    }

    //-------- 打电话 -------
    private fun makeCallFun() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CALL_PHONE), 1)
        } else {
            call()
        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call()
                } else {
                    Toast.makeText(this, "You denied the permission",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 初始化好友列表
    private fun initUsers() {
        userList.add(Friend("dewen","19215120433", R.drawable.img_1))
        userList.add(Friend("杰","19215120436", R.drawable.img_2))
        userList.add(Friend("帅","19215120437", R.drawable.img_3))
        userList.add(Friend("鹏","19215120438", R.drawable.img_4))
        userList.add(Friend("1","19215120439", R.drawable.img_5))
        userList.add(Friend("9","19215120440", R.drawable.img_6))
        userList.add(Friend("2","19215120441", R.drawable.img_7))
        userList.add(Friend("1","19215120442", R.drawable.img_8))
        userList.add(Friend("5","19215120443", R.drawable.img_9))
        userList.add(Friend("1","19215120444", R.drawable.img_10))
        userList.add(Friend("2","19215120445", R.drawable.img_11))
        userList.add(Friend("0","19215120446", R.drawable.img_12))
        userList.add(Friend("4","19215120447", R.drawable.img_13))
        userList.add(Friend("3","19215120448", R.drawable.img_14))
        userList.add(Friend("3","19215120449", R.drawable.img_15))
//        userList.add(Friend("friend","19215120450", R.drawable.img_16))
//        userList.add(Friend("friend","19215120452", R.drawable.img_18))
//        userList.add(Friend("friend","19215120453", R.drawable.img_19))
//        userList.add(Friend("friend","19215120454", R.drawable.img_20))
//        userList.add(Friend("friend","19215120455", R.drawable.img_21))
//        userList.add(Friend("friend","19215120456", R.drawable.img_22))
//        userList.add(Friend("friend","19215120457", R.drawable.img_23))
//        userList.add(Friend("friend","19215120458", R.drawable.img_24))
//        userList.add(Friend("friend","19215120459", R.drawable.img_25))
//        userList.add(Friend("friend","19215120460", R.drawable.img_26))
//        userList.add(Friend("friend","19215120461", R.drawable.img_27))
//        userList.add(Friend("friend","19215120462", R.drawable.img_28))
//        userList.add(Friend("friend","19215120463", R.drawable.img_29))
//        userList.add(Friend("friend","19215120464", R.drawable.img_30))
    }
}
package com.example.test3

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_more_list.*
import java.util.jar.Manifest

class MoreList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_list)
        supportActionBar?.hide()

        retToFriList.setOnClickListener{
            val intent = Intent(this, FriendList::class.java)
            startActivity(intent)
        }

        makeCall.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            } else {
                call()
            }
        }

        makeWeb.setOnClickListener{
            val url = webUrl.text.toString()
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("urlData", url)
            startActivity(intent)
        }


        phoneList.setOnClickListener{
            val intent = Intent(this, FriendBook::class.java)
            startActivity(intent)
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

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + callPhone.text.toString())
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
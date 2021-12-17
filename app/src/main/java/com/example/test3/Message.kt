package com.example.test3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_friend_list.*
import kotlinx.android.synthetic.main.activity_message.*

class Message : AppCompatActivity(), View.OnClickListener {

    private val messageList = ArrayList<Msg>()
    private var adapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        supportActionBar?.hide()

        retToFriList.setOnClickListener{
            val intent = Intent(this, FriendList::class.java)
            startActivity(intent)
        }

        val friend = intent.getStringExtra("friendName")
        friendName.setText(friend)

        initMessage()
        val layoutManager = LinearLayoutManager(this)
        messageRecycleView.layoutManager = layoutManager
        adapter = MessageAdapter(messageList)
        messageRecycleView.adapter = adapter
        send.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when(p0) {
            send -> {
                val content = inputText.text.toString()
                if (content.isNotEmpty()) {
                    val msg = Msg(content,Msg.TYPE_SEND)
                    messageList.add(msg)
                    adapter?.notifyItemInserted(messageList.size - 1)
                    messageRecycleView.scrollToPosition(messageList.size - 1)
                    inputText.setText("")
                }
            }

        }
    }

    private fun initMessage() {
        val msg1 = Msg("Hello guy.", Msg.TYPE_RECEIVED)
        messageList.add(msg1)
        val msg2 = Msg("Hello. Who is that?",Msg.TYPE_SEND)
        messageList.add(msg2)
        val msg3 = Msg("This is DeWen. Nice talking to you. ",Msg.TYPE_RECEIVED)
        messageList.add(msg3)
    }
}
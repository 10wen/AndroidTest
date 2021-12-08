package com.example.test3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class UserAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    private var itemClick: MyOnClickListener? = null
//    private var userList: List<User>? = null
//    private var context: Context? = null

//    constructor(userList: List<User>, context: Context) : this() {
//        this.userList = userList
//        this.context = context
//    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userImage: ImageView = view.findViewById(R.id.userImg)
        val userName: TextView = view.findViewById(R.id.userName)
        val userPhone: TextView = view.findViewById(R.id.userPhone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item, parent, false)
        return ViewHolder(view)
    }
        //注册点击事件
//        val viewHolder = ViewHolder(view)
//        viewHolder.itemView.setOnClickListener{
//            val position = viewHolder.adapterPosition
//            val user = userList?.get(position)
//            if (user != null) {
//                Toast.makeText(parent.context,"view ${user.Name}",Toast.LENGTH_SHORT).show()
//            }
//
//            itemClick?.clickListener(position)
//
//        }
//        viewHolder.userImage.setOnClickListener{
//            val position = viewHolder.adapterPosition
//            val user = userList?.get(position)
//            if (user != null) {
//                Toast.makeText(parent.context,"image ${user.Name}",Toast.LENGTH_SHORT).show()
//            }
//        }
//        viewHolder.userPhone.setOnClickListener{
//            val position = viewHolder.adapterPosition
//            val user = userList?.get(position)
//            if (user != null) {
//                Toast.makeText(parent.context,"Phone ${user.Name}",Toast.LENGTH_SHORT).show()
//            }
////        }
//
//        return viewHolder
//    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.userImage.setImageResource(user.ImgId)
        holder.userName.text = user.Name
        holder.userPhone.text = user.Phone

        holder.itemView.setOnClickListener{
            itemClick?.clickListener(position)
        }
    }

    override fun getItemCount() = userList.size

//    提供set方法
    fun setMyOnClickListener(itemClick: MyOnClickListener) {
        this.itemClick = itemClick
    }

//    自定义接口
    interface MyOnClickListener {
        fun clickListener (position: Int)
    }
}
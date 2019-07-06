package com.baddelni.baddelni.chat

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.chat.response.chatUsersResponse.Data
import com.bumptech.glide.Glide

class ChatUsersAdapter(val list: List<Data>) : RecyclerView.Adapter<ChatUsersAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(vh: viewHolder, postion: Int) {
        val data = list[postion]

        Glide.with(vh.image)
                .load(data.receiverData.img)
                .into(vh.image)

        vh.name.text = data.receiverData.name
        vh.message.text = data.product.name



        vh.itemView.setOnClickListener {
            val intent = Intent(vh.itemView.context, ChatActivity::class.java)
            intent.putExtra("roomId", data.chatId.toString())
            vh.itemView.context.startActivity(intent)

        }
    }


    class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val name = view.findViewById<TextView>(R.id.name)
        val message = view.findViewById<TextView>(R.id.message)
    }
}
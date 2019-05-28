package com.baddelni.baddelni.chat

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.baddelni.baddelni.R
import com.baddelni.baddelni.chat.viewHolders.ReceivedTextMessageVH
import com.baddelni.baddelni.chat.viewHolders.SendTextMessageVH
import com.baddelni.baddelni.databinding.ItemMessageRecivedBinding
import com.baddelni.baddelni.databinding.ItemMessageSentBinding

class ChatAdapter(val context: Context, val messages: List<TextMessage>, val userId: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val SEND_TEXT_MESSAGE = 1;
    val RECIVED_TEXT_MESSAGE = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == SEND_TEXT_MESSAGE) {

            val binding: ItemMessageSentBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(context),
                            R.layout.item_message_sent, parent, false)

            return SendTextMessageVH(binding)

        } else {

            val binding: ItemMessageRecivedBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(context),
                            R.layout.item_message_recived, parent, false)

            return ReceivedTextMessageVH(binding)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].userId.toString() == userId) SEND_TEXT_MESSAGE else RECIVED_TEXT_MESSAGE
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        if (viewHolder is SendTextMessageVH) {
            viewHolder.b.message.text = message.message
            viewHolder.b.time.text = message.time.toString()

        } else if (viewHolder is ReceivedTextMessageVH) {
            viewHolder.b.message.text = message.message
            viewHolder.b.time.text = message.time.toString()
        }

    }
}
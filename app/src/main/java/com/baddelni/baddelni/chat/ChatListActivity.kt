package com.baddelni.baddelni.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.chat.response.chatUsersResponse.ChatUserResponse
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_chat_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatListActivity : AppCompatActivity() {

    val co by lazy { CommonObjects(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)


        getChatUsers()
    }

    private fun getChatUsers() {

        co.showLoading()
        Api.getApi().getChatUserList(co.getStringPrams()).enqueue(object : Callback<ChatUserResponse> {
            override fun onResponse(call: Call<ChatUserResponse>, response: Response<ChatUserResponse>) {
                co.hideLoading()
                val body = response.body()

                if (!body?.data.isNullOrEmpty()) {
                    chatListRecycler.adapter = ChatUsersAdapter(body?.data!!)
                }
            }

            override fun onFailure(call: Call<ChatUserResponse>, t: Throwable) {
                co.hideLoading()
                t.printStackTrace()
            }

        })

    }
}

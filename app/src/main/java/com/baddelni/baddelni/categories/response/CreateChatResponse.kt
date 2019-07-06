package com.baddelni.baddelni.categories.response


import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class CreateChatResponse(
        @SerializedName("chat_id")
        val chatId: Int,
        @SerializedName("code")
        val code: NetworkCode,
        @SerializedName("msg")
        val msg: String
)
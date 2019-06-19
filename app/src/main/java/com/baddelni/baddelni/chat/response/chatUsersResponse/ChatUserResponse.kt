package com.baddelni.baddelni.chat.response.chatUsersResponse


import com.google.gson.annotations.SerializedName

data class ChatUserResponse(
        @SerializedName("data")
        val `data`: List<Data>,
        @SerializedName("code")
        val code: String
)
package com.baddelni.baddelni.chat

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class TextMessage @JvmOverloads constructor(

        @field:SerializedName("messageType")
        val messageType: String = "",

        @field:SerializedName("message")
        val message: String = "",

        @field:SerializedName("userId")
        val userId: Int = 0,

        @field:SerializedName("time")
        val time: Long = 0
)
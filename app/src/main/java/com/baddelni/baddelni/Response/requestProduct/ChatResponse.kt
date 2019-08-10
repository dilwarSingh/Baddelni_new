package com.baddelni.baddelni.Response.requestProduct

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChatResponse(
        @SerializedName("Chat_id")
        val chatId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("product_id")
        val productId: Int,
        @SerializedName("receiver_id")
        val receiverId: Int,
        @SerializedName("sender_id")
        val senderId: Int,
        @SerializedName("updated_at")
        val updatedAt: String
) : Serializable
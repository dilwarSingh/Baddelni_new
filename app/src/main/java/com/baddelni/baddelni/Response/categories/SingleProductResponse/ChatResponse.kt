package com.baddelni.baddelni.Response.categories.SingleProductResponse


import com.google.gson.annotations.SerializedName

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
)
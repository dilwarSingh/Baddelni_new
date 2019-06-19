package com.baddelni.baddelni.chat.response.chatUsersResponse


import com.google.gson.annotations.SerializedName

data class Data(
        @SerializedName("Chat_id")
        val chatId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("product")
        val product: Product,
        @SerializedName("product_id")
        val productId: Int,
        @SerializedName("receiver_data")
        val receiverData: ReceiverData,
        @SerializedName("receiver_id")
        val receiverId: Int,
        @SerializedName("sender_id")
        val senderId: Int,
        @SerializedName("updated_at")
        val updatedAt: String
)
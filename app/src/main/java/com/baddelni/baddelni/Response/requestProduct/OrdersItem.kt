package com.baddelni.baddelni.Response.requestProduct

import com.google.gson.annotations.SerializedName

data class OrdersItem(

        @field:SerializedName("image")
        val image: String? = null,

        @field:SerializedName("chat")
        val chat: ChatResponse? = null,

        @field:SerializedName("img")
        val img: String? = null,

        @field:SerializedName("product2")
        val product2: Product2? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("product1")
        val product1: Product1? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("category_id")
        val categoryId: Int? = null,

        @field:SerializedName("user_id")
        val userId: Int? = null,

        @field:SerializedName("product_id")
        val productId: Int? = null,

        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("id")
        val orderId: Int? = null,

        @field:SerializedName("status_text")
        val status_text: String? = null,

        @field:SerializedName("status")
        val status: Int? = null
)
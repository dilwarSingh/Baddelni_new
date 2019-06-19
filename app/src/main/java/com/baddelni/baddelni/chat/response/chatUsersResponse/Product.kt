package com.baddelni.baddelni.chat.response.chatUsersResponse


import com.google.gson.annotations.SerializedName

data class Product(
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("country_id")
        val countryId: Any,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("exchange_type")
        val exchangeType: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("is_special")
        val isSpecial: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("price")
        val price: Any,
        @SerializedName("published_at")
        val publishedAt: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("user_id")
        val userId: Int
)
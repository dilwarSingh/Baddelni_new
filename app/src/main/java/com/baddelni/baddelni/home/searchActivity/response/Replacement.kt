package com.baddelni.baddelni.home.searchActivity.response


import com.google.gson.annotations.SerializedName

data class Replacement(
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("img")
        val img: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("product_id")
        val productId: Int,
        @SerializedName("updated_at")
        val updatedAt: String
)
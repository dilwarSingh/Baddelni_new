package com.baddelni.baddelni.Response.sellingItems


import com.google.gson.annotations.SerializedName

data class MainImage(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("img")
        val img: String,
        @SerializedName("product_id")
        val productId: Int,
        @SerializedName("role")
        val role: Int,
        @SerializedName("updated_at")
        val updatedAt: String
)
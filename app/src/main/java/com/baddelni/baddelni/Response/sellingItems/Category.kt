package com.baddelni.baddelni.Response.sellingItems


import com.google.gson.annotations.SerializedName

data class Category(
        @SerializedName("category")
        val category: String,
        @SerializedName("category_ar")
        val categoryAr: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("img")
        val img: String,
        @SerializedName("updated_at")
        val updatedAt: String
)
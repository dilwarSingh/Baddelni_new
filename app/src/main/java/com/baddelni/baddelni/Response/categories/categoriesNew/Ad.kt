package com.baddelni.baddelni.Response.categories.categoriesNew


import com.google.gson.annotations.SerializedName

data class Ad(
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("end_date")
        val endDate: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("img")
        val img: String,
        @SerializedName("link")
        val link: String,
        @SerializedName("start_date")
        val startDate: String,
        @SerializedName("updated_at")
        val updatedAt: String
)
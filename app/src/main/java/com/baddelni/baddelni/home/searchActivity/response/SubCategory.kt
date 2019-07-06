package com.baddelni.baddelni.home.searchActivity.response


import com.google.gson.annotations.SerializedName

data class SubCategory(
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("img")
        val img: String,
        @SerializedName("pivot")
        val pivot: Pivot,
        @SerializedName("sub_category")
        val subCategory: String,
        @SerializedName("sub_category_ar")
        val subCategoryAr: String,
        @SerializedName("updated_at")
        val updatedAt: String
)
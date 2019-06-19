package com.baddelni.baddelni.Response.sellingItems


import com.google.gson.annotations.SerializedName

data class Pivot(
        @SerializedName("product_id")
        val productId: Int,
        @SerializedName("sub_category_id")
        val subCategoryId: Int
)
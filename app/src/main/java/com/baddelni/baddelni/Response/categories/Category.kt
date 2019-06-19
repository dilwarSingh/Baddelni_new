package com.baddelni.baddelni.Response.categories

import com.baddelni.baddelni.Response.categories.categoriesNew.CategoriesItem
import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class Category(

        @field:SerializedName("code")
        val code: NetworkCode? = null,

        @field:SerializedName("category_image_path")
        val categoryImagePath: String? = null,

        @field:SerializedName("categories")
        val categories: MutableList<CategoriesItem>? = null
)
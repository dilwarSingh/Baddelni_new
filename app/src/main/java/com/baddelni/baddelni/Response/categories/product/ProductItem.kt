package com.baddelni.baddelni.Response.categories.product

import com.baddelni.baddelni.util.FavoriteCode
import com.google.gson.annotations.SerializedName

data class ProductItem(

        @field:SerializedName("category_id")
        val categoryId: Int? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("user_id")
        val userId: Int? = null,

        @field:SerializedName("main_image")
        val mainImage: MainImage? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("fav")
        var fav: FavoriteCode = FavoriteCode.NotFavorite,


        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("status")
        val status: Int? = null,

        @field:SerializedName("sub_categories")
        val sub_categories: List<SubCategoriesRes>? = null
)
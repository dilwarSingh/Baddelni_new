package com.baddelni.baddelni.Response.sellingItems


import com.baddelni.baddelni.util.FavoriteCode
import com.google.gson.annotations.SerializedName

data class Data(
        @SerializedName("category")
        val category: Category,
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("country_id")
        val countryId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("exchange_type")
        val exchangeType: String,
        @SerializedName("fav")
        var fav: FavoriteCode = FavoriteCode.NotFavorite,
        @SerializedName("id")
        val id: Int,
        @SerializedName("is_special")
        val isSpecial: String,
        @SerializedName("main_image")
        val mainImage: MainImage,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("price")
        val price: Any,
        @SerializedName("published_at")
        val publishedAt: String,
        @SerializedName("replacements")
        val replacements: List<Any>,
        @SerializedName("status")
        val status: Int,
        @SerializedName("sub_categories")
        val subCategories: List<SubCategory>,
        @SerializedName("sub_images")
        val subImages: List<Any>,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("user")
        val user: User,
        @SerializedName("user_id")
        val userId: Int
)
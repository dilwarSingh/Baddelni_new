package com.baddelni.baddelni.Response.categories.SingleProductResponse

import com.baddelni.baddelni.Response.Countries.CountriesItem
import com.google.gson.annotations.SerializedName

data class Product(

        @field:SerializedName("main_image")
        val mainImage: MainImage? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("price")
        val price: String? = null,

        @field:SerializedName("sub_categories")
        val subCategories: List<SubCategoriesItem?>? = null,

        @field:SerializedName("category_id")
        val categoryId: Int? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("user_id")
        val userId: Int? = null,

        @field:SerializedName("sub_images")
        val subImages: List<ReplacementsItem>? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("country")
        val country: CountriesItem? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("is_special")
        val is_special: Int = 0,

        @field:SerializedName("exchange_type")
        val exchange_type: Int = 0,

        @field:SerializedName("replacements")
        val replacements: List<ReplacementsItem>? = null,

        @field:SerializedName("category")
        val category: Category? = null,

        @field:SerializedName("country_id")
        val country_id: Int? = null,

        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("phone")
        val phone: String? = null,


        @field:SerializedName("status")
        val status: Int? = null
)
package com.baddelni.baddelni.Response.home

import com.google.gson.annotations.SerializedName

data class CategoriesItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("sub_category")
	val subCategory: List<SubCategoryItem?>? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("category_ar")
	val categoryAr: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: String? = null
)
package com.baddelni.baddelni.Response.categories.product

import com.google.gson.annotations.SerializedName

data class Category(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("product")
	val product: List<ProductItem>? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("category_ar")
	val categoryAr: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: String? = null
)
package com.baddelni.baddelni.Response.categories.product

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class CategoryProducts(

	@field:SerializedName("code")
	val code: NetworkCode? = null,

	@field:SerializedName("category_image_path")
	val categoryImagePath: String? = null,

	@field:SerializedName("product_image_path")
	val productImagePath: String? = null,

	@field:SerializedName("category")
	val category: Category? = null
)
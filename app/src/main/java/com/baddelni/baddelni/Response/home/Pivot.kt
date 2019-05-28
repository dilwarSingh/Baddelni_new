package com.baddelni.baddelni.Response.home

import com.google.gson.annotations.SerializedName

data class Pivot(

	@field:SerializedName("sub_category_id")
	val subCategoryId: Int? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null
)
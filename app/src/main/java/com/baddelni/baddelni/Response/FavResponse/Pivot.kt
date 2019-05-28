package com.baddelni.baddelni.Response.FavResponse

import com.google.gson.annotations.SerializedName

data class Pivot(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("sub_category_id")
	val subCategoryId: Int? = null
)
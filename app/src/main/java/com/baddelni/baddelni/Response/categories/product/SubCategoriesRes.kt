package com.baddelni.baddelni.Response.categories.product

import com.google.gson.annotations.SerializedName

data class SubCategoriesRes(

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("sub_category")
	val subCategory: String? = null,

	@field:SerializedName("sub_category_ar")
	val subCategoryAr: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("pivot")
	val pivot: Pivot? = null,

	@field:SerializedName("id")
	val subCategoryId: Int? = null
)
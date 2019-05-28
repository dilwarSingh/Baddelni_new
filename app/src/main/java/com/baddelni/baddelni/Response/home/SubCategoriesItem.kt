package com.baddelni.baddelni.Response.home

import com.google.gson.annotations.SerializedName

data class SubCategoriesItem(

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
	val id: Int? = null
)
package com.baddelni.baddelni.Response.FavResponse

import com.google.gson.annotations.SerializedName

data class DataItem(

	@field:SerializedName("main_image")
	val mainImage: MainImage? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("sub_categories")
	val subCategories: List<SubCategoriesItem?>? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("sub_images")
	val subImages: List<Any?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("pivot")
	val pivot: Pivot? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("replacements")
	val replacements: List<ReplacementsItem?>? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
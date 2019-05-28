package com.baddelni.baddelni.Response.home

import com.google.gson.annotations.SerializedName

data class SlidesItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("title_ar")
	val titleAr: String? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("country_id")
	val countryId: Int? = null,

	@field:SerializedName("description_ar")
	val descriptionAr: String? = null
)
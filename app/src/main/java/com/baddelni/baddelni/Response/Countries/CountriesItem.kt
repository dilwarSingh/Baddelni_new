package com.baddelni.baddelni.Response.Countries

import com.google.gson.annotations.SerializedName

data class CountriesItem(

	@field:SerializedName("country_ar")
	val countryAr: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
package com.baddelni.baddelni.Response.loginResponse

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("country")
	val country: Any? = null,

	@field:SerializedName("role")
	val role: Int? = null,

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("active_code")
	val activeCode: Any? = null,

	@field:SerializedName("goggle_id")
	val goggleId: Any? = null,

	@field:SerializedName("active")
	val active: Int? = null,

	@field:SerializedName("available_product")
	val availableProduct: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: Any? = null,

	@field:SerializedName("facebook_id")
	val facebookId: Any? = null,

	@field:SerializedName("available_time")
	val availableTime: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: List<Any?>? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("country_id")
	val countryId: Int? = null
)
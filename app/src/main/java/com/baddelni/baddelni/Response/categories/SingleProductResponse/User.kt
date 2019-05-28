package com.baddelni.baddelni.Response.categories.SingleProductResponse

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("google_id")
	val googleId: Any? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("role")
	val role: Int? = null,



	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("active_code")
	val activeCode: Any? = null,

	@field:SerializedName("active")
	val active: Int? = null,

	@field:SerializedName("available_product")
	val availableProduct: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: String? = null,

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

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("country_id")
	val countryId: Int? = null
)
package com.baddelni.baddelni.Response

import com.google.gson.annotations.SerializedName

data class Setting(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("advice")
	val advice: String? = null,

	@field:SerializedName("facebook")
	val facebook: String? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("privacy")
	val privacy: String? = null,

	@field:SerializedName("help")
	val help: String? = null,

	@field:SerializedName("instagram")
	val instagram: String? = null,

	@field:SerializedName("linkedin")
	val linkedin: String? = null,

	@field:SerializedName("snapchat")
	val snapchat: String? = null,

	@field:SerializedName("twitter")
	val twitter: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("terms")
	val terms: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("advertise")
	val advertise: String? = null
)
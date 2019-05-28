package com.baddelni.baddelni.Response.PayPackages

import com.google.gson.annotations.SerializedName

data class PackagesItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("name_ar")
	val nameAr: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("time")
	val time: Int? = null
)
package com.baddelni.baddelni.Response.FavResponse

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class FavResponse(

	@field:SerializedName("code")
	val code: NetworkCode? = null,

	@field:SerializedName("data")
	val data: List<DataItem>? = null
)
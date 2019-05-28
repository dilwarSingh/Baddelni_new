package com.baddelni.baddelni.Response

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class Settings(

	@field:SerializedName("code")
	val code: NetworkCode? = null,

	@field:SerializedName("setting")
	val setting: Setting? = null
)
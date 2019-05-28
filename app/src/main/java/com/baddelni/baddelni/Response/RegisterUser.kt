package com.baddelni.baddelni.Response

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class RegisterUser(

	@field:SerializedName("msg_en")
	val msgEn: String? = null,

	@field:SerializedName("code")
	val code: NetworkCode? = null,

	@field:SerializedName("data")
	val data: com.baddelni.baddelni.Response.MyAccount.Data? = null,

	@field:SerializedName("msg_ar")
	val msgAr: String? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("error_ar")
	val errorAr: String? = null
)
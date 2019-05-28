package com.baddelni.baddelni.Response.Countries

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class Countries(

	@field:SerializedName("code")
	val code: NetworkCode? = null,

	@field:SerializedName("countries")
	val countryList: List<CountriesItem>? = null
)
package com.baddelni.baddelni.Response.requestProduct

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class RequestResponse(

	@field:SerializedName("code")
	val code: NetworkCode? = null,

	@field:SerializedName("orders")
	val orders: List<OrdersItem?>? = null
)
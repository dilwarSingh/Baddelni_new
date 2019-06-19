package com.baddelni.baddelni.Response.sellingItems


import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class SelllingItemResponse(
        @SerializedName("data")
        val `data`: List<Data>,
        @SerializedName("code")
        val code: NetworkCode
)
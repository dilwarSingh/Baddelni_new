package com.baddelni.baddelni.Response.singleAds


import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class SingleAdsResponse(
        @SerializedName("data")
        val `data`: List<Data>?,
        @SerializedName("code")
        val code: NetworkCode?
)
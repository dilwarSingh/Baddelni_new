package com.baddelni.baddelni.home.searchActivity.response


import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class SearchResponse(
        @SerializedName("data")
        val `data`: List<Data>,
        @SerializedName("code")
        val code: NetworkCode
)
package com.baddelni.baddelni.Response
import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName


data class ContactUsResponse(
        @SerializedName("code")
        val code: NetworkCode? = null,
        @SerializedName("msg")
    val msg: String
)
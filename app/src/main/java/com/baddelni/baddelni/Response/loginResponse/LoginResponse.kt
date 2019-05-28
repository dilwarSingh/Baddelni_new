package com.baddelni.baddelni.Response.loginResponse

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class LoginResponse(

        @field:SerializedName("msg")
        val msg: String? = null,

        @field:SerializedName("code")
        val code: NetworkCode? = null,

        @field:SerializedName("data")
        val data: Data? = null


        )
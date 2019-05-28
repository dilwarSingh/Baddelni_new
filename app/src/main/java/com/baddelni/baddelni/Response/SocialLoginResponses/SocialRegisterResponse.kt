package com.baddelni.baddelni.Response.SocialLoginResponses

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class SocialRegisterResponse(
        @SerializedName("code")
        val code: NetworkCode? = null,
        @SerializedName("user")
        val user: User
)

package com.baddelni.baddelni.Response.categories.SingleProductResponse

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class SingleProductResponse(

        @field:SerializedName("product")
        val product: Product? = null,

        @field:SerializedName("code")
        val code: NetworkCode? = null
)
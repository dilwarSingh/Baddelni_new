package com.baddelni.baddelni.Response.PayPackages

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class PackageResponse(

        @field:SerializedName("code")
        val code: NetworkCode? = null,

        @field:SerializedName("packages")
        val packages: List<PackagesItem>? = null
)
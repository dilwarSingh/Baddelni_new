package com.baddelni.baddelni.util

import com.google.gson.annotations.SerializedName

enum class NetworkCode(val i: kotlin.String) {

    @SerializedName("0")
    SUCCESS("0"),
    @SerializedName("1")
    ERROR("1"),
    @SerializedName("2")
    UNKNOWN("2");


    fun isSuccess(): Boolean {
        return this == SUCCESS
    }

}
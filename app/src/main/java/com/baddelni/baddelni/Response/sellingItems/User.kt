package com.baddelni.baddelni.Response.sellingItems


import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("about")
        val about: Any,
        @SerializedName("active")
        val active: Int,
        @SerializedName("active_code")
        val activeCode: Any,
        @SerializedName("available_product")
        val availableProduct: Int,
        @SerializedName("available_time")
        val availableTime: String,
        @SerializedName("country_id")
        val countryId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("device")
        val device: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("email_verified_at")
        val emailVerifiedAt: String,
        @SerializedName("facebook_id")
        val facebookId: Any,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("google_id")
        val googleId: Any,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("img")
        val img: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("role")
        val role: Int,
        @SerializedName("token")
        val token: String,
        @SerializedName("updated_at")
        val updatedAt: String
)
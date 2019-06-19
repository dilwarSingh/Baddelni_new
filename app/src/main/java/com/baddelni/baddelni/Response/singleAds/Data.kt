package com.baddelni.baddelni.Response.singleAds


import com.google.gson.annotations.SerializedName

data class Data(
        @SerializedName("ads_description")
        val adsDescription: String,
        @SerializedName("ads_link")
        val adsLink: String,
        @SerializedName("ads_name")
        val adsName: String,
        @SerializedName("country_id")
        val countryId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("img")
        val img: String,
        @SerializedName("updated_at")
        val updatedAt: String
)
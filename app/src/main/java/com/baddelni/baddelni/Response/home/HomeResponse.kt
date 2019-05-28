package com.baddelni.baddelni.Response.home

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class HomeResponse(

        @field:SerializedName("my_products")
        val myProducts: List<MyProductsItem>? = null,

        @field:SerializedName("ads")
        val ads: List<AdsItem>? = null,

        @field:SerializedName("slides")
        val slides: List<SlidesItem>? = null,

        @field:SerializedName("code")
        val code: NetworkCode? = null,

        @field:SerializedName("interested_products")
        val interestedProducts: List<InterestedProductsItem>? = null,

        @field:SerializedName("categories")
        val categories: List<CategoriesItem>? = null,

        @field:SerializedName("my_country_products")
        val myCountryProducts: List<MyCountryProductsItem>? = null,

        @field:SerializedName("latest_products")
        val latestProducts: List<LatestProductsItem>? = null
)
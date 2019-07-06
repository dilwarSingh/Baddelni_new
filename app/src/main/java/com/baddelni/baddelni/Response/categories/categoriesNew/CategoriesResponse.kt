package com.baddelni.baddelni.Response.categories.categoriesNew


import com.baddelni.baddelni.Response.home.MyCountryProductsItem
import com.baddelni.baddelni.Response.home.SlidesItem
import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
        @SerializedName("code")
        val code: NetworkCode? = null,

        @SerializedName("data")
        val `data`: List<MyCountryProductsItem>,

        @SerializedName("ads")
        val ads: List<Ad>,

        @SerializedName("categories")
        val categories: MutableList<CategoriesItem>,

        @SerializedName("sliders")
        val sliders: List<SlidesItem>
)

// --> POST https://baddelni.com/developbaddelni/public/api/categories http/1.1

package com.baddelni.baddelni.util

import com.google.gson.annotations.SerializedName

enum class FavoriteCode(val favCode: Int) {

    @SerializedName("0")
    NotFavorite(0),
    @SerializedName("1")
    FAVORITE(1);

    fun isFavorite(): Boolean {
        return this == FAVORITE
    }
}
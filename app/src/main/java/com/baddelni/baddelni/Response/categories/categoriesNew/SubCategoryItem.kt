package com.baddelni.baddelni.Response.categories.categoriesNew

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SubCategoryItem(

        @field:SerializedName("category_id")
        val categoryId: Int? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("sub_category")
        val subCategory: String? = null,

        @field:SerializedName("sub_category_ar")
        val subCategoryAr: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("img")
        val img: String? = null,

        @field:SerializedName("id")
        val id: Int? = null
) : Serializable
/*: Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(categoryId)
        parcel.writeString(updatedAt)
        parcel.writeString(subCategory)
        parcel.writeString(subCategoryAr)
        parcel.writeString(createdAt)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubCategoryItem> {
        override fun createFromParcel(parcel: Parcel): SubCategoryItem {
            return SubCategoryItem(parcel)
        }

        override fun newArray(size: Int): Array<SubCategoryItem?> {
            return arrayOfNulls(size)
        }
    }
}*/
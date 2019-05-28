package com.baddelni.baddelni.categories

import java.io.Serializable

data class pojoProductDetail(val productId: Int) : Serializable {

    lateinit var name: String
    lateinit var description: String
    var imageUrl: String = ""
    var userImage: String = ""
    var userName = ""
    var userEmail = ""
    var userId = ""
    var isFav = false
    var subCatId = 0

}
package com.baddelni.baddelni.loginRegister.interests

import java.io.Serializable

data class IntrestsPojo(val catId: Int?) {

    lateinit var catName: String
    lateinit var imageUrl: String
    var status: Boolean = false

}
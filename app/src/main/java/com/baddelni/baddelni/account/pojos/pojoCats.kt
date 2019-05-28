package com.baddelni.baddelni.account.pojos

import java.io.Serializable


data class pojoCats(val categoryId: String) : Serializable {
    var name = ""
    var imageUrl = ""
}
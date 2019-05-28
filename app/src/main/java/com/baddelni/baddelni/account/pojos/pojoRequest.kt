package com.baddelni.baddelni.account.pojos

import java.io.Serializable

data class pojoRequest(var orderId: Int) : Serializable {
    var name = ""
    var phone = ""
    var loaction = ""
    var imageUrl = ""
    var img1 = ""
    var img2 = ""
    var p1Name = ""
    var p2Name = ""
    var statusText = ""
    var description1 = ""
    var description2 = ""
    var pId = 0
    var isFav = false
}
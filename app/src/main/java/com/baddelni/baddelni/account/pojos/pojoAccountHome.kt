package com.baddelni.baddelni.account.pojos

data class pojoAccountHome(val pId: Int) {


    lateinit var name: String
    lateinit var description: String
    lateinit var location: String
    lateinit var productImage: String
    var img1: String = ""
    var img2: String = ""
    var isFav = false
}
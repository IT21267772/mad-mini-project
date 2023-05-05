package com.example.madminiproject.models

data class PackageModel(
    var packID:String = "",
    var packImage:String = "",
    var hotelName:String = "",
    var hotelLocation:String = "",
    val packAuthor: String = "",
    var hotelPrice: Double = 0.0,
    var packDesc:String = "",
    val userId: String = ""
)

package com.example.madminiproject.models

data class Post(
    var postId: String = "",
    var image: String = "",
    var title: String = "",
    var location: String = "",
    val author: String = "",
    val views: Int = 0 ,
    val content: String = "",
    val userId: String = ""
)

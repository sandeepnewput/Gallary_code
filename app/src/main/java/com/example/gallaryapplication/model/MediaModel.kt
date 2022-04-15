package com.example.gallaryapplication.model

enum class MediaType {
    IMAGE,
    VIDEO,
    MUSIC,
    FLICKR
}

data class MediaModel(
    val uri:String,
    val name:String,
    val size: Long,
    val mediaType:MediaType
)





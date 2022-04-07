package com.example.gallaryapplication.view.view.model

import com.google.gson.annotations.SerializedName

data class PhotoModel(
    @SerializedName("photos") val photo: Photo,
    @SerializedName("stat") val stat: String
)

data class Photo(
    @SerializedName("photo") val photos: List<Photos>
)
data class Photos(
    @SerializedName("id") val id: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("server") val server: Int,
    @SerializedName("title") val title: String,
)
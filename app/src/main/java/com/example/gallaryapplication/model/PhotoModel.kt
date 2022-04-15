package com.example.gallaryapplication.model

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("photos") val photo: Photo,
    @SerializedName("stat") val stat: String
)

data class Photo(
    @SerializedName("page") val page: Int,
    @SerializedName("photo") val photos: List<PhotoItem>
)
data class PhotoItem(
    @SerializedName("id") val id: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("server") val server: Int,
    @SerializedName("title") val title: String,
)
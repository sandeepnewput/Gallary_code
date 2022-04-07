package com.example.gallaryapplication.view.view.model

import com.google.gson.annotations.SerializedName


data class PhotoModel(
    @SerializedName("photos") val photos: PhotosModel,
    @SerializedName("stat") val stat: String
)

data class PhotosModel(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("perpage") val perpage: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("photo") val photo: List<PhotoSubModel>
)

data class PhotoSubModel(
    @SerializedName("id") val id: String,
    @SerializedName("owner") val owner: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("server") val server: Int,
    @SerializedName("farm") val farm: Int,
    @SerializedName("title") val title: String,
    @SerializedName("ispublic") val ispublic: Int,
    @SerializedName("isfriend") val isfriend: Int,
    @SerializedName("isfamily") val isfamily: Int
)
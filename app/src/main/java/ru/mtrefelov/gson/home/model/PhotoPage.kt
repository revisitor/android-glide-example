package ru.mtrefelov.gson.home.model

import com.google.gson.annotations.SerializedName

data class PhotoPage(
    val page: Int,

    @SerializedName("pages")
    val pagesCount: Int,

    @SerializedName("perpage")
    val photosPerPage: Int,

    @SerializedName("total")
    val photosTotal: Int,

    @SerializedName("photo")
    val photos: List<Photo>
)

package ru.mtrefelov.gson.home.model

import com.google.gson.annotations.SerializedName

data class Wrapper(
    @SerializedName("photos")
    val photoPage: PhotoPage,
    val stat: String = "ok"
)

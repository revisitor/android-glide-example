package ru.mtrefelov.gson.model.network

import com.google.gson.Gson
import okhttp3.*

object HttpClientConfiguration {
    val okHttpClient = OkHttpClient()
    val gson = Gson()
    val request: Request = Request.Builder()
        .url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
        .build()
}
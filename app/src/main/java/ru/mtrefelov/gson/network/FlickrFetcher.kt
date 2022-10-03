package ru.mtrefelov.gson.network

import okhttp3.*
import ru.mtrefelov.gson.model.Wrapper
import timber.log.Timber
import java.io.IOException

class FlickrFetcher {
    fun run(onRequestSuccess: (Wrapper) -> Unit) = with(HttpClientConfiguration) {
        val callback = ResponseCallback(onRequestSuccess)
        okHttpClient.newCall(request).enqueue(callback)
    }

    private class ResponseCallback(private val onRequestSuccess: (Wrapper) -> Unit) : Callback {
        override fun onResponse(call: Call, response: Response) {
            val json: String = response.body!!.use { it.string() }
            val payload: Wrapper = HttpClientConfiguration.gson.fromJson(json, Wrapper::class.java)
            onRequestSuccess(payload)
        }

        override fun onFailure(call: Call, e: IOException) {
            Timber.e(e)
        }
    }
}
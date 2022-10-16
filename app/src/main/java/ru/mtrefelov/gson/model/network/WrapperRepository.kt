package ru.mtrefelov.gson.model.network

import okhttp3.*
import ru.mtrefelov.gson.model.Wrapper
import timber.log.Timber
import java.io.IOException

class WrapperRepository {
    fun fetchWrapper(onRequestSuccess: (Wrapper) -> Unit) {
        val call = HttpClientConfiguration.run { okHttpClient.newCall(request) }
        val callback = ResponseCallback(onRequestSuccess)
        call.enqueue(callback)
    }

    private class ResponseCallback(private val onRequestSuccess: (Wrapper) -> Unit) : Callback {
        override fun onResponse(call: Call, response: Response) {
            val json = response.use { it.getBodyJson() }
            val wrapper = HttpClientConfiguration.gson.fromJson(json, Wrapper::class.java)
            onRequestSuccess(wrapper)
        }

        private fun Response.getBodyJson() = body.use { it.string() }

        override fun onFailure(call: Call, e: IOException) {
            Timber.e(e)
        }
    }
}
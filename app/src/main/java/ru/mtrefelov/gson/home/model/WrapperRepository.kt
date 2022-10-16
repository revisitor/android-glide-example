package ru.mtrefelov.gson.home.model

import com.google.gson.Gson
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class WrapperRepository {
    private val okHttpClient = OkHttpClient()
    private val gson = Gson()
    private val request: Request = Request.Builder()
        .url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
        .build()

    fun getWrapper(onRequestSuccess: (Wrapper) -> Unit) {
        val call = okHttpClient.newCall(request)
        val callback = ResponseCallback(onRequestSuccess)
        call.enqueue(callback)
    }

    inner class ResponseCallback(private val onRequestSuccess: (Wrapper) -> Unit) : Callback {
        override fun onResponse(call: Call, response: Response) {
            val wrapper = response.jsonToWrapper().also { it.log() }
            onRequestSuccess(wrapper)
        }

        private fun Response.jsonToWrapper(): Wrapper {
            val json: String = use { it.bodyToJson() }
            return gson.fromJson(json, Wrapper::class.java)
        }

        private fun Response.bodyToJson(): String {
            return body.use { it.string() }
        }

        private fun Wrapper.log() {
            photoPage.photos.run {
                slice(4..lastIndex step 5).map(::photoToJson).forEach(Timber::d)
            }
        }

        private fun photoToJson(photo: Photo) = gson.toJson(photo)

        override fun onFailure(call: Call, e: IOException) {
            Timber.e(e)
        }
    }
}
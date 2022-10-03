package ru.mtrefelov.gson.view.activity

import android.content.Context
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*

import ru.mtrefelov.gson.R
import ru.mtrefelov.gson.model.*
import ru.mtrefelov.gson.network.*
import ru.mtrefelov.gson.view.adapter.*

import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainActivityContext: Context = this

        val recyclerView: RecyclerView = findViewById(R.id.rView)
        recyclerView.apply {
            layoutManager = GridLayoutManager(mainActivityContext, 2)
            val itemDecoration = PhotoRecyclerViewItemDecoration(100)
            addItemDecoration(itemDecoration)
        }

        FlickrFetcher().run { payload: Wrapper ->
            val photos: List<Photo> = payload.photoPage.photos.also { it.log() }
            runOnUiThread {
                recyclerView.adapter = PhotoRecyclerViewAdapter(mainActivityContext, photos)
            }
        }
    }

    private fun List<Photo>.log() {
        slice(4..lastIndex step 5).forEach {
            val json: String = HttpClientConfiguration.gson.toJson(it)
            Timber.d(json)
        }
    }
}

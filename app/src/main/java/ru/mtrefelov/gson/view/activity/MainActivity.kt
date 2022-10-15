package ru.mtrefelov.gson.view.activity

import android.annotation.SuppressLint
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar

import ru.mtrefelov.gson.R
import ru.mtrefelov.gson.model.*
import ru.mtrefelov.gson.network.*
import ru.mtrefelov.gson.view.ImageClickListener
import ru.mtrefelov.gson.view.adapter.*

import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var layout: ViewGroup
    private lateinit var photoRecyclerView: RecyclerView

    private val activityContext: Context = this
    private val photoRepository = WrapperRepository()
    private val photos = mutableListOf<Photo>()

    private val imageClickListener = object : ImageClickListener {
        override fun onClick(imageUrl: String) {
            Timber.i(imageUrl)
            putLinkIntoClipboard(imageUrl)
            showImageActivity(imageUrl)
        }
    }

    private val onWrapperFetched: (Wrapper) -> Unit = {
        photos.addAll(it.photoPage.photos)
        photos.log()
        runOnUiThread {
            photoRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private val clipboard: ClipboardManager by lazy {
        getSystemService(ClipboardManager::class.java)
    }

    private val showImageActivityIntent: Intent by lazy {
        Intent(activityContext, ImageActivity::class.java)
    }

    private val snackBar: Snackbar by lazy {
        Snackbar.make(layout, "Картинка добавлена в Избранное", Snackbar.LENGTH_LONG)
    }

    private val showImageInBrowserIntent: Intent by lazy {
        Intent(Intent.ACTION_VIEW)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.main_layout)

        photoRecyclerView = findViewById(R.id.recyclerview)
        photoRecyclerView.apply {
            adapter = PhotoRecyclerViewAdapter(activityContext, photos, imageClickListener)
            layoutManager = GridLayoutManager(activityContext, 2)
            addItemDecoration(PhotoRecyclerViewItemDecoration(100))
        }

        photoRepository.getWrapper { onWrapperFetched(it) }
    }

    private fun List<Photo>.log() {
        for (i in 4..lastIndex step 5) {
            val photo = get(i)
            val json = photo.toJson()
            Timber.d(json)
        }
    }

    private fun Photo.toJson() = HttpClientConfiguration.gson.toJson(this)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK) {
            snackBar.setAction("Открыть") {
                data?.getStringExtra("favouriteImageUrl")?.let { imageUrl ->
                    showImageInBrowserIntent.data = Uri.parse(imageUrl)
                    startActivity(showImageInBrowserIntent)
                }
            }

            snackBar.show()
        }
    }

    private fun putLinkIntoClipboard(text: CharSequence) {
        val clip = ClipData.newPlainText("Flickr image link", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun showImageActivity(imageUrl: CharSequence) {
        showImageActivityIntent.putExtra("imageUrl", imageUrl)
        startActivityForResult(showImageActivityIntent, 0)
    }
}

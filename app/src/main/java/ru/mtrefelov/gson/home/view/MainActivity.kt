package ru.mtrefelov.gson.home.view

import android.annotation.SuppressLint
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar

import ru.mtrefelov.gson.R
import ru.mtrefelov.gson.home.MainContract
import ru.mtrefelov.gson.home.model.Photo
import ru.mtrefelov.gson.home.presenter.MainPresenter
import ru.mtrefelov.gson.image.ImageActivity
import timber.log.Timber

class MainActivity : AppCompatActivity(), MainContract.View, ImageClickListener {
    private lateinit var layout: ViewGroup
    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var snackbar: Snackbar
    private lateinit var clipboard: ClipboardManager

    private lateinit var presenter: MainContract.Presenter

    private val photos = mutableListOf<Photo>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoRecyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            val activityContext: Context = this@MainActivity
            val imageClickListener: ImageClickListener = this@MainActivity
            adapter = PhotoRecyclerViewAdapter(activityContext, photos, imageClickListener)
            layoutManager = GridLayoutManager(activityContext, 2)
            addItemDecoration(PhotoRecyclerViewItemDecoration(100))
        }

        layout = findViewById<ViewGroup>(R.id.main_layout).also {
            snackbar = Snackbar.make(it, "Картинка добавлена в Избранное", Snackbar.LENGTH_LONG)
        }

        clipboard = getSystemService(ClipboardManager::class.java)

        setPresenter(MainPresenter(this))
        presenter.onViewCreated()
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun renderPhotos(photos: List<Photo>) {
        this.photos.addAll(photos)
        runOnUiThread {
            photoRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun openImageView(imageUrl: String) {
        val intent = Intent(this, ImageActivity::class.java).putExtra("imageUrl", imageUrl)
        startActivityForResult(intent, 0)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onImageClicked(imageUrl: String) {
        Timber.i(imageUrl)
        putLinkIntoClipboard(imageUrl)
        presenter.onImageClicked(imageUrl)
    }

    private fun putLinkIntoClipboard(text: CharSequence) {
        val clip = ClipData.newPlainText("Flickr image link", text)
        clipboard.setPrimaryClip(clip)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK) {
            data?.getStringExtra("favouriteImageUrl")?.let(::showSnackbar)
        }
    }

    override fun showSnackbar(imageUrl: String) {
        snackbar.apply {
            setAction("Открыть") { openBrowser(imageUrl) }
            show()
        }
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

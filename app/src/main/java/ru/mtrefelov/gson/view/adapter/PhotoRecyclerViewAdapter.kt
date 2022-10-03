package ru.mtrefelov.gson.view.adapter

import android.content.*
import android.view.*
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide

import ru.mtrefelov.gson.R
import ru.mtrefelov.gson.model.Photo

import timber.log.Timber

class PhotoRecyclerViewAdapter(private val context: Context, private val photos: List<Photo>) :
    RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
    }

    private val clipboard: ClipboardManager by lazy {
        context.getSystemService(ClipboardManager::class.java)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.rview_item, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val url: String = photos[position].toUrl()
        with(holder) {
            Glide.with(itemView).load(url).into(imageView)
            itemView.setOnClickListener {
                Timber.i(url)
                val clip = ClipData.newPlainText("Flickr image link", url)
                clipboard.setPrimaryClip(clip)
            }
        }
    }

    private fun Photo.toUrl(): String {
        return "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}_z.jpg"
    }

    override fun getItemCount() = photos.size
}
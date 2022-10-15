package ru.mtrefelov.gson.view.adapter

import android.content.*
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import ru.mtrefelov.gson.R
import ru.mtrefelov.gson.model.Photo
import ru.mtrefelov.gson.view.ImageClickListener

class PhotoRecyclerViewAdapter(
    private val context: Context,
    private val photos: List<Photo>,
    private val imageClickListener: ImageClickListener
) :
    RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image)

        fun putImage(imageUrl: String) {
            Glide.with(itemView).load(imageUrl).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.rview_item, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: PhotoViewHolder, position: Int) {
        val imageUrl: String = photos[position].toUrl()
        with(viewHolder) {
            putImage(imageUrl)
            itemView.setOnClickListener {
                imageClickListener.onClick(imageUrl)
            }
        }
    }

    private fun Photo.toUrl(): String {
        return "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}_z.jpg"
    }

    override fun getItemCount() = photos.size
}
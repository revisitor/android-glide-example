package ru.mtrefelov.gson.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide

import ru.mtrefelov.gson.R

class ImageActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var imageView: ImageView
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        imageView = findViewById(R.id.image_focused)
        imageUrl = intent.getStringExtra("imageUrl")!!
        showImage()
    }

    private fun showImage() = Glide.with(this).load(imageUrl).into(imageView)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add_to_favourites) {
            val resultIntent = Intent().apply {
                putExtra("favouriteImageUrl", imageUrl)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
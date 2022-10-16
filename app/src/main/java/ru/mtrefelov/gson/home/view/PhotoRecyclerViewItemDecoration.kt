package ru.mtrefelov.gson.home.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PhotoRecyclerViewItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply {
            top = offset
            bottom = offset
            if (parent.getChildAdapterPosition(view) % 2 == 0) {
                right = offset
            } else {
                left = offset
            }
        }
    }
}
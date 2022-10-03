package ru.mtrefelov.gson.view.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PhotoRecyclerViewItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply {
            top = offset
            bottom = offset
            if (parent.getChildAdapterPosition(view).isEven()) {
                right = offset
            } else {
                left = offset
            }
        }
    }

    private fun Int.isEven() = this % 2 == 0
}
package com.example.projectcontact.util.margin

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(context: Context, @DimenRes private val marginInDp: Int) : RecyclerView.ItemDecoration() {

    private val margin: Int = context.resources.getDimensionPixelSize(marginInDp)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        // Apply margin to all items except the first
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = margin
        }
        // Apply margin to the last item
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.right = margin
        }
    }


}
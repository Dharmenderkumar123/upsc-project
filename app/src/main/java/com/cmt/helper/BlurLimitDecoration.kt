package com.cmt.helper

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class BlurLimitDecoration(private val limit: Int) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        color = Color.parseColor("#BCFFFFFF") // Semi-transparent white
        maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)

            if (position >= limit) {
                // Draw a blur effect or a "Locked" overlay over the page
                c.drawRect(child.left.toFloat(), child.top.toFloat(),
                    child.right.toFloat(), child.bottom.toFloat(), paint)
            }
        }
    }
}


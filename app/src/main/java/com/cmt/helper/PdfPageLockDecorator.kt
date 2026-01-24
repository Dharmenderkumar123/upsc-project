package com.cmt.helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class PdfPageLockDecorator(context: Context, private val limit: Int) : RecyclerView.ItemDecoration() {

    // 1. Setup Paints
    private val overlayPaint = Paint().apply {
        color = Color.parseColor("#F2121212") // Dark overlay
        style = Paint.Style.FILL
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 40f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    // 2. Load the Lock Icon (Use a standard Android lock icon or your own)
    private val lockIcon: Drawable? = ContextCompat.getDrawable(context, android.R.drawable.ic_lock_lock)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)

            if (position >= limit) {
                // Draw the dark overlay
                c.drawRect(
                    child.left.toFloat(),
                    child.top.toFloat(),
                    child.right.toFloat(),
                    child.bottom.toFloat(),
                    overlayPaint
                )

                // Calculate the center of the current page
                val centerX = (child.left + child.right) / 2
                val centerY = (child.top + child.bottom) / 2

                // Draw the Lock Icon
                lockIcon?.let {
                    val iconSize = 120 // Adjust size as needed
                    it.setBounds(
                        centerX - (iconSize / 2),
                        centerY - iconSize, // Positioned slightly above center
                        centerX + (iconSize / 2),
                        centerY
                    )
                    // Optional: Make the icon white
                    it.setTint(Color.WHITE)
                    it.draw(c)
                }

                // Draw Text below the icon
                c.drawText("PAGE LOCKED", centerX.toFloat(), centerY.toFloat() + 60f, textPaint)
            }
        }
    }
}

//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.RecyclerView
//
//class PdfPageLockDecorator(private val limit: Int) : RecyclerView.ItemDecoration() {
//
//    private val overlayPaint = Paint().apply {
//        color = Color.parseColor("#F2121212") // Near-solid dark grey
//        style = Paint.Style.FILL
//    }
//
//    private val textPaint = Paint().apply {
//        color = Color.WHITE
//        textSize = 45f
//        textAlign = Paint.Align.CENTER
//        isAntiAlias = true
//    }
//
//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        val childCount = parent.childCount
//        for (i in 0 until childCount) {
//            val child = parent.getChildAt(i)
//            val position = parent.getChildAdapterPosition(child)
//
//            // Lock every page at index 50 and above
//            if (position >= limit) {
//                // 1. Draw the dark overlay covering the page
//                c.drawRect(
//                    child.left.toFloat(),
//                    child.top.toFloat(),
//                    child.right.toFloat(),
//                    child.bottom.toFloat(),
//                    overlayPaint
//                )
//
//                // 2. Draw "Locked" text in the center
//                val centerX = (child.left + child.right) / 2f
//                val centerY = (child.top + child.bottom) / 2f
//
//                c.drawText("PAGE LOCKED", centerX, centerY, textPaint)
//                c.drawText("Purchase full version to view", centerX, centerY + 60f, textPaint)
//            }
//        }
//    }
//}
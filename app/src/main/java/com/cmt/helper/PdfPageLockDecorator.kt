package com.cmt.helper


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cmt.services.R


class PdfPageLockDecorator(
    private val context: Context,
    private val limit: Int,
    @DrawableRes private val lockImageRes: Int, // Pass the image resource here
    val onBuyClick: () -> Unit
) : RecyclerView.ItemDecoration() {

    // Decode the bitmap once to save memory/processing
    private val backgroundBitmap: Bitmap? = BitmapFactory.decodeResource(context.resources, lockImageRes)

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 44f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        typeface = Typeface.DEFAULT_BOLD
        // Added shadow to make text pop against any image background
        setShadowLayer(5f, 0f, 0f, Color.BLACK)
    }

    // ... (Your existing buttonPaint and buttonTextPaint)

        private val buttonTextPaint = Paint().apply {
        color = Color.WHITE
        textSize = 36f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val buttonPaint = Paint().apply {
        color = ContextCompat.getColor(context, com.the_pride_ias.R.color.bottomBarColor)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        for (i in 0 until parent.childCount) {
//            val child = parent.getChildAt(i)
//            val position = parent.getChildAdapterPosition(child)
//
//            if (position >= limit) {
//                // 1. Draw the Full Screen Image Overlay
//                backgroundBitmap?.let { bitmap ->
//                    val srcRect = Rect(0, 0, bitmap.width, bitmap.height)
//                    val destRect = Rect(child.left, child.top, child.right, child.bottom)
//
//                    // Use a filter for better scaling quality
//                    c.drawBitmap(bitmap, srcRect, destRect, Paint(Paint.FILTER_BITMAP_FLAG))
//                }
//
//                // 2. Draw UI Elements (Icon, Text, Button)
//                val centerX = (child.left + child.right) / 2f
//                val centerY = (child.top + child.bottom) / 2f
//
//                // ... (Keep your existing Lock Icon and Button drawing logic here)
//
//                c.drawText("PAGE LOCKED", centerX, centerY - 20f, textPaint)
//
//                val rect = getButtonRectForChild(child)
//                c.drawRoundRect(rect, 24f, 24f, buttonPaint)
//                val textY = rect.centerY() + (buttonTextPaint.textSize / 3)
//                c.drawText("BUY NOW", centerX, textY, buttonTextPaint)
//            }
//        }
//    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)

            if (position >= limit) {
                backgroundBitmap?.let { bitmap ->
                    val viewWidth = child.width.toFloat()
                    val viewHeight = child.height.toFloat()
                    val bitmapWidth = bitmap.width.toFloat()
                    val bitmapHeight = bitmap.height.toFloat()

                    // 1. Calculate Center Crop srcRect
                    val scale: Float
                    var dx = 0f
                    var dy = 0f

                    if (bitmapWidth * viewHeight > viewWidth * bitmapHeight) {
                        scale = viewHeight / bitmapHeight
                        dx = (bitmapWidth - viewWidth / scale) * 0.3f
                    } else {
                        scale = viewWidth / bitmapWidth
                        dy = (bitmapHeight - viewHeight / scale) * 0.3f
                    }

                    val srcRect = Rect(
                        dx.toInt(),
                        dy.toInt(),
                        (bitmapWidth - dx).toInt(),
                        (bitmapHeight - dy).toInt()
                    )

                    val destRect = Rect(child.left, child.top, child.right, child.bottom)

                    c.drawBitmap(bitmap, srcRect, destRect, Paint(Paint.FILTER_BITMAP_FLAG))
                }

                val centerX = (child.left + child.right) / 2f
                val centerY = (child.top + child.bottom) / 2f

                c.drawText("PAGE LOCKED", centerX, centerY - 20f, textPaint)

                val rect = getButtonRectForChild(child)
                c.drawRoundRect(rect, 24f, 24f, buttonPaint)
                val textY = rect.centerY() + (buttonTextPaint.textSize / 3)
                c.drawText("BUY NOW", centerX, textY, buttonTextPaint)
            }
        }
    }

    fun getButtonRectForChild(child: View): RectF {
        val centerX = (child.left + child.right) / 2f
        val centerY = (child.top + child.bottom) / 2f
        return RectF(centerX - 160f, centerY + 40f, centerX + 160f, centerY + 150f)
    }
}

//class PdfPageLockDecorator(
//    private val context: Context,
//    private val limit: Int,
//    val onBuyClick: () -> Unit
//) : RecyclerView.ItemDecoration() {
//
//    private val overlayPaint = Paint().apply {
//        color = Color.BLACK
//        style = Paint.Style.FILL
//    }
//
//    private val textPaint = Paint().apply {
//        color = Color.WHITE
//        textSize = 40f
//        textAlign = Paint.Align.CENTER
//        isAntiAlias = true
//        typeface = Typeface.DEFAULT_BOLD
//    }
//
//    private val buttonPaint = Paint().apply {
//        color = ContextCompat.getColor(context, com.the_pride_ias.R.color.colorTheme)
//        style = Paint.Style.FILL
//        isAntiAlias = true
//    }
//
//    private val buttonTextPaint = Paint().apply {
//        color = Color.BLACK
//        textSize = 36f
//        textAlign = Paint.Align.CENTER
//        isAntiAlias = true
//    }
//
//    private val lockIcon: Drawable? =
//        ContextCompat.getDrawable(context, android.R.drawable.ic_lock_lock)
//
//    /**
//     * Helper function to calculate the button's bounding box for a specific child view.
//     * This is used both for drawing and for hit-testing touch events.
//     */
//    fun getButtonRectForChild(child: View): RectF {
//        val centerX = (child.left + child.right) / 2f
//        val centerY = (child.top + child.bottom) / 2f
//
//        val btnWidth = 320f
//        val btnHeight = 110f
//        val left = centerX - btnWidth / 2
//        val top = centerY + 40f // Positioned below the "PAGE LOCKED" text
//        val right = centerX + btnWidth / 2
//        val bottom = top + btnHeight
//
//        return RectF(left, top, right, bottom)
//    }
//
//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        for (i in 0 until parent.childCount) {
//            val child = parent.getChildAt(i)
//            val position = parent.getChildAdapterPosition(child)
//
//            // Only draw overlay if the page index is beyond the limit
//            if (position >= limit) {
//                // 1. Draw Semi-transparent Overlay
//                c.drawRect(
//                    child.left.toFloat(), child.top.toFloat(),
//                    child.right.toFloat(), child.bottom.toFloat(), overlayPaint
//                )
//
//                val centerX = (child.left + child.right) / 2f
//                val centerY = (child.top + child.bottom) / 2f
//
//                // 2. Draw Lock Icon
//                lockIcon?.let {
//                    val iconSize = 120
//                    it.setBounds(
//                        (centerX - iconSize / 2).toInt(),
//                        (centerY - 200).toInt(),
//                        (centerX + iconSize / 2).toInt(),
//                        (centerY - 80).toInt()
//                    )
//                    it.setTint(Color.WHITE)
//                    it.draw(c)
//                }
//
//                // 3. Draw Text
//                c.drawText("PAGE LOCKED", centerX, centerY - 20f, textPaint)
//
//                // 4. Draw Buy Button
//                val rect = getButtonRectForChild(child)
//                c.drawRoundRect(rect, 24f, 24f, buttonPaint)
//
//                // Vertical centering for text inside the button
//                val textY = rect.centerY() + (buttonTextPaint.textSize / 3)
//                c.drawText("BUY NOW", centerX, textY, buttonTextPaint)
//            }
//        }
//    }
//}


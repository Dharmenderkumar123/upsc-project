package com.cmt.helper

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar


class VerticalSeekBar : AppCompatSeekBar {

    interface ScrollStateListener {
        fun onStartScrolling()
        fun onStopScrolling()
        fun onProgressChanged(progress: Int)
    }

    private var scrollStateListener: ScrollStateListener? = null

    fun setScrollStateListener(listener: ScrollStateListener) {
        this.scrollStateListener = listener
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    // FIX: This moves the thumb visually when progress is set via code (PDF scrolling)
    override fun setProgress(progress: Int) {
        super.setProgress(progress)
        onSizeChanged(width, height, 0, 0)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(h, w, oldh, oldw)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    override fun onDraw(c: Canvas) {
        c.rotate(90f)
        c.translate(0f, -width.toFloat())
        super.onDraw(c)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isPressed = true
                scrollStateListener?.onStartScrolling()
                updateFromTouch(event)
            }
            MotionEvent.ACTION_MOVE -> {
                updateFromTouch(event)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isPressed = false
                scrollStateListener?.onStopScrolling()
            }
        }
        return true
    }

    private fun updateFromTouch(event: MotionEvent) {
        val i = (max * event.y / height).toInt().coerceIn(0, max)
        progress = i
        scrollStateListener?.onProgressChanged(i)
        onSizeChanged(width, height, 0, 0)
        invalidate()
    }
}

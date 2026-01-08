package com.cmt.helper

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.the_pride_ias.R
import java.io.File


@BindingAdapter(
    value = ["imageUrl", "drawableImage", "bitmapImage", "circularImage", "uriImage"],
    requireAll = false
)
fun loadImage(
    view: ImageView,
    imageUrl: String?,
    drawableImage: Int?,
    bitmapImage: Bitmap?,
    circularImage: String?,
    uriImage: Uri?
) {
    val circularProgressDrawable = CircularProgressDrawable(view.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.setTint(
        ContextCompat.getColor(view.context, R.color.colorPrimary)
    )
    circularProgressDrawable.start()

    if (imageUrl != null && imageUrl.isNotEmpty()) {
        Glide.with(view).load(imageUrl).placeholder(R.drawable.image_placeholder).into(view)
    } else if (bitmapImage != null) {
        Glide.with(view).load(bitmapImage).into(view)
    } else if (drawableImage != null) {
        Glide.with(view).load(drawableImage).into(view)
    } else if (circularImage != null && circularImage.isNotEmpty()) {
        Glide.with(view).load(circularImage).placeholder(circularProgressDrawable).centerInside()
            .circleCrop().into(view)
    } else if (uriImage != null) {
        view.setImageURI(uriImage)

    }
}


@BindingAdapter("htmlText")
fun setHtmlTextValue(textView: TextView, htmlText: String?) {
    if (htmlText == null) return
    textView.text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT)
}


@BindingAdapter("tint")
fun ImageView.setImageTint(@ColorInt color: Int) {
    setColorFilter(color)
}

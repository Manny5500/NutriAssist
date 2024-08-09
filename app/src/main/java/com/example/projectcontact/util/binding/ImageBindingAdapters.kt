package com.example.projectcontact.util.binding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

object ImageBindingAdapters{
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: CircleImageView, url: String?) {
        if (url.isNullOrEmpty()) {
            view.setImageDrawable(null)
            return
        }
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}

@BindingAdapter("srcCompat")
fun setImageDrawable(imageView: ImageView, @DrawableRes drawableRes: Int) {
    imageView.setImageResource(drawableRes)
}
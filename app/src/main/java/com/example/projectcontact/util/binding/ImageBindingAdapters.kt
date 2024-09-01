package com.example.projectcontact.util.binding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.projectcontact.R
import de.hdodenhof.circleimageview.CircleImageView

object ImageBindingAdapters{
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: CircleImageView, url: String?) {
        if(url.isNullOrEmpty()){
            Glide.with(view.context)
                .load(R.drawable.profile_user)
                .into(view)
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
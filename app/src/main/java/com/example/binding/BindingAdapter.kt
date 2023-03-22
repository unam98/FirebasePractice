package com.example.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:mainImage")
fun loadMainImage(view: ImageView, imageUrl: String) {
    Glide
        .with(view.context)
        .load(imageUrl)
        .circleCrop()
        .into(view)
}

package com.example.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.opengl.Visibility
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.firebasepractice.R

//@SuppressLint("UseCompatLoadingForDrawables")
//@BindingAdapter("app:textView_touch_gitItem")
//fun TextView.textviewtouch(selected: Boolean) {
//    if (selected) {
//        typeface = ResourcesCompat.getFont(context, R.font.nanumgothicextrabold)
//    } else {
//        typeface = ResourcesCompat.getFont(context, R.font.nanumgothicbold)
//    }
//}

@SuppressLint("ResourceAsColor")
@BindingAdapter("app:layout_touch_gitItem")
fun ConstraintLayout.layouttouch(selected: Boolean) {
    if (selected) {
        setBackgroundResource(R.drawable.select)
//        setBackgroundColor(ContextCompat.getColor(context, R.color.M1))
    } else {
        setBackgroundColor(Color.parseColor("#FFFFFF"))
    }
}

@SuppressLint("ResourceAsColor")
@BindingAdapter("app:image_select")
fun ImageView.loadSelect(selected: Boolean) {
    if (selected) {
        setBackgroundResource(R.drawable.select)
//        setBackgroundColor(ContextCompat.getColor(context, R.color.M1))
    } else {
        setBackgroundColor(Color.parseColor("#FFFFFF"))
    }
}


//@BindingAdapter("app:profile_load")
//fun ImageView.loadprofile(url: String) {
//    if (url == "") {
//        Glide.with(context)
//            .load(R.drawable.ic_github)
//            .circleCrop()
//            .into(this)
//
//    } else {
//        Glide.with(context)
//            .load(url)
//            .circleCrop()
//            .error(R.drawable.ic_github)
//            .into(this)
//    }
//}
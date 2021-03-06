package com.cainiaowo.service.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * 项目适配用的BindingAdapter
 */

/**
 * ImageView支持图片加载、绑定
 */
@BindingAdapter("srcCompat", requireAll = false)
fun setImageSrcCompat(iv: ImageView, src: Any?) {
    Glide.with(iv).load(src).into(iv)
}

// region ImageView app:tint    ImageViewBindingAdapter中未实现,所以如果在DataBinding布局中需要使用时自己实现
@BindingAdapter("tint")
fun setImageTint(iv: ImageView, color: Int) {
    if (color == 0) return
    runCatching {
        iv.setColorFilter(ContextCompat.getColor(iv.context, color))
    }.onFailure {
        iv.setColorFilter(color)
    }
}
// endregion

// region TextView android:textColor  TextViewBindingAdapter中未实现,所以如果在DataBinding布局中需要使用时自己实现
@BindingAdapter("android:textColor")
fun setTextColor(tv: TextView, color: Int) {
    if (color == 0) return
    runCatching {
        tv.setTextColor(ContextCompat.getColor(tv.context, color))
    }.onFailure {
        tv.setTextColor(color)
    }
}
// endregion
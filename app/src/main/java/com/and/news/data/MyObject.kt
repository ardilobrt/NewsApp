package com.and.news.data

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.and.news.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

object MyObject {

    const val EXTRA_ARTICLES = "extra_articles"

    // Avoid duplicate coding
    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(this)
    }

    fun ImageView.loadImageDetail(url: String?) {
        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .into(this)
    }

    fun RecyclerView.showLoading(isLoading: Boolean) {
        val skeleton: Skeleton = this.applySkeleton(R.layout.item_news)
        if (isLoading) skeleton.showSkeleton() else skeleton.showOriginal()
    }
}
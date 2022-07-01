package com.and.news.data

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.and.news.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

object MyCompanion {

    const val API_KEY = "0e10d44df94f420db1acead69a71a17e"

    // Avoid duplicate coding
    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(this)
    }

    fun RecyclerView.showLoading(isLoading: Boolean) {
        val skeleton: Skeleton = this.applySkeleton(R.layout.item_news)
        if (isLoading) skeleton.showSkeleton() else skeleton.showOriginal()
    }
}
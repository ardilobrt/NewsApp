package com.and.news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.and.news.R
import com.and.news.adapter.ArticlesAdapter.MyViewHolder
import com.and.news.data.local.entity.Articles
import com.and.news.databinding.ItemNewsBinding
import com.and.news.utils.MyCompanion.loadImage

class ArticlesAdapter(private val onBookmarkClick: (Articles) -> Unit) :
    ListAdapter<Articles, MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallBack: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articles = getItem(position)
        holder.bind(articles)

        val ivBookmark = holder.binding.ivBookmark
        if (articles.isBookmarked) {
            ivBookmark.setImageResource(R.drawable.ic_bookmarked)
        } else ivBookmark.setImageResource(R.drawable.ic_bookmark)

        ivBookmark.setOnClickListener {
            onBookmarkClick(articles)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked(articles)
        }
    }

    inner class MyViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(articles: Articles) {
            binding.apply {
                tvItemSource.text = articles.sourceName
                tvItemTitle.text = articles.title
                ivItemImage.loadImage(articles.urlToImage)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Articles)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Articles> =
            object : DiffUtil.ItemCallback<Articles>() {
                override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
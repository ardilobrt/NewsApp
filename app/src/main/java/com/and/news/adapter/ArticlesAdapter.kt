package com.and.news.adapter

import android.annotation.SuppressLint
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.and.news.R
import com.and.news.data.local.entity.Articles
import com.and.news.adapter.ArticlesAdapter.MyViewHolder
import com.and.news.databinding.ItemNewsBinding
import com.and.news.utils.MyCompanion.loadImage

class ArticlesAdapter(private val onBookmarkClick: (Articles) -> Unit) :
    ListAdapter<Articles, MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articles = getItem(position)
        holder.bind(articles)

        val ivBookmark = holder.binding.ivBookmark
        if (articles.isBookmarked) {
            ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    ivBookmark.context,
                    R.drawable.ic_bookmarked
                )
            )
        } else ivBookmark.setImageDrawable(
            ContextCompat.getDrawable(
                ivBookmark.context,
                R.drawable.ic_bookmark
            )
        )

        ivBookmark.setOnClickListener {
            onBookmarkClick(articles)
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
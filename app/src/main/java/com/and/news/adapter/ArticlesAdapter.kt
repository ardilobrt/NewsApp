package com.and.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.and.news.utils.MyCompanion.loadImage
import com.and.news.databinding.ItemNewsBinding
import com.and.news.data.remote.model.ArticlesItem

class ArticlesAdapter(
    private val listArticles: ArrayList<ArticlesItem>,
    val onItemClick: (ArticlesItem) -> Unit
) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articles: ArticlesItem) {
            binding.apply {
                ivItemImage.loadImage(articles.urlToImage)
                tvItemSource.text = articles.source?.name
                tvItemTitle.text = articles.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listArticles[position]
        holder.itemView.setOnClickListener{
            onItemClick(item)
        }
        holder.bind(listArticles[position])
    }

    override fun getItemCount(): Int = listArticles.size
}
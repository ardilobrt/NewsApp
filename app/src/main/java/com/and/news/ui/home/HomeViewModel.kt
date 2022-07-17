package com.and.news.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.MyResult
import com.and.news.data.local.entity.Articles
import com.and.news.data.repository.ArticlesRepository

class HomeViewModel(private val articlesRepository: ArticlesRepository) : ViewModel() {

    val listArticles: LiveData<MyResult<List<Articles>>> = articlesRepository.listArticles

    init {
        setArticles()
    }

    fun setArticles() = articlesRepository.getArticlesFromApi()

    fun saveBookmark(articles: Articles) {
        articlesRepository.setBookmarkArticles(articles, true)
    }

    fun deleteBookmark(articles: Articles) {
        articlesRepository.setBookmarkArticles(articles, false)
    }
}
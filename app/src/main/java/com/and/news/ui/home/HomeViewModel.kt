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

    private fun setArticles() = articlesRepository.getArticlesFromApi()
}
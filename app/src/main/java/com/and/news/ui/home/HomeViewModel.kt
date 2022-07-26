package com.and.news.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.Event
import com.and.news.data.local.entity.Articles
import com.and.news.data.repository.ArticlesRepository

class HomeViewModel(private val articlesRepository: ArticlesRepository) : ViewModel() {

    val listArticles: MutableLiveData<List<Articles>> = articlesRepository.listArticles
    val errorMessage: MutableLiveData<Event<String>> = articlesRepository.errorMessage

    fun getArticles(country: String) = articlesRepository.getArticlesFromApi(country)

    fun saveBookmark(articles: Articles) {
        articlesRepository.setBookmarkArticles(articles, true)
    }

    fun deleteBookmark(articles: Articles) {
        articlesRepository.setBookmarkArticles(articles, false)
    }
}
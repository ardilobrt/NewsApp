package com.and.news.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.local.entity.Articles
import com.and.news.data.repository.ArticlesRepository

class BookmarkViewModel(private val articlesRepository: ArticlesRepository) : ViewModel() {

    private val _listBookmark = articlesRepository.getBookmarkedArticles()
    val listBookmark: LiveData<List<Articles>> = _listBookmark

    fun saveBookmark(articles: Articles) {
        articlesRepository.setBookmarkArticles(articles, true)
    }

    fun deleteBookmark(articles: Articles) {
        articlesRepository.setBookmarkArticles(articles, false)
    }
}
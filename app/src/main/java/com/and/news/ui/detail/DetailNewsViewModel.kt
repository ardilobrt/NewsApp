package com.and.news.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.remote.model.ArticlesItem

class DetailNewsViewModel : ViewModel() {

    private val _items = MutableLiveData<ArticlesItem>()
    val item: LiveData<ArticlesItem> = _items

    fun getDetails(articles: ArticlesItem) {
        _items.value = articles
    }
}
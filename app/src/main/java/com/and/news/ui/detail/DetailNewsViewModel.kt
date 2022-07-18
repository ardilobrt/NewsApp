package com.and.news.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.local.entity.Articles

class DetailNewsViewModel : ViewModel() {

    private val _items = MutableLiveData<Articles>()
    val item: LiveData<Articles> = _items

    fun getDetails(articles: Articles) {
        _items.value = articles
    }
}
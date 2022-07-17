package com.and.news.ui.home

import android.content.Context
import androidx.lifecycle.*
import com.and.news.data.repository.ArticlesRepository
import com.and.news.di.Injection

class HomeViewModelFactory private constructor(private val articlesRepository: ArticlesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(articlesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HomeViewModelFactory? = null
        fun getInstance(context: Context): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}
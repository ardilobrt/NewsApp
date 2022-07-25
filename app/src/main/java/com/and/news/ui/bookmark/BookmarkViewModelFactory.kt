package com.and.news.ui.bookmark

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.and.news.data.repository.ArticlesRepository
import com.and.news.di.Injection

class BookmarkViewModelFactory(private val articlesRepository: ArticlesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(articlesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: BookmarkViewModelFactory? = null
        fun getInstance(context: Context): BookmarkViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: BookmarkViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}
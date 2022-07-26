package com.and.news.di

import android.content.Context
import com.and.news.data.local.room.ArticlesDatabase
import com.and.news.data.remote.api.ApiConfig
import com.and.news.data.repository.ArticlesRepository
import com.and.news.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): ArticlesRepository {
        val apiService = ApiConfig.getArticleService(context)
        val database = ArticlesDatabase.getInstance(context)
        val dao = database.articleDao()
        val appExecutors = AppExecutors()
        return ArticlesRepository.getInstance(apiService, dao, appExecutors)
    }
}
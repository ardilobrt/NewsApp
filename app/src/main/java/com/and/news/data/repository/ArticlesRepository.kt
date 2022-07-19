package com.and.news.data.repository

import androidx.lifecycle.*
import com.and.news.data.MyResult
import com.and.news.data.local.entity.Articles
import com.and.news.data.local.room.ArticlesDao
import com.and.news.data.remote.api.ApiService
import com.and.news.data.remote.model.ArticlesResponse
import com.and.news.utils.AppExecutors
import com.and.news.utils.DateFormatter
import retrofit2.*

class ArticlesRepository private constructor(
    private val apiService: ApiService,
    private val articlesDao: ArticlesDao,
    private val appExecutors: AppExecutors
) {
    private val _listArticles = MediatorLiveData<MyResult<List<Articles>>>()
    val listArticles: LiveData<MyResult<List<Articles>>> get() = _listArticles

    fun getArticlesFromApi() {
        _listArticles.value = MyResult.Loading
        val client = apiService.getNewsByLocal(LOCAL_IDN)
        client.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.articles
                    val articlesList = ArrayList<Articles>()
                    appExecutors.diskIO.execute {
                        responseBody?.forEach {
                            val isBookmarked = articlesDao.isNewsBookmarked(it.title.toString())
                            val date = DateFormatter.formatDate(it.publishedAt)
                            val articles = Articles(
                                it.title.toString(),
                                date.toString(),
                                it.source?.name.toString(),
                                it.urlToImage.toString(),
                                it.description,
                                it.url.toString(),
                                isBookmarked
                            )
                            articlesList.add(articles)
                        }
                        articlesDao.deleteAll()
                        articlesDao.insertArticles(articlesList)
                    }
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                _listArticles.value = MyResult.Error(t.message.toString())
            }
        })
        setArticlesToLocal()
    }

    private fun setArticlesToLocal() {
        val localData = articlesDao.getArticles()
        _listArticles.addSource(localData) {
            _listArticles.value = MyResult.Success(it)
        }
    }

    fun setBookmarkArticles(articles: Articles, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            articles.isBookmarked = bookmarkState
            articlesDao.updateArticles(articles)
        }
    }

    fun getBookmarkedArticles(): LiveData<List<Articles>> {
        return articlesDao.getBookmarkedArticles()
    }

    companion object {
        private const val LOCAL_IDN = "id"

        @Volatile
        private var INSTANCE: ArticlesRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: ArticlesDao,
            appExecutors: AppExecutors
        ): ArticlesRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ArticlesRepository(apiService, newsDao, appExecutors)
            }.also { INSTANCE = it }
    }
}
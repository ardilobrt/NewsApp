package com.and.news.data.repository

import androidx.lifecycle.*
import com.and.news.data.Event
import com.and.news.data.local.entity.Articles
import com.and.news.data.local.room.ArticlesDao
import com.and.news.data.remote.api.ArticleService
import com.and.news.data.remote.model.ArticlesResponse
import com.and.news.utils.AppExecutors
import com.and.news.utils.DateFormatter
import retrofit2.*

class ArticlesRepository(
    private val articleService: ArticleService,
    private val articlesDao: ArticlesDao,
    private val appExecutors: AppExecutors
) {
    val listArticles: MediatorLiveData<List<Articles>> = MediatorLiveData()
    val errorMessage: MutableLiveData<Event<String>> = MutableLiveData()

    fun getArticlesFromApi() {
        val client = articleService.getNewsByLocal(LOCAL_IDN)
        client.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.articles
                    val articlesList = mutableListOf<Articles>()
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
                } else errorMessage.value = Event("Something Error")
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                errorMessage.value = Event(t.message.toString())
            }
        })
        setArticlesToLocal()
    }

    private fun setArticlesToLocal() {
        val localData = articlesDao.getArticles()
        listArticles.addSource(localData) {
            listArticles.value = it.toMutableList()
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
            articleService: ArticleService,
            newsDao: ArticlesDao,
            appExecutors: AppExecutors
        ): ArticlesRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ArticlesRepository(articleService, newsDao, appExecutors)
            }.also { INSTANCE = it }
    }
}
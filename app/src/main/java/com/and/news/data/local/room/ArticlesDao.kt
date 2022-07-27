package com.and.news.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.and.news.data.local.entity.Articles

@Dao
interface ArticlesDao {
    @Query("SELECT * FROM articles WHERE country = :country ORDER BY publishedAt DESC")
    fun getArticles(country: String): LiveData<List<Articles>>

    @Query("SELECT * FROM articles WHERE isBookmarked = 1")
    fun getBookmarkedArticles(): LiveData<List<Articles>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticles(articles: List<Articles>)

    @Update
    fun updateArticles(articles: Articles)

    @Query("DELETE FROM articles WHERE isBookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM articles WHERE title = :title AND isBookmarked = 1)")
    fun isNewsBookmarked(title: String): Boolean
}
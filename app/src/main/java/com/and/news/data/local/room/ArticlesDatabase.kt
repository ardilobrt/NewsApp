package com.and.news.data.local.room

import android.content.Context
import androidx.room.*
import com.and.news.data.local.entity.Articles

@Database(entities = [Articles::class], version = 1, exportSchema = false)
abstract class ArticlesDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticlesDao

    companion object {
        @Volatile
        private var INSTANCE: ArticlesDatabase? = null
        fun getInstance(context: Context): ArticlesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ArticlesDatabase::class.java, "news.db"
                ).build()
            }
    }
}
package com.and.news.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL = "https://newsapi.org/v2/"
    private const val USER_URL = "https://binar-gdd-cc8.herokuapp.com/api/v1/"
    private lateinit var retrofit: Retrofit

    fun getArticleService(): ArticleService {

        retrofit = setRetrofit(BASE_URL)
        return retrofit.create(ArticleService::class.java)
    }

    fun getUserService(): UserService {
        retrofit = setRetrofit(USER_URL)
        return retrofit.create(UserService::class.java)
    }

    private fun setRetrofit(url: String): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
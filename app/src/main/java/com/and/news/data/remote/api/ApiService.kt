package com.and.news.data.remote.api

import com.and.news.BuildConfig
import com.and.news.data.remote.model.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    fun getNewsByLocal(
        @Query("country") country: String,
        @Query("apiKey") apikey: String = BuildConfig.APP_KEY
    ) : Call<ArticlesResponse>
}
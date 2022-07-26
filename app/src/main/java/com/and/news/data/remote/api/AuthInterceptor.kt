package com.and.news.data.remote.api

import android.content.Context
import com.and.news.utils.SharedPrefManager
import okhttp3.Interceptor
import okhttp3.Response

// https://medium.com/android-news/token-authorization-with-retrofit-android-oauth-2-0-747995c79720
class AuthInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        SharedPrefManager.getUserToken(context).let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}
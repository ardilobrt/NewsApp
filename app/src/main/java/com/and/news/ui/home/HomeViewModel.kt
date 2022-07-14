package com.and.news.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.api.ApiConfig
import com.and.news.data.model.ArticlesItem
import com.and.news.data.model.ArticlesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listArticles = MutableLiveData<ArrayList<ArticlesItem>>()
    val listArticles: LiveData<ArrayList<ArticlesItem>> = _listArticles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        setArticles()
    }

    fun setArticles() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNewsByLocal(LOCAL)
        client.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) _listArticles.value = responseBody.articles
                } else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "HomeFragment"
        private const val LOCAL = "id"
    }

}
package com.and.news.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.Event
import com.and.news.data.remote.api.ApiConfig
import com.and.news.data.remote.model.AuthResponse
import com.and.news.data.remote.model.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    val data: MutableLiveData<Data> = MutableLiveData()
    val dataError: MutableLiveData<Event<String>> = MutableLiveData()

    fun getUser(context: Context) {
        val client = ApiConfig.getUserService(context).getUser()
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data
                    data.value = responseBody as Data
                } else dataError.value = Event("Something Wrong")
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                dataError.value = Event(t.message.toString())
                Log.e("Profile Fragment", "onFailure: ${t.message}")
            }
        })
    }
}
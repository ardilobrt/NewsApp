package com.and.news.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.Event
import com.and.news.data.remote.api.ApiConfig
import com.and.news.data.remote.model.AuthResponse
import com.and.news.data.remote.model.Data
import com.and.news.data.remote.model.SignInRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    val data: MutableLiveData<Data> = MutableLiveData()
    val dataError: MutableLiveData<Event<String>> = MutableLiveData()

    fun getUser(context: Context, signInRequest: SignInRequest) {
        val client = ApiConfig.getUserService(context).getUser()
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data
                    data.value = responseBody
                } else {
                    if (response.code() == 403) {
                        getToken(context, signInRequest)
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                dataError.value = Event(t.message.toString())
                Log.e("Profile Fragment", "onFailure: ${t.message}")
            }
        })
    }

    private fun getToken(context: Context, signInRequest: SignInRequest) {
        val client = ApiConfig.getUserService(context).loginUser(signInRequest)
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data
                    data.value = responseBody
                    Log.i("Profile Fragment", "onResponse: Get Token Success")
                } else dataError.value = Event("Failed Get Token")
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                dataError.value = Event(t.message.toString())
            }
        })
    }
}
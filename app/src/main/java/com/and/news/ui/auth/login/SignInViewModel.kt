package com.and.news.ui.auth.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.Event
import com.and.news.data.remote.api.ApiConfig
import com.and.news.data.remote.model.AuthResponse
import com.and.news.data.remote.model.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel : ViewModel() {

    val dataSuccess: MutableLiveData<Event<String>> = MutableLiveData()
    val dataError: MutableLiveData<Event<String>> = MutableLiveData()
    val isLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val token: MutableLiveData<String> = MutableLiveData()

    fun signInUser(signInResponse: SignInResponse, context: Context) {

        if (signInResponse.email.isEmpty() || signInResponse.password.isEmpty()) {
            dataError.value = Event("Please Fill All Field")
            return
        }

        isLoading.value = Event(true)
        val client = ApiConfig.getUserService(context).loginUser(signInResponse)
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                isLoading.value = Event(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data
                    token.value = responseBody?.token
                    dataSuccess.value = Event("Login Success ${responseBody?.username}")
                } else {
                    dataError.value = Event("Login Failed")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                isLoading.value = Event(false)
                dataError.value = Event(t.message.toString())
            }
        })
    }
}
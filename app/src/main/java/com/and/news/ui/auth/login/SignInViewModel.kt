package com.and.news.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.Event
import com.and.news.data.remote.api.ApiConfig
import com.and.news.data.remote.model.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel : ViewModel() {

    val dataSuccess: MutableLiveData<Event<String>> = MutableLiveData()
    val dataError: MutableLiveData<Event<String>> = MutableLiveData()
    val isLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()

    fun signInUser(signInResponse: SignInResponse) {

        if (signInResponse.email.isEmpty() || signInResponse.password.isEmpty()) {
            dataError.value = Event("Please Fill All Field")
            return
        }

        isLoading.value = Event(true)
        val client = ApiConfig.getUserService().loginUser(signInResponse)
        client.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                isLoading.value = Event(false)
                if (response.isSuccessful) {
                    dataSuccess.value = Event("Login Success")
                } else dataError.value = Event("Login Failed")
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                isLoading.value = Event(false)
                dataError.value = Event(t.message.toString())
            }
        })
    }
}
package com.and.news.ui.auth.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.remote.api.ApiConfig
import com.and.news.data.remote.model.AuthResponse
import com.and.news.data.remote.model.SignUpRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    val dataSuccess: MutableLiveData<String> = MutableLiveData()
    val dataError: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun signUpUser(signUpRequest: SignUpRequest, context: Context) {

        if (signUpRequest.username.isEmpty()
            || signUpRequest.email.isEmpty()
            || signUpRequest.password.isEmpty()
        ) {
            dataError.value = "Please Fill All Field"
            return
        }

        isLoading.value = true
        val client = ApiConfig.getUserService(context).registerUser(signUpRequest)
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    dataSuccess.value = "Register Success"
                } else dataError.value = "Register Failed"
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                isLoading.value = false
                dataSuccess.value = t.message
            }
        })
    }
}
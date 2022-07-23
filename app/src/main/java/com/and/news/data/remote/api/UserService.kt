package com.and.news.data.remote.api

import com.and.news.data.remote.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.POST

interface UserService {

    @POST("auth/register")
    fun registerUser(registerResponse: RegisterResponse) : Call<RegisterResponse>
}
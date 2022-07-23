package com.and.news.data.remote.api

import com.and.news.data.remote.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("auth/register")
    fun registerUser(@Body signUpResponse: SignUpResponse) : Call<SignUpResponse>
}
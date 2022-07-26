package com.and.news.data.remote.api

import com.and.news.data.remote.model.AuthResponse
import com.and.news.data.remote.model.SignInRequest
import com.and.news.data.remote.model.SignUpRequest
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("auth/register")
    fun registerUser(@Body signUpRequest: SignUpRequest): Call<AuthResponse>

    @POST("auth/login")
    fun loginUser(@Body signInRequest: SignInRequest): Call<AuthResponse>

    @GET("users")
    fun getUser(): Call<AuthResponse>
}
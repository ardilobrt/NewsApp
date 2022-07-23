package com.and.news.data.remote.model


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)